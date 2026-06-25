#include <WiFi.h>
#include <PubSubClient.h>
#include <HX711.h>
#include <Preferences.h>

#include <BLEDevice.h>
#include <BLEServer.h>
#include <BLEUtils.h>
#include <BLE2902.h>

//====================================================
// HX711 腳位設定
//====================================================
#define HX711_DT   6
#define HX711_SCK  5

HX711 scale;
bool hx711Ready = false;

//====================================================
// NVS 記憶體快取（用於儲存 WiFi 設定）
//====================================================
Preferences preferences;

const char* DEFAULT_WIFI_SSID = "thmrb306";
const char* DEFAULT_WIFI_PASSWORD = "thmrbthmrb";

String currentSSID = "";
String currentPassword = "";

//====================================================
// MQTT 設定
//====================================================
const char* MQTT_SERVER = "mqttgo.io";
const int MQTT_PORT = 1883;

const char* TOPIC_WEIGHT = "esp32/weight";
const char* TOPIC_RAW = "esp32/raw";
const char* TOPIC_CMD = "esp32/cmd";

WiFiClient espClient;
PubSubClient mqttClient(espClient);

//====================================================
// BLE UART 設定
//====================================================
#define BLE_DEVICE_NAME "ESP32S3_SCALE"

#define SERVICE_UUID           "6E400001-B5A3-F393-E0A9-E50E24DCCA9E"
#define RX_CHARACTERISTIC_UUID "6E400002-B5A3-F393-E0A9-E50E24DCCA9E"
#define TX_CHARACTERISTIC_UUID "6E400003-B5A3-F393-E0A9-E50E24DCCA9E"

BLECharacteristic* txCharacteristic = nullptr;
bool bleDeviceConnected = false;

//====================================================
// 電子秤校正參數
//====================================================
float calibration_factor = 387.2;
long zeroOffset = 0;

//====================================================
// 移動平均濾波器參數
//====================================================
const int AVG_SIZE = 8;
float avgBuffer[AVG_SIZE];
int avgIndex = 0;
bool avgReady = false;

//====================================================
// Serial 暫存輸入
//====================================================
String serialInput = "";

//====================================================
// 非阻塞式定時器（重連時間間隔）
//====================================================
unsigned long lastWiFiTryTime = 0;
unsigned long lastMQTTTryTime = 0;
unsigned long lastHX711TryTime = 0;

const unsigned long WIFI_RETRY_INTERVAL = 5000;  // WiFi 重連間隔 5 秒
const unsigned long MQTT_RETRY_INTERVAL = 5000;  // MQTT 重連間隔 5 秒
const unsigned long HX711_RETRY_INTERVAL = 2000; // HX711 重連間隔 2 秒

//====================================================
// BLE 與 Serial 同時輸出訊息
//====================================================
void appPrint(String text)
{
    Serial.println(text);

    if(bleDeviceConnected && txCharacteristic != nullptr)
    {
        txCharacteristic->setValue(text.c_str());
        txCharacteristic->notify();
    }
}

//====================================================
// 從 NVS 快取讀取 WiFi 設定
//====================================================
void loadWiFiFromNVS()
{
    preferences.begin("wifi", false);

    currentSSID = preferences.getString("ssid", DEFAULT_WIFI_SSID);
    currentPassword = preferences.getString("pwd", DEFAULT_WIFI_PASSWORD);

    Serial.println();
    Serial.println("========== 載入 WIFI 設定 ==========");
    Serial.print("SSID : ");
    Serial.println(currentSSID);
    Serial.println("Password : ********");
}

//====================================================
// 儲存 WiFi 設定至 NVS 快取
//====================================================
void saveWiFiToNVS()
{
    preferences.putString("ssid", currentSSID);
    preferences.putString("pwd", currentPassword);
    appPrint("WiFi 設定已成功儲存至 NVS。");
}

//====================================================
// 解析並套用 WiFi 指令（格式：SSID:PASSWORD）
//====================================================
void applyWiFiCommand(String input)
{
    input.trim();
    int colonIndex = input.indexOf(':');

    if(colonIndex <= 0)
    {
        appPrint("格式錯誤，請輸入正確格式：SSID:PASSWORD");
        return;
    }

    currentSSID = input.substring(0, colonIndex);
    currentPassword = input.substring(colonIndex + 1);

    currentSSID.trim();
    currentPassword.trim();

    if(currentSSID.length() == 0)
    {
        appPrint("錯誤：SSID 不可為空白");
        return;
    }

    Serial.println();
    Serial.println("========== 設定新 WIFI ==========");
    Serial.print("新 SSID : ");
    Serial.println(currentSSID);
    Serial.println("新 Password : ********");

    saveWiFiToNVS();

    appPrint("正在重新連線 WiFi...");

    if(mqttClient.connected())
    {
        mqttClient.disconnect();
    }

    WiFi.disconnect(true);
    delay(300);

    WiFi.mode(WIFI_STA);
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());
    lastWiFiTryTime = millis();
}

//====================================================
// BLE 伺服器狀態回呼（連線/斷線）
//====================================================
class MyServerCallbacks : public BLEServerCallbacks
{
    void onConnect(BLEServer* server)
    {
        bleDeviceConnected = true;
        Serial.println("BLE 已連線");
    }

    void onDisconnect(BLEServer* server)
    {
        bleDeviceConnected = false;
        Serial.println("BLE 已斷線");
        delay(300);
        BLEDevice::startAdvertising(); // 重新開啟廣播供後續連線
    }
};

//====================================================
// BLE 接收資料回呼（收到手機端指令）
//====================================================
class MyRXCallbacks : public BLECharacteristicCallbacks
{
    void onWrite(BLECharacteristic* characteristic)
    {
        String rxValue = characteristic->getValue().c_str();
        rxValue.trim();

        if(rxValue.length() > 0)
        {
            Serial.println();
            Serial.println("========== 收到 BLE 指令 ==========");
            Serial.print("指令內容 : ");
            Serial.println(rxValue);

            applyWiFiCommand(rxValue);
        }
    }
};

//====================================================
// BLE 初始化設定
//====================================================
void setupBLE()
{
    BLEDevice::init(BLE_DEVICE_NAME);
    BLEServer* server = BLEDevice::createServer();
    server->setCallbacks(new MyServerCallbacks());

    BLEService* service = server->createService(SERVICE_UUID);

    // 建立 TX 特徵值（用來發送數據給手機）
    txCharacteristic = service->createCharacteristic(
        TX_CHARACTERISTIC_UUID,
        BLECharacteristic::PROPERTY_NOTIFY
    );
    txCharacteristic->addDescriptor(new BLE2902());
    txCharacteristic->setValue("BLE TX Ready");

    // 建立 RX 特徵值（用來接收手機端設定）
    BLECharacteristic* rxCharacteristic = service->createCharacteristic(
        RX_CHARACTERISTIC_UUID,
        BLECharacteristic::PROPERTY_WRITE | BLECharacteristic::PROPERTY_WRITE_NR
    );
    rxCharacteristic->setValue("");
    rxCharacteristic->setCallbacks(new MyRXCallbacks());

    service->start();

    // 開始 BLE 廣播
    BLEAdvertising* advertising = BLEDevice::getAdvertising();
    advertising->addServiceUUID(SERVICE_UUID);
    advertising->setScanResponse(true);
    BLEDevice::startAdvertising();

    Serial.println("BLE UART 服務已啟動");
    Serial.print("藍牙名稱 : ");
    Serial.println(BLE_DEVICE_NAME);
}

//====================================================
// MQTT 訊息接收回呼
//====================================================
void mqttCallback(char* topic, byte* payload, unsigned int length)
{
    String message;
    for(unsigned int i = 0; i < length; i++)
    {
        message += (char)payload[i];
    }

    Serial.println();
    Serial.println("========== 收到 MQTT 訊息 ==========");
    Serial.print("主題 Topic : ");
    Serial.println(topic);
    Serial.print("內容 Message : ");
    Serial.println(message);

    // 遠端去皮歸零指令
    if(message == "tare")
    {
        if(scale.is_ready())
        {
            Serial.println("執行遠端去皮 (Tare)...");
            scale.tare();
            delay(300);
            zeroOffset = scale.read_average(30);
            Serial.print("新基準零點零點偏置 = ");
            Serial.println(zeroOffset);
        }
        else
        {
            Serial.println("HX711 未就緒，忽略歸零指令。");
        }
    }
}

//====================================================
// 處理 Serial 序列埠輸入指令
//====================================================
void handleSerialCommand()
{
    while(Serial.available() > 0)
    {
        char c = Serial.read();

        if(c == '\n' || c == '\r')
        {
            serialInput.trim();
            if(serialInput.length() > 0)
            {
                Serial.println();
                Serial.println("========== 收到序列埠指令 ==========");
                Serial.print("指令內容 : ");
                Serial.println(serialInput);

                applyWiFiCommand(serialInput);
            }
            serialInput = "";
        }
        else
        {
            serialInput += c;
        }
    }
}

//====================================================
// WiFi 非阻塞式連線檢查與重連
//====================================================
void connectWiFiNonBlocking()
{
    if(WiFi.status() == WL_CONNECTED)
    {
        return;
    }

    unsigned long now = millis();
    if(now - lastWiFiTryTime < WIFI_RETRY_INTERVAL)
    {
        return;
    }
    lastWiFiTryTime = now;

    Serial.println();
    Serial.println("嘗試連線 WiFi...");
    Serial.print("SSID : ");
    Serial.println(currentSSID);

    WiFi.disconnect();
    WiFi.mode(WIFI_STA);
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());
}

//====================================================
// MQTT 非阻塞式連線檢查與重連
//====================================================
void connectMQTTNonBlocking()
{
    if(WiFi.status() != WL_CONNECTED)
    {
        return;
    }

    if(mqttClient.connected())
    {
        return;
    }

    unsigned long now = millis();
    if(now - lastMQTTTryTime < MQTT_RETRY_INTERVAL)
    {
        return;
    }
    lastMQTTTryTime = now;

    Serial.println();
    Serial.println("嘗試連線 MQTT 伺服器...");

    String clientId = "ESP32S3-";
    clientId += String(random(0xffff), HEX);

    if(mqttClient.connect(clientId.c_str()))
    {
        Serial.println("MQTT 已連線");
        mqttClient.subscribe(TOPIC_CMD);
        Serial.print("已訂閱主題 : ");
        Serial.println(TOPIC_CMD);
    }
    else
    {
        Serial.print("MQTT 連線失敗 rc=");
        Serial.println(mqttClient.state());
    }
}

//====================================================
// HX711 初始化（非阻塞，避免硬體沒插好導致整機卡死）
//====================================================
void initHX711NonBlocking()
{
    Serial.println("HX711 初始化中...");
    scale.begin(HX711_DT, HX711_SCK);

    unsigned long startTime = millis();
    while(!scale.is_ready())
    {
        handleSerialCommand(); // 即使硬體未好，依然允許接收 Serial 設定

        if(millis() - startTime > 3000) // 超過 3 秒判定為離線
        {
            hx711Ready = false;
            Serial.println("HX711 未就緒。");
            Serial.println("秤體處於離線狀態，但 WiFi/BLE 仍可正常設定。");
            return;
        }
        delay(10);
    }

    hx711Ready = true;
    Serial.println("開機自動去皮 (Tare)...");
    scale.tare();
    delay(300);
    zeroOffset = scale.read_average(30);
    
    Serial.print("初始零點偏置 = ");
    Serial.println(zeroOffset);
    Serial.println("HX711 初始化成功並已就緒");
}

//====================================================
// HX711 斷線重新檢查機制
//====================================================
void checkHX711Reconnect()
{
    if(hx711Ready)
    {
        return;
    }

    unsigned long now = millis();
    if(now - lastHX711TryTime < HX711_RETRY_INTERVAL)
    {
        return;
    }
    lastHX711TryTime = now;

    if(scale.is_ready())
    {
        Serial.println();
        Serial.println("偵測到 HX711 已重新連接。");
        hx711Ready = true;

        Serial.println("重新去皮 (Tare)...");
        scale.tare();
        delay(300);
        zeroOffset = scale.read_average(30);
        Serial.print("重新整理後的零點偏置 = ");
        Serial.println(zeroOffset);
        Serial.println("HX711 恢復就緒");
    }
}

//====================================================
// 移動平均濾波（平滑震盪數據）
//====================================================
float movingAverage(float value)
{
    avgBuffer[avgIndex] = value;
    avgIndex++;

    if(avgIndex >= AVG_SIZE)
    {
        avgIndex = 0;
        avgReady = true;
    }

    int count = avgReady ? AVG_SIZE : avgIndex;
    float sum = 0;

    for(int i = 0; i < count; i++)
    {
        sum += avgBuffer[i];
    }

    return sum / count;
}

//====================================================
// 自動零點微調追隨（Auto Zero Tracking）
// 目的：補償環境溫度或小雜訊引起的零點微小飄移
//====================================================
void autoZero(float weight)
{
    // 注意：必須是極度微小的真實重量變化（例如小於 0.15g）才視為慢速溫飄並進行微調追隨
    if(abs(weight) < 0.15 && scale.is_ready())
    {
        long raw = scale.read_average(5);
        // 極高權重的低通濾波，使零點非常緩慢地跟隨真實物理變化
        zeroOffset = (zeroOffset * 9999 + raw) / 10000;
    }
}

//====================================================
// Arduino Setup 初始化主程式
//====================================================
void setup()
{
    Serial.begin(115200);
    delay(1000);

    Serial.println();
    Serial.println("=================================");
    Serial.println("  HX711 MQTT BLE SCALE 已啟動    ");
    Serial.println("  序列埠 / 藍牙 更改 WiFi 格式:   ");
    Serial.println("  SSID:PASSWORD                  ");
    Serial.println("=================================");

    loadWiFiFromNVS();
    setupBLE();

    WiFi.mode(WIFI_STA);
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());

    Serial.println("\nWiFi 開始非阻塞連線，秤體允許離線工作。");

    mqttClient.setServer(MQTT_SERVER, MQTT_PORT);
    mqttClient.setCallback(mqttCallback);

    initHX711NonBlocking();
    Serial.println("系統就緒！");
}

//====================================================
// Arduino Loop 主要循環主程式
//====================================================
void loop()
{
    // 1. 優先處理序列埠指令與連線狀態（確保不卡死）
    handleSerialCommand();
    connectWiFiNonBlocking();
    connectMQTTNonBlocking();

    if(mqttClient.connected())
    {
        mqttClient.loop();
    }

    // 2. 檢查感測器是否斷線重連
    checkHX711Reconnect();

    // 若感測器離線，跳過重量計算，但不影響 BLE/WiFi 設定功能
    if(!hx711Ready || !scale.is_ready())
    {
        Serial.println("HX711 離線中... WiFi/藍牙設定仍可正常運作。");
        delay(500);
        return;
    }

    // 3. 讀取感測器 RAW 原始值（採樣 10 次）
    long raw = scale.read_average(10);

    // 4. 計算真實重量 (尚未經過零點死區截斷)
    float weight = (raw - zeroOffset) / calibration_factor;
    
    // 5. 進行平滑濾波
    weight = movingAverage(weight);

    // 6. 重要修正：【先】拿真實未截斷的重量進行自動零點微調追隨
    // 如果重量在 0.15g 以內，代表是慢速溫飄，程式會自動追隨更新零點基準
    if (abs(weight) < 0.15) 
    {
        autoZero(weight);
    }

    // 7. 【後】進行顯示死區（小數值遮罩）處理
    // 為了視覺美觀，若濾波與追隨後的重量小於死區門檻（0.3g），顯示與發送直接歸零
    if(abs(weight) < 0.3)
    {
        weight = 0;
    }

    // 8. Serial 終端機格式化輸出
    Serial.print("RAW = ");
    Serial.print(raw);
    Serial.print("    Weight = ");
    Serial.print(weight, 2);
    Serial.print(" g");

    if(WiFi.status() == WL_CONNECTED) { Serial.print("    WiFi OK"); }
    else { Serial.print("    WiFi OFFLINE"); }

    if(mqttClient.connected()) { Serial.print("    MQTT OK"); }
    else { Serial.print("    MQTT OFFLINE"); }
    
    Serial.println();

    // 9. MQTT 資料發送
    if(mqttClient.connected())
    {
        char rawText[20];
        sprintf(rawText, "%ld", raw);
        mqttClient.publish(TOPIC_RAW, rawText, true);

        char weightText[20];
        dtostrf(weight, 0, 2, weightText);
        mqttClient.publish(TOPIC_WEIGHT, weightText, true);
    }

    delay(200); // 每次主循環延時 200ms
}

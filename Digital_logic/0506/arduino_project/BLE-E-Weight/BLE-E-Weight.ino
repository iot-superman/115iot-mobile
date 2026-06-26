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
const char* TOPIC_MED_TAKEN = "esp32/medication/taken"; // 新增：吃藥紀錄主題
const char* TOPIC_WAT_TAKEN = "esp32/water/taken";      // 新增：飲水紀錄主題

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

String bleRxBuffer = "";
unsigned long lastBleRxTime = 0;
const unsigned long BLE_PACKET_TIMEOUT = 50; 

//====================================================
// 電子秤校正參數
//====================================================
float calibration_factor = 387.2; // 請確保此數值與你的感測器匹配

//====================================================
// 移動平均濾波器參數
//====================================================
const int AVG_SIZE = 8;
float avgBuffer[AVG_SIZE];
int avgIndex = 0;
bool avgReady = false;

//====================================================
// 長輩行為追蹤狀態機 (長照專用免按鈕設計)
//====================================================
float initial_weight = 0;   // 拿起前的重量
bool has_item = false;      // 秤上目前是否有東西
bool is_lifted = false;     // 東西是否被拿起來了
float last_stable_weight = 0;
int stable_count = 0;
const int STABLE_THRESHOLD = 4; // 連續 4 次讀數接近（約 0.8 秒）視為數值穩定

//====================================================
// Serial 暫存輸入
//====================================================
String serialInput = "";

//====================================================
// 非阻塞式定時器
//====================================================
unsigned long lastWiFiTryTime = 0;
unsigned long lastMQTTTryTime = 0;
unsigned long lastHX711TryTime = 0;

const unsigned long WIFI_RETRY_INTERVAL = 5000;  
const unsigned long MQTT_RETRY_INTERVAL = 5000;  
const unsigned long HX711_RETRY_INTERVAL = 2000; 

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
// 執行去皮歸零實作 (Tare)
//====================================================
void executeTare()
{
    if(scale.is_ready())
    {
        appPrint("執行去皮歸零 (Tare)...");
        scale.tare(10); // 取 10 次平均建立基準點
        appPrint("去皮完成。");
    }
    else
    {
        appPrint("錯誤：HX711 未就緒，忽略歸零指令。");
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

    Serial.println("\n========== 載入 WIFI 設定 ==========");
    Serial.print("SSID : ");
    Serial.println(currentSSID);
    Serial.println("Password : ********");
}

//====================================================
// 儲儲 WiFi 設定至 NVS 快取
//====================================================
void saveWiFiToNVS()
{
    preferences.putString("ssid", currentSSID);
    preferences.putString("pwd", currentPassword);
    appPrint("WiFi 設定已成功儲存至 NVS。");
}

//====================================================
// 核心指令解析中心
//====================================================
void handleCommand(String input)
{
    input.trim();
    if(input.length() == 0) return;

    if(input == "tare")
    {
        executeTare();
        return;
    }

    int colonIndex = input.indexOf(':');
    if(colonIndex <= 0)
    {
        appPrint("未知指令或格式錯誤： '" + input + "'. 更改 WiFi 請輸入：SSID:PASSWORD");
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

    Serial.println("\n========== 設定新 WIFI ==========");
    Serial.print("新 SSID : ");
    Serial.println(currentSSID);
    saveWiFiToNVS();

    appPrint("正在重新連線 WiFi...");
    if(mqttClient.connected()) mqttClient.disconnect();

    WiFi.disconnect(true);
    delay(300);
    WiFi.mode(WIFI_STA);
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());
    lastWiFiTryTime = millis();
}

//====================================================
// BLE 伺服器狀態回呼
//====================================================
class MyServerCallbacks : public BLEServerCallbacks
{
    void onConnect(BLEServer* server)
    {
        bleDeviceConnected = true;
        Serial.println("BLE 已連線");
        bleRxBuffer = ""; 
    }
    void onDisconnect(BLEServer* server)
    {
        bleDeviceConnected = false;
        Serial.println("BLE 已斷線");
        delay(300);
        BLEDevice::startAdvertising(); 
    }
};

//====================================================
// BLE 接收資料回呼
//====================================================
class MyRXCallbacks : public BLECharacteristicCallbacks
{
    void onWrite(BLECharacteristic* characteristic)
    {
        String rxValue = characteristic->getValue().c_str();
        if(rxValue.length() > 0)
        {
            bleRxBuffer += rxValue;
            lastBleRxTime = millis(); 
        }
    }
};

void setupBLE()
{
    BLEDevice::init(BLE_DEVICE_NAME);
    BLEServer* server = BLEDevice::createServer();
    server->setCallbacks(new MyServerCallbacks());

    BLEService* service = server->createService(SERVICE_UUID);

    txCharacteristic = service->createCharacteristic(TX_CHARACTERISTIC_UUID, BLECharacteristic::PROPERTY_NOTIFY);
    txCharacteristic->addDescriptor(new BLE2902());
    txCharacteristic->setValue("BLE TX Ready");

    BLECharacteristic* rxCharacteristic = service->createCharacteristic(
        RX_CHARACTERISTIC_UUID, BLECharacteristic::PROPERTY_WRITE | BLECharacteristic::PROPERTY_WRITE_NR
    );
    rxCharacteristic->setValue("");
    rxCharacteristic->setCallbacks(new MyRXCallbacks());

    service->start();
    BLEAdvertising* advertising = BLEDevice::getAdvertising();
    advertising->addServiceUUID(SERVICE_UUID);
    advertising->setScanResponse(true);
    BLEDevice::startAdvertising();

    Serial.println("BLE UART 服務已啟動");
}

//====================================================
// MQTT 訊息接收回呼
//====================================================
void mqttCallback(char* topic, byte* payload, unsigned int length)
{
    String message;
    for(unsigned int i = 0; i < length; i++) message += (char)payload[i];

    Serial.println("\n========== 收到 MQTT 訊息 ==========");
    Serial.print("主題 Topic : ");  Serial.println(topic);
    Serial.print("內容 Message : "); Serial.println(message);

    handleCommand(message);
}

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
                Serial.println("\n========== 收到序列埠指令 ==========");
                handleCommand(serialInput);
            }
            serialInput = "";
        }
        else
        {
            serialInput += c;
        }
    }
}

void connectWiFiNonBlocking()
{
    if(WiFi.status() == WL_CONNECTED) return;

    unsigned long now = millis();
    if(now - lastWiFiTryTime < WIFI_RETRY_INTERVAL) return;
    lastWiFiTryTime = now;

    Serial.println("\n嘗試連線 WiFi...");
    WiFi.disconnect();
    WiFi.mode(WIFI_STA);
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());
}

void connectMQTTNonBlocking()
{
    if(WiFi.status() != WL_CONNECTED || mqttClient.connected()) return;

    unsigned long now = millis();
    if(now - lastMQTTTryTime < MQTT_RETRY_INTERVAL) return;
    lastMQTTTryTime = now;

    Serial.println("\n嘗試連線 MQTT 伺服器...");
    String clientId = "ESP32S3-";
    clientId += String(random(0xffff), HEX);

    if(mqttClient.connect(clientId.c_str()))
    {
        Serial.println("MQTT 已連線");
        mqttClient.subscribe(TOPIC_CMD);
    }
}

void initHX711NonBlocking()
{
    Serial.println("HX711 初始化中...");
    scale.begin(HX711_DT, HX711_SCK);
    scale.set_scale(calibration_factor); // 直接在初始化時設定校正因子

    unsigned long startTime = millis();
    while(!scale.is_ready())
    {
        handleSerialCommand();
        if(millis() - startTime > 3000)
        {
            hx711Ready = false;
            Serial.println("HX711 未就緒。");
            return;
        }
        delay(10);
    }

    hx711Ready = true;
    Serial.println("開機自動去皮 (Tare)...");
    scale.tare(20);
    Serial.println("HX711 初始化成功並已就緒");
}

void checkHX711Reconnect()
{
    if(hx711Ready) return;

    unsigned long now = millis();
    if(now - lastHX711TryTime < HX711_RETRY_INTERVAL) return;
    lastHX711TryTime = now;

    if(scale.is_ready())
    {
        Serial.println("\n偵測到 HX711 已重新連接。重新去皮...");
        scale.set_scale(calibration_factor);
        scale.tare(20);
        hx711Ready = true;
    }
}

//====================================================
// 移動平均濾波
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
    for(int i = 0; i < count; i++) sum += avgBuffer[i];
    return sum / count;
}

//====================================================
// 判斷重量是否穩定
//====================================================
bool checkStable(float current) {
    if (abs(current - last_stable_weight) < 0.4) { 
        stable_count++;
    } else {
        stable_count = 0;
    }
    last_stable_weight = current;
    return (stable_count >= STABLE_THRESHOLD);
}

//====================================================
// 主程式 Setup 與 Loop
//====================================================
void setup()
{
    Serial.begin(115200);
    delay(1000);

    loadWiFiFromNVS();
    setupBLE();

    mqttClient.setServer(MQTT_SERVER, MQTT_PORT);
    mqttClient.setCallback(mqttCallback);

    initHX711NonBlocking();
}

void loop()
{
    handleSerialCommand();
    connectWiFiNonBlocking();
    connectMQTTNonBlocking();

    if(mqttClient.connected()) mqttClient.loop();

    // BLE 藍牙指令分包阻斷組裝
    if (bleRxBuffer.length() > 0)
    {
        if ((millis() - lastBleRxTime > BLE_PACKET_TIMEOUT) || 
            (bleRxBuffer.indexOf('\n') >= 0) || (bleRxBuffer.indexOf('\r') >= 0))
        {
            bleRxBuffer.trim();
            handleCommand(bleRxBuffer);
            bleRxBuffer = ""; 
        }
    }

    checkHX711Reconnect();

    if(!hx711Ready || !scale.is_ready())
    {
        delay(200);
        return;
    }

    // 【核心優化】改用 get_units(1) 非阻塞讀取，不拖慢整個 MCU 的 Loop 速度
    float weight = scale.get_units(1); 
    weight = movingAverage(weight);
    long raw = scale.read(); // 僅讀取單次 raw 供 MQTT 監看

    // 零點追蹤 (AZT)：小於 0.2g 且沒放東西時，偷偷進行極微幅 Tare
    if (!has_item && abs(weight) < 0.20) 
    {
        scale.tare(1); // 僅用 1 筆樣本微幅修正
        weight = 0;
    }
    
    if (abs(weight) < 0.4) weight = 0; // 顯示盲區

    // 顯示當前數值
    Serial.print("RAW = "); Serial.print(raw);
    Serial.print("    Weight = "); Serial.print(weight, 2);
    Serial.println(" g");

    // ====================================================
    // 長輩吃藥/飲水 動態行為演算法（核心新增）
    // ====================================================
    bool isStableNow = checkStable(weight);

    if (isStableNow) {
        // 動作一：照顧者或長輩放上容器（水杯或藥碗）
        if (!has_item && weight > 8.0) { 
            has_item = true;
            is_lifted = false;
            initial_weight = weight;
            appPrint("【長照通知】放上容器，初始重: " + String(initial_weight, 2) + " g");
        }
        
        // 動作三：長輩服用完畢，把容器放回秤上
        else if (has_item && is_lifted && weight > 8.0) {
            float consumed = initial_weight - weight; // 計算減少的差值

            // 判斷 1: 減少量在 0.4g ~ 12g 之間，通常是藥丸被拿走了
            if (consumed >= 0.4 && consumed <= 12.0) {
                appPrint("【紀錄】偵測到長輩服用藥物！減少: " + String(consumed, 2) + " g");
                if(mqttClient.connected()) {
                    char msg[10]; dtostrf(consumed, 0, 2, msg);
                    mqttClient.publish(TOPIC_MED_TAKEN, msg, true); // 發送至吃藥主題
                }
            }
            // 判斷 2: 減少量大於 12g，判定為喝水
            else if (consumed > 12.0) {
                appPrint("【紀錄】偵測到長輩飲水！減少: " + String(consumed, 2) + " cc");
                if(mqttClient.connected()) {
                    char msg[10]; dtostrf(consumed, 0, 2, msg);
                    mqttClient.publish(TOPIC_WAT_TAKEN, msg, true); // 發送至飲水主題
                }
            }

            initial_weight = weight; // 將目前的重量設為新基準，防止重複計算
            is_lifted = false;
        }
    } 
    else {
        // 動作二：長輩拿起杯子或藥碗（重量明顯下降）
        if (has_item && !is_lifted && weight < (initial_weight - 6.0)) {
            is_lifted = true;
            appPrint("【狀態】長輩已拿起容器...");
        }
    }

    // 動作四：容器被徹底移開（秤面空了超過 2 秒）
    if (has_item && weight < 4.0) {
        has_item = false;
        is_lifted = false;
        scale.tare(5); // 自動歸零，迎接下一次使用
        appPrint("【狀態】容器已收走，秤面重置歸零。");
    }

    // 定時將即時數據發佈至原本的監看主題
    if(mqttClient.connected())
    {
        char rawText[20]; sprintf(rawText, "%ld", raw);
        mqttClient.publish(TOPIC_RAW, rawText, true);

        char weightText[20]; dtostrf(weight, 0, 2, weightText);
        mqttClient.publish(TOPIC_WEIGHT, weightText, true);
    }

    delay(200); 
}

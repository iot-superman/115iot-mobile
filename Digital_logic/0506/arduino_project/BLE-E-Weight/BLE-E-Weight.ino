//2026 6-27優化偵測方式
//程式解說與流程圖： https://chatgpt.com/share/6a3f7f6a-cdac-83e9-9b67-98ee48b0e40b
//算法狀態圖： https://share.gemini.google/B1mAqx4qO58B
#include <WiFi.h>
#include <PubSubClient.h>
#include <HX711.h>
#include <Preferences.h>

#include <BLEDevice.h>
#include <BLEServer.h>
#include <BLEUtils.h>
#include <BLE2902.h>

//====================================================
// HX711 腳位設定（避開 Flash 佔用的 GPIO 6-11）
//====================================================
#define HX711_DT   4  
#define HX711_SCK  5  

HX711 scale;
bool hx711Ready = false;

//====================================================
// 🎯 核心智慧演算法參數設定區（已微調至最精準狀態）
//====================================================
const float MED_THRESHOLD = 0.25;      // 吃藥觸發門檻 (g)
const float WAT_THRESHOLD = 3.00;      // 飲水觸發門檻 (cc)：防止微幅漂移誤判

// ✨【智慧身分識別門檻】：基準總重大於此值視為【水杯模式】，小於此值視為【藥盒模式】
const float MODE_CUP_THRESHOLD = 40.00; 

// 智慧空秤門檻 (g)：當前重量「小於或等於」此值一律視為空秤移開
const float EMPTY_LIMIT = 1.50;        

const float DISPLAY_DEADBAND = 0.10;   // 儀表板零點死區 (g)：當前重量在 ±此值 內網頁強制顯示 0g

// 🔥【放回精密結算參數】：收緊雜訊比，確保藥盒徹底停穩才算單次吃藥克數
const float STABLE_NOISE_LIMIT = 0.06; // 靜態判定雜訊上限 (g)：收緊到 0.06g 讓手離判定更嚴格
const int   STABLE_THRESHOLD = 12;     // 靜態判定連續次數：連續符合 25 次（約 3.7 秒）回穩才結算 12=1.8S

// ✨ 新增：網頁數據美化（拿起凍結畫面）功能開關
// true = 開啟美化（拿起時指針凍結在原位）；false = 關閉美化（預設，拿起時立刻變 0g）
const bool ENABLE_DISPLAY_SMOOTHING = false;

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
const char* TOPIC_MED_TAKEN = "esp32/medication/taken"; 
const char* TOPIC_WAT_TAKEN = "esp32/water/taken";      

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
float calibration_factor = 387.2; 

//====================================================
// 移動平均濾波器參數
//====================================================
const int AVG_SIZE = 8;
float avgBuffer[AVG_SIZE];
int avgIndex = 0;
bool avgReady = false;

//====================================================
// 相對重量演算法暫存變數
//====================================================
float last_reported_weight = 0; // 上一次成功發布紀錄時的靜態總重基準
float last_loop_weight = 0;     // 上一個 Loop 的重量
int stable_count = 0;           // 穩定計數器
bool is_initialized = false;    // 是否已建立初始重量基準

// ✨ 新增：明確鎖定當前模式狀態，防止空秤歸零後失去水杯/藥盒記憶
bool is_cup_mode = false;       // true: 水杯模式, false: 藥盒模式

// ✨ 水杯與藥盒模式防止錯位、記憶拿起前狀態必備的狀態機旗標
float weight_before_pickup = 0.0;
bool box_was_picked_up = false;

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

void appPrint(String text)
{
    Serial.println(text);
    if(bleDeviceConnected && txCharacteristic != nullptr)
    {
        txCharacteristic->setValue(text.c_str());
        txCharacteristic->notify();
    }
}

void executeTare()
{
    if(scale.is_ready())
    {
        appPrint("執行去皮歸零 (Tare)...");
        scale.tare(10); 
        float current_w = scale.get_units(5);
        last_reported_weight = current_w;
        last_loop_weight = current_w;
        weight_before_pickup = 0.0;
        box_was_picked_up = false;
        is_cup_mode = (current_w >= MODE_CUP_THRESHOLD);
        is_initialized = true;
        appPrint("去皮完成，目前基準重: " + String(current_w, 2) + "g");
    }
}

void executeClear()
{
    appPrint("執行紀錄清零 (Clear)...");
    weight_before_pickup = 0.0;
    box_was_picked_up = false;

    if(mqttClient.connected()) {
        mqttClient.publish(TOPIC_MED_TAKEN, "0", true); 
        mqttClient.publish(TOPIC_WAT_TAKEN, "0", true); 
        appPrint("MQTT 吃藥與飲水紀錄已成功獨立清零。");
    } else {
        appPrint("錯誤：MQTT 未連線，無法同步清零網頁端。");
    }
}

void loadWiFiFromNVS()
{
    preferences.begin("wifi", false);
    currentSSID = preferences.getString("ssid", DEFAULT_WIFI_SSID);
    currentPassword = preferences.getString("pwd", DEFAULT_WIFI_PASSWORD);
}

void saveWiFiToNVS()
{
    preferences.putString("ssid", currentSSID);
    preferences.putString("pwd", currentPassword);
    appPrint("WiFi 設定已成功儲存至 NVS。");
}

void handleCommand(String input)
{
    input.trim();
    if(input.length() == 0) return;

    if(input == "tare")
    {
        executeTare();
        return;
    }

    if(input == "clear")
    {
        executeClear();
        return;
    }

    int colonIndex = input.indexOf(':');
    if(colonIndex <= 0) return;

    currentSSID = input.substring(0, colonIndex);
    currentPassword = input.substring(colonIndex + 1);
    currentSSID.trim(); currentPassword.trim();

    saveWiFiToNVS();
    if(mqttClient.connected()) mqttClient.disconnect();
    WiFi.disconnect(true);
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());
}

class MyServerCallbacks : public BLEServerCallbacks {
    void onConnect(BLEServer* server) { bleDeviceConnected = true; bleRxBuffer = ""; }
    void onDisconnect(BLEServer* server) { bleDeviceConnected = false; delay(300); BLEDevice::startAdvertising(); }
};

class MyRXCallbacks : public BLECharacteristicCallbacks {
    void onWrite(BLECharacteristic* characteristic) {
        String rxValue = characteristic->getValue().c_str();
        if(rxValue.length() > 0) { bleRxBuffer += rxValue; lastBleRxTime = millis(); }
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
    BLECharacteristic* rxCharacteristic = service->createCharacteristic(RX_CHARACTERISTIC_UUID, BLECharacteristic::PROPERTY_WRITE | BLECharacteristic::PROPERTY_WRITE_NR);
    rxCharacteristic->setCallbacks(new MyRXCallbacks());
    service->start();
    BLEDevice::getAdvertising()->start();
}

void mqttCallback(char* topic, byte* payload, unsigned int length)
{
    String message;
    for(unsigned int i = 0; i < length; i++) message += (char)payload[i];
    handleCommand(message);
}

void handleSerialCommand()
{
    while(Serial.available() > 0)
    {
        char c = Serial.read();
        if(c == '\n' || c == '\r') {
            serialInput.trim();
            if(serialInput.length() > 0) handleCommand(serialInput);
            serialInput = "";
        } else {
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
    WiFi.disconnect();
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());
}

void connectMQTTNonBlocking()
{
    if(WiFi.status() != WL_CONNECTED || mqttClient.connected()) return;
    unsigned long now = millis();
    if(now - lastMQTTTryTime < MQTT_RETRY_INTERVAL) return;
    lastMQTTTryTime = now;
    String clientId = "ESP32S3-"; clientId += String(random(0xffff), HEX);
    if(mqttClient.connect(clientId.c_str())) mqttClient.subscribe(TOPIC_CMD);
}

void initHX711NonBlocking()
{
    scale.begin(HX711_DT, HX711_SCK);
    scale.set_scale(calibration_factor); 
    unsigned long startTime = millis();
    while(!scale.is_ready()) {
        handleSerialCommand();
        if(millis() - startTime > 3000) return;
        delay(10); 
    }
    scale.tare(20);
    hx711Ready = true; 
}

float movingAverage(float value)
{
    avgBuffer[avgIndex] = value; 
    avgIndex++;
    if(avgIndex >= AVG_SIZE) { avgIndex = 0; avgReady = true; }
    int count = avgReady ? AVG_SIZE : avgIndex;
    float sum = 0;
    for(int i = 0; i < count; i++) sum += avgBuffer[i];
    return sum / count;
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

    if (bleRxBuffer.length() > 0) {
        if ((millis() - lastBleRxTime > BLE_PACKET_TIMEOUT) || (bleRxBuffer.indexOf('\n') >= 0)) {
            bleRxBuffer.trim(); handleCommand(bleRxBuffer); bleRxBuffer = ""; 
        }
    }

    if(!hx711Ready || !scale.is_ready()) { delay(200); return; }

    float weight = scale.get_units(1); 
    weight = movingAverage(weight);
    long raw = scale.read(); 

    // 初始化基準重量與模式
    if (!is_initialized) {
        last_reported_weight = weight;
        last_loop_weight = weight;
        is_cup_mode = (weight >= MODE_CUP_THRESHOLD); // 動態建立初始識別模式
        is_initialized = true;
    }

    // 印出目前即時狀態 (新增顯示目前的 Mode 鎖定狀態)
    Serial.print("RAW = "); Serial.print(raw);
    Serial.print("    Weight = "); Serial.print(weight, 2);
    Serial.print(" g    [Base: "); Serial.print(last_reported_weight, 2);
    Serial.print(" g]   [Mode: "); Serial.print(is_cup_mode ? "CUP" : "BOX");
    Serial.println("]");

    // ====================================================
    // 智慧絕對值靜態比對演算法
    // ====================================================
    if (abs(weight - last_loop_weight) < STABLE_NOISE_LIMIT) { 
        stable_count++;
    } else {
        stable_count = 0; 
    }

    // ✨【藥盒動態離手偵測】：只有在「藥盒模式」下且有物件時，發生重量劇烈掉落（>1.5g）才死鎖歷史重量
    if (!is_cup_mode && last_reported_weight > EMPTY_LIMIT) {
        if ((last_reported_weight - weight) > 1.50 && !box_was_picked_up) {
            weight_before_pickup = last_reported_weight; 
            box_was_picked_up = true;
            Serial.print("🔥【狀態鎖定】偵測到藥盒被拿起！鎖定拿藥前重量: ");
            Serial.println(weight_before_pickup);
        }
    }

    if (stable_count >= STABLE_THRESHOLD) {
        stable_count = 0; 

        // 1. 智慧空秤防呆判定
        if (weight <= EMPTY_LIMIT) {
            // -----------------【狀況 A：水杯模式下被拿走】-----------------
            if (is_cup_mode) {
                if (weight_before_pickup == 0.0) {
                    weight_before_pickup = last_reported_weight; 
                }
                last_reported_weight = weight; // 允許 Base 歸零更新
                box_was_picked_up = false;
                // ✨ 關鍵修正：保持 is_cup_mode 記憶為 true，防止空秤零點雜訊誤觸藥盒流程
                Serial.println("【狀態通知】偵測到水杯被拿離，基準已歸零。保持水杯模式記憶。");
            } 
            // -----------------【狀況 B：藥盒模式下移開/負數漂移】-----------------
            else {
                static unsigned long last_msg_time = 0;
                if (millis() - last_msg_time > 5000) {
                    Serial.println("【狀態通知】當前處於藥盒拿藥狀態，鎖定歷史藥盒基準。");
                    last_msg_time = millis();
                }
            }
        } 
        else {
            // 2. 有東西在秤上（數值達到長時間完全穩定）
            // ✨ 當重量重新回穩在秤上時，動態判斷並切換目前的運作模式
            bool previous_mode = is_cup_mode;
            is_cup_mode = (weight >= MODE_CUP_THRESHOLD); 
            
            if (previous_mode != is_cup_mode) {
                Serial.print("🔄【模式切換】偵測到設備變更，目前切換至: ");
                Serial.println(is_cup_mode ? "水杯模式" : "藥盒模式");
            }

            if (is_cup_mode) {
                // =================【水杯模式】=================
                float consumed = 0.0;
                if (weight_before_pickup >= MODE_CUP_THRESHOLD) {
                    consumed = weight_before_pickup - weight;
                    weight_before_pickup = 0.0; 
                } else {
                    consumed = last_reported_weight - weight;
                }

                if (consumed >= WAT_THRESHOLD) {
                    appPrint("【智慧紀錄】偵測到飲水！單次減少: " + String(consumed, 2) + " cc");
                    if(mqttClient.connected()) {
                        char msg[10]; dtostrf(consumed, 0, 2, msg); 
                        mqttClient.publish(TOPIC_WAT_TAKEN, msg, true);
                    }
                } 
                else if (consumed < -3.0) {
                    Serial.println("【狀態】偵測到水杯水量增加，已更新基準重.");
                }
                last_reported_weight = weight; 
            } 
            else {
                // =================【藥盒模式】=================
                if (box_was_picked_up && weight_before_pickup > EMPTY_LIMIT) {
                    float consumed = weight_before_pickup - weight;

                    if (consumed >= MED_THRESHOLD) {
                        appPrint("【智慧紀錄】偵測服用藥物！單次減少: " + String(consumed, 2) + " g");
                        if(mqttClient.connected()) {
                            char msg[10]; dtostrf(consumed, 0, 2, msg); 
                            mqttClient.publish(TOPIC_MED_TAKEN, msg, true);
                        }
                    } else {
                        Serial.println("【防誤觸】放回後重量無顯著減少，不發送紀錄。");
                    }
                    
                    weight_before_pickup = 0.0;
                    box_was_picked_up = false;
                } 
                else {
                    float consumed = last_reported_weight - weight;
                    if (consumed < -0.20) {
                        Serial.println("【狀態】偵測到藥盒重量顯著增加，更新基準重。");
                    }
                }
                
                last_reported_weight = weight; 
            }
        }
    }

    last_loop_weight = weight; 

   // ====================================================
    // 網頁數據美化與即時發佈（支援獨立開關 Flag）
    // ====================================================
    if(mqttClient.connected())
    {
        // 發佈原始 ADC 數值
        char rawText[20]; sprintf(rawText, "%ld", raw);
        mqttClient.publish(TOPIC_RAW, rawText, true);

        float display_weight = weight;

        // 💡 根據 Flag 決定是否啟用「拿起凍結」功能
        if (ENABLE_DISPLAY_SMOOTHING) {
            // 【開啟美化模式】：東西移開時，畫面凍結在拿起前的重量
            if (display_weight <= EMPTY_LIMIT) {
                if (weight_before_pickup > EMPTY_LIMIT) {
                    display_weight = weight_before_pickup;
                } else {
                    display_weight = last_reported_weight;
                }
            }
            // 只有在完全清空且基準也歸零時，才真正強制作 0
            if (abs(display_weight) < DISPLAY_DEADBAND || (display_weight <= EMPTY_LIMIT && last_reported_weight <= EMPTY_LIMIT)) {
                display_weight = 0.00;
            }
        } 
        else {
            // 【關閉美化模式（預設）】：即時反映秤上重量，低於防呆門檻就直接歸 0
            if (display_weight <= EMPTY_LIMIT || abs(display_weight) < DISPLAY_DEADBAND) {
                display_weight = 0.00;
            }
        }

        // 發佈最終處理後的網頁顯示重量
        char weightText[20]; dtostrf(display_weight, 0, 2, weightText);
        mqttClient.publish(TOPIC_WEIGHT, weightText, true);
    }

    delay(150); 
}


//====================================================
// 2026 最新終極修正版：封鎖輕水杯/保特瓶喝空誤觸藥盒漏洞（Log 串流同步強化版）
//====================================================
#include <WiFi.h>
#include <PubSubClient.h>
#include <HX711.h>
#include <Preferences.h>

#include <BLEDevice.h>
#include <BLEServer.h>
#include <BLEUtils.h>
#include <BLE2902.h>

//====================================================
// HX711 腳位設定（已避開 Flash 佔用的 GPIO 6-11）
//====================================================
#define HX711_DT   4  
#define HX711_SCK  5  

HX711 scale;
bool hx711Ready = false;

//====================================================
// 🎯 核心智慧演算法參數設定區
//====================================================
const float MED_THRESHOLD = 0.25;      // 吃藥觸發門檻 (g)
const float WAT_THRESHOLD = 3.00;      // 飲水觸發門檻 (cc)

// ✨【智慧身分識別門檻】
const float MODE_CUP_THRESHOLD = 40.00; 

// 智慧空秤門檻 (g)
const float EMPTY_LIMIT = 1.50;        

const float DISPLAY_DEADBAND = 0.10;   // 儀表板零點死區 (g)

// 🔥【放回精密結算參數】
const float STABLE_NOISE_LIMIT = 0.06; // 靜態判定雜訊上限 (g)
const int   STABLE_THRESHOLD = 12;     // 靜態判定連續次數（約 1.8 秒）

// ✨【網頁數據美化獨立功能開關】
const bool enBeautiful = false;

//====================================================
// NVS 記憶體快取
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
const char* TOPIC_MSG = "esp32/msg";                     
const char* TOPIC_SERIAL_RAW = "esp/msg/seialraw";       // 🎯 包含所有 Serial 文字與數據的終極追 Log 主題

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
float last_reported_weight = 0; 
float last_loop_weight = 0;     
int stable_count = 0;           
bool is_initialized = false;    

bool is_cup_mode = false;       

float weight_before_pickup = 0.0;
bool box_was_picked_up = false;
bool cup_was_picked_up = false; 

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
// 通用列印函式
//====================================================
void appPrint(String text)
{
    Serial.println(text);
    if(bleDeviceConnected && txCharacteristic != nullptr)
    {
        txCharacteristic->setValue(text.c_str());
        txCharacteristic->notify();
    }
    if(mqttClient.connected())
    {
        mqttClient.publish(TOPIC_MSG, text.c_str(), true);
        mqttClient.publish(TOPIC_SERIAL_RAW, text.c_str(), true); 
    }
}

void logPrint(String text) {
    Serial.println(text);
    if(mqttClient.connected()) {
        mqttClient.publish(TOPIC_MSG, text.c_str(), true);
        mqttClient.publish(TOPIC_SERIAL_RAW, text.c_str(), true); 
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
        cup_was_picked_up = false;
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
    cup_was_picked_up = false;

    if(mqttClient.connected()) {
        mqttClient.publish(TOPIC_MED_TAKEN, "0", true); 
        mqttClient.publish(TOPIC_WAT_TAKEN, "0", true); 
        appPrint("吃藥與飲水數據已重置歸零，MQTT 紀錄已成功獨立清零。");
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
// 主程式 Setup
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

//====================================================
// 主程式 Loop
//====================================================
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
        is_cup_mode = (weight >= MODE_CUP_THRESHOLD); 
        is_initialized = true;
    }

    // ✨【動態加速濾波】
    if (abs(weight - last_loop_weight) > 15.0) {
        for(int i = 0; i < AVG_SIZE; i++) avgBuffer[i] = weight;
        avgReady = true;
        logPrint("⚡【動態加速】偵測到重量劇烈變動，瞬間同步濾波緩衝區。");
    }

    // 1. 建立標準每輪狀態字串（常態純數據行）
    String fullSerialLine = "RAW = " + String(raw) + 
                            "    Weight = " + String(weight, 2) + 
                            " g    [Base: " + String(last_reported_weight, 2) + 
                            " g]   [Mode: " + String(is_cup_mode ? "CUP" : "BOX") + "]";

    // 2. 複製一份給網頁通知端，稍後若觸發結算會自動在此行屁股用單個 | 黏接數據
    String webMsgLine = fullSerialLine;

    // 印出即時狀態到物理 Serial 埠
    Serial.println(fullSerialLine);

    // ====================================================
    // 智慧絕對值靜態比對演算法
    // ====================================================
    if (abs(weight - last_loop_weight) < STABLE_NOISE_LIMIT) { 
        stable_count++;
    } else {
        stable_count = 0; 
    }

    // ✨【動態離手偵測】
    if (last_reported_weight > EMPTY_LIMIT) {
        // ----------------- 狀況一：水杯被拿起 -----------------
        if (is_cup_mode && (last_reported_weight - weight) > 10.0 && !cup_was_picked_up) {
            weight_before_pickup = last_reported_weight;
            cup_was_picked_up = true;
            logPrint("🥛【狀態鎖定】偵測到水杯被拿起！鎖定飲水前重量: " + String(weight_before_pickup, 2) + "g");
        }
        // ----------------- 狀況二：藥盒被拿起 -----------------
        else if (!is_cup_mode && (last_reported_weight - weight) > 1.50 && !box_was_picked_up) {
            weight_before_pickup = last_reported_weight; 
            box_was_picked_up = true;
            logPrint("🔥【狀態鎖定】偵測到藥盒被拿起！鎖定拿藥前重量: " + String(weight_before_pickup, 2) + "g");
        }
    }

    if (stable_count >= STABLE_THRESHOLD) {
        stable_count = 0; 

        // 1. 智慧空秤防呆判定
        if (weight <= EMPTY_LIMIT) {
            if (is_cup_mode) {
                if (weight_before_pickup == 0.0) weight_before_pickup = last_reported_weight; 
                last_reported_weight = weight; 
                static unsigned long last_cup_msg = 0;
                if (millis() - last_cup_msg > 5000) {
                    logPrint("【狀態通知】水杯已拿離。保持水杯模式鎖定。");
                    last_cup_msg = millis();
                }
            } 
            else {
                static unsigned long last_box_msg = 0;
                if (millis() - last_box_msg > 5000) {
                    logPrint("【狀態通知】藥盒已拿離。保持藥盒模式鎖定。");
                    last_box_msg = millis();
                }
            }
        } 
        else {
            // 2. 有東西在秤上且完全穩定
            if (!cup_was_picked_up && !box_was_picked_up) {
                bool previous_mode = is_cup_mode;
                is_cup_mode = (weight >= MODE_CUP_THRESHOLD); 
                
                if (previous_mode != is_cup_mode) {
                    logPrint("🔄【模式切換】偵測到設備變更，目前切換至: " + String(is_cup_mode ? "水杯模式" : "藥盒模式"));
                }
            }

            if (is_cup_mode) {
                // =================【水杯模式結算】=================
                float consumed = 0.0;
                if (cup_was_picked_up && weight_before_pickup >= MODE_CUP_THRESHOLD) {
                    consumed = weight_before_pickup - weight;
                } else {
                    consumed = last_reported_weight - weight;
                }

                if (cup_was_picked_up) {
                    if (consumed >= WAT_THRESHOLD) {
                        // ✨ 升級：在 webMsgLine 屁股用單個 | 黏接網頁通知必備的 Reason 與 Consumed 標籤
                        webMsgLine += "|Reason=WaterTaken|Consumed=" + String(consumed, 1) + "|Mode=CUP|RAW=" + String(raw);

                        appPrint("【智慧紀錄】偵測到飲水！單次減少: " + String(consumed, 2) + " cc");
                        if(mqttClient.connected()) {
                            char msg[10]; dtostrf(consumed, 0, 2, msg); 
                            mqttClient.publish(TOPIC_WAT_TAKEN, msg, true);
                        }
                    } 
                    else if (consumed < -3.0) {
                        logPrint("【狀態】偵測到水杯水量增加，已更新基準重.");
                    }
                    
                    cup_was_picked_up = false;
                }
                last_reported_weight = weight; 
                weight_before_pickup = 0.0;
            } 
            else {
                // =================【藥盒模式結算】=================
                if (box_was_picked_up && weight_before_pickup > EMPTY_LIMIT) {
                    
                    float test_consumed = weight_before_pickup - weight;
                    
                    if (weight < 4.00 && test_consumed > 5.00) {
                        logPrint("🛡️【攔截空秤誤判】目前重量 (" + String(weight, 2) + "g) 判定為空秤零點漂移，藥盒尚未放回，暫緩結算。");
                        stable_count = 0; 
                    }
                    else if (weight > EMPTY_LIMIT) {
                        float consumed = weight_before_pickup - weight;

                        if (consumed >= MED_THRESHOLD) {
                            // ✨ 升級：在 webMsgLine 屁股用單個 | 黏接網頁通知必備的 Reason 與 Consumed 標籤
                            webMsgLine += "|Reason=MedicationTaken|Consumed=" + String(consumed, 2) + "|Mode=BOX|RAW=" + String(raw);

                            appPrint("【智慧紀錄】偵測服用藥物！單次減少: " + String(consumed, 2) + " g");
                            if(mqttClient.connected()) {
                                char msg[10]; dtostrf(consumed, 0, 2, msg); 
                                mqttClient.publish(TOPIC_MED_TAKEN, msg, true);
                            }
                        } else {
                            logPrint("【防誤觸】放回後重量無顯著減少，不發送紀錄。");
                        }
                        
                        weight_before_pickup = 0.0;
                        box_was_picked_up = false;
                        last_reported_weight = weight; 
                    }
                } 
                else {
                    float consumed = last_reported_weight - weight;
                    
                    if (consumed < -0.20) { 
                        logPrint("【防誤觸】偵測到藥盒重量顯著增加，強制刷新基準，不作吃藥結算。");
                        weight_before_pickup = 0.0;
                        box_was_picked_up = false;
                    }
                    else if (consumed >= 0.0 && consumed < MED_THRESHOLD) {
                        logPrint("【防誤觸】微幅自然環境漂移，不作吃藥結算. ");
                    }
                    last_reported_weight = weight; 
                }
            }
        }
    }

    last_loop_weight = weight; 

    // ====================================================
    // 網頁數據美化與即時發佈（保持原始獨立 Raw/Weight 運作）
    // ====================================================
    if(mqttClient.connected())
    {
        // 1. 發送常態 RAW 點位數字
        char rawText[20]; sprintf(rawText, "%ld", raw);
        mqttClient.publish(TOPIC_RAW, rawText, true);

        // 2. 處理儀表板美化重量
        float display_weight = weight;
        if (enBeautiful) {
            if (display_weight <= EMPTY_LIMIT) {
                display_weight = (weight_before_pickup > EMPTY_LIMIT) ? weight_before_pickup : last_reported_weight;
            }
            if (abs(display_weight) < DISPLAY_DEADBAND || (display_weight <= EMPTY_LIMIT && last_reported_weight <= EMPTY_LIMIT)) {
                display_weight = 0.00;
            }
        } 
        else {
            if (abs(display_weight) < DISPLAY_DEADBAND) display_weight = 0.00;
        }
        char weightText[20]; dtostrf(display_weight, 0, 2, weightText);
        mqttClient.publish(TOPIC_WEIGHT, weightText, true);

        // 3. 🎯【分流發佈關鍵強化】
        // 下方大框框專用：維持最純淨、無雜質的數據行，完全不抖動
        mqttClient.publish(TOPIC_SERIAL_RAW, fullSerialLine.c_str(), true); 
        
        // 上方狀態通知專用：發布帶有單個 | 與 Reason/Consumed 的複合行，相容舊有翻譯引擎
        mqttClient.publish(TOPIC_MSG, webMsgLine.c_str(), true); 
    }

    delay(150); 
}
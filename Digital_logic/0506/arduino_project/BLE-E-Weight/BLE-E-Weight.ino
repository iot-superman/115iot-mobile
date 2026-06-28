//====================================================
// 2026 最新終極修正版：加入「防彈跳」、「空秤攔截」與「100% 同步 Serial 文字 MQTT 發佈」
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
const float MED_THRESHOLD = 0.25;      // 吃藥觸發門檻 (g)(藥最小顕為幾克？ )
const float WAT_THRESHOLD = 3.00;      // 飲水觸發門檻 (cc)：防止微幅漂移誤判

// ✨【智慧身分識別門檻】：基準總重大於此值視為【水杯模式】，小於此值視為【藥盒模式】
const float MODE_CUP_THRESHOLD = 40.00; 

// 智慧空秤門檻 (g)：當前重量「小於或等於」此值一律視為空秤移開
const float EMPTY_LIMIT = 1.50;        

const float DISPLAY_DEADBAND = 0.10;   // 儀表板零點死區 (g)：當前重量在 ±此值 內網頁強制顯示 0g

// 🔥【放回精密結算參數】：收緊雜訊比，確保藥盒徹底停穩才算單次吃藥克數
const float STABLE_NOISE_LIMIT = 0.06; // 靜態判定雜訊上限 (g)：收緊到 0.06g 讓手離判定更嚴格
const int   STABLE_THRESHOLD = 12;     // 靜態判定連續次數：連續符合 12 次（約 1.8 秒）回穩才結算

// ✨【網頁數據美化獨立功能開關】
// true = 開啟美化（拿起時指針凍結在原位）；false = 關閉美化（預設，第一個儀表誠實反映目前實際重量）関掉會吃藥偵測比較穩
const bool enBeautiful = false;

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
const char* TOPIC_MSG = "esp32/msg";                     // 動態狀態中文訊息主題
const char* TOPIC_SERIAL_RAW = "esp/msg/seialraw";       // ✨ 誠實反映 SerialPort 原始文字的主題

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

// ✨ 明確鎖定當前模式狀態，防止空秤歸零後失去水杯/藥盒記憶
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

//====================================================
// 功能性函式
//====================================================
void appPrint(String text)
{
    Serial.println(text);
    if(bleDeviceConnected && txCharacteristic != nullptr)
    {
        txCharacteristic->setValue(text.c_str());
        txCharacteristic->notify();
    }
    // 同時將重要狀態文字同步推播至 MQTT esp32/msg 主題
    if(mqttClient.connected())
    {
        mqttClient.publish(TOPIC_MSG, text.c_str(), true);
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
        mqttClient.publish(TOPIC_MSG, "吃藥與飲水數據已重置歸零", true);
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
        is_cup_mode = (weight >= MODE_CUP_THRESHOLD); 
        is_initialized = true;
    }

    // ✨【動態加速濾波】：若瞬間變化量極大（大動作拿起放下），強制刷新緩衝區，消除延遲
    if (abs(weight - last_loop_weight) > 15.0) {
        for(int i = 0; i < AVG_SIZE; i++) avgBuffer[i] = weight;
        avgReady = true;
        Serial.println("⚡【動態加速】偵測到重量劇烈變動，瞬間同步濾波緩衝區。");
    }

    // 建立 100% 完全對齊原本 SerialPort 排版格式的字串字元流
    String fullSerialLine = "RAW = " + String(raw) + 
                            "    Weight = " + String(weight, 2) + 
                            " g    [Base: " + String(last_reported_weight, 2) + 
                            " g]   [Mode: " + String(is_cup_mode ? "CUP" : "BOX") + "]";

    // 印出目前即時狀態到物理 Serial 埠
    Serial.println(fullSerialLine);

    // ====================================================
    // 智慧絕對值靜態比對演算法
    // ====================================================
    if (abs(weight - last_loop_weight) < STABLE_NOISE_LIMIT) { 
        stable_count++;
    } else {
        stable_count = 0; 
    }

    // ✨【藥盒動態離手偵測】：藥盒模式下發生重量劇烈掉落，鎖定歷史重量
    if (!is_cup_mode && last_reported_weight > EMPTY_LIMIT) {
        if ((last_reported_weight - weight) > 1.50 && !box_was_picked_up) {
            weight_before_pickup = last_reported_weight; 
            box_was_picked_up = true;
            
            String msgStr = "🔥【狀態鎖定】偵測到藥盒被拿起！鎖定拿藥前重量: " + String(weight_before_pickup, 2) + "g";
            Serial.println(msgStr);
            if(mqttClient.connected()) mqttClient.publish(TOPIC_MSG, msgStr.c_str(), true);
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
                last_reported_weight = weight; 
                box_was_picked_up = false; 
                
                String msgStr = "【狀態通知】偵測到水杯被拿離，基準已歸零。保持水杯模式記憶。";
                Serial.println(msgStr);
                if(mqttClient.connected()) mqttClient.publish(TOPIC_MSG, msgStr.c_str(), true);
            } 
            // -----------------【狀況 B：藥盒模式下移開/環境漂移】-----------------
            else {
                static unsigned long last_msg_time = 0;
                if (millis() - last_msg_time > 5000) {
                    String msgStr = "【狀態通知】當前處於藥盒拿藥狀態，鎖定歷史藥盒基準。";
                    Serial.println(msgStr);
                    if(mqttClient.connected()) mqttClient.publish(TOPIC_MSG, msgStr.c_str(), true);
                    last_msg_time = millis();
                }
            }
        } 
        else {
            // 2. 有東西在秤上且完全穩定
            bool previous_mode = is_cup_mode;
            is_cup_mode = (weight >= MODE_CUP_THRESHOLD); 
            
            if (previous_mode != is_cup_mode) {
                String msgStr = "🔄【模式切換】偵測到設備變更，目前切換至: " + String(is_cup_mode ? "水杯模式" : "藥盒模式");
                Serial.println(msgStr);
                if(mqttClient.connected()) mqttClient.publish(TOPIC_MSG, msgStr.c_str(), true);
            }

            if (is_cup_mode) {
                // =================【水杯模式】=================
                float consumed = 0.0;
                if (weight_before_pickup >= MODE_CUP_THRESHOLD) {
                    consumed = weight_before_pickup - weight;
                } else {
                    consumed = last_reported_weight - weight;
                }

                if (consumed >= WAT_THRESHOLD) {
                    appPrint("【智慧紀錄】偵測到飲水！單次減少: " + String(consumed, 2) + " cc");
                    if(mqttClient.connected()) {
                        char msg[10]; dtostrf(consumed, 0, 2, msg); 
                        mqttClient.publish(TOPIC_WAT_TAKEN, msg, true);
                    }
                    weight_before_pickup = 0.0; 
                } 
                else if (consumed < -3.0) {
                    String msgStr = "【狀態】偵測到水杯水量增加，已更新基準重.";
                    Serial.println(msgStr);
                    if(mqttClient.connected()) mqttClient.publish(TOPIC_MSG, msgStr.c_str(), true);
                    weight_before_pickup = 0.0; 
                } else {
                    if (weight_before_pickup > 0.0 && abs(consumed) < WAT_THRESHOLD) {
                        weight_before_pickup = 0.0;
                    }
                }
                last_reported_weight = weight; 
            } 
            else {
                // =================【藥盒模式：防空秤與放回物理防抖機制】=================
                if (box_was_picked_up && weight_before_pickup > EMPTY_LIMIT) {
                    
                    float test_consumed = weight_before_pickup - weight;
                    
                    // 1. [攔截空秤]：避免空藥盒移開零點漂移數據被錯算為吃藥
                    if (weight < 4.00 && test_consumed > 5.00) {
                        String msgStr = "🛡️【攔截空秤誤判】目前重量 (" + String(weight, 2) + "g) 判定為空秤零點漂移，藥盒尚未放回，暫緩結算。";
                        Serial.println(msgStr);
                        if(mqttClient.connected()) mqttClient.publish(TOPIC_MSG, msgStr.c_str(), true);
                        
                        stable_count = 0; 
                    }
                    // 2. [放回防彈跳]：必須確實高於空秤範圍，回到實體放回區域，才執行吃藥結算
                    else if (weight > EMPTY_LIMIT) {
                        float consumed = weight_before_pickup - weight;

                        if (consumed >= MED_THRESHOLD) {
                            appPrint("【智慧紀錄】偵測服用藥物！單次減少: " + String(consumed, 2) + " g");
                            if(mqttClient.connected()) {
                                char msg[10]; dtostrf(consumed, 0, 2, msg); 
                                mqttClient.publish(TOPIC_MED_TAKEN, msg, true);
                            }
                        } else {
                            String msgStr = "【防誤觸】放回後重量無顯著減少，不發送紀錄。";
                            Serial.println(msgStr);
                            if(mqttClient.connected()) mqttClient.publish(TOPIC_MSG, msgStr.c_str(), true);
                        }
                        
                        weight_before_pickup = 0.0;
                        box_was_picked_up = false;
                        last_reported_weight = weight; 
                    }
                } 
                else {
                    // 處理「未拿起、但在秤面上直接變重（如手壓到）」的突發狀況
                    float consumed = last_reported_weight - weight;
                    
                    if (consumed < -0.20) { 
                        String msgStr = "【防誤觸】偵測到藥盒重量顯著增加，強制刷新基準，不作吃藥結算。";
                        Serial.println(msgStr);
                        if(mqttClient.connected()) mqttClient.publish(TOPIC_MSG, msgStr.c_str(), true);
                        
                        weight_before_pickup = 0.0;
                        box_was_picked_up = false;
                    }
                    else if (consumed >= 0.0 && consumed < MED_THRESHOLD) {
                        String msgStr = "【防誤觸】微幅自然環境漂移，不作吃藥結算。";
                        Serial.println(msgStr);
                        if(mqttClient.connected()) mqttClient.publish(TOPIC_MSG, msgStr.c_str(), true);
                    }
                    last_reported_weight = weight; 
                }
            }
        }
    }

    last_loop_weight = weight; 

    // ====================================================
    // 網頁數據美化與即時發佈、以及 100% 同步的 Serial Raw 文字推播
    // ====================================================
    if(mqttClient.connected())
    {
        // 1. 發佈原始數值碼到舊的 topic (esp32/raw)
        char rawText[20]; sprintf(rawText, "%ld", raw);
        mqttClient.publish(TOPIC_RAW, rawText, true);

        // 2. ✨【全新優化點】：直接把上面與實體埠 100% 格式同步的完整字串，完好如初發佈到指定主題
        mqttClient.publish(TOPIC_SERIAL_RAW, fullSerialLine.c_str(), true);

        // 3. 發佈第一個網頁儀表板處理後的重量 (esp32/weight)
        float display_weight = weight;

        if (enBeautiful) {
            if (display_weight <= EMPTY_LIMIT) {
                if (weight_before_pickup > EMPTY_LIMIT) {
                    display_weight = weight_before_pickup;
                } else {
                    display_weight = last_reported_weight;
                }
            }
            if (abs(display_weight) < DISPLAY_DEADBAND || (display_weight <= EMPTY_LIMIT && last_reported_weight <= EMPTY_LIMIT)) {
                display_weight = 0.00;
            }
        } 
        else {
            if (abs(display_weight) < DISPLAY_DEADBAND) {
                display_weight = 0.00;
            }
        }

        char weightText[20]; dtostrf(display_weight, 0, 2, weightText);
        mqttClient.publish(TOPIC_WEIGHT, weightText, true);
    }

    delay(150); 
}
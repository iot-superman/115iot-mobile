//====================================================
// 2026 最新終極修正版：全面重構為完整有限狀態機 (FSM) 架構
// 🛡️ 徹底根除：拿起途中誤切模式、空秤漂移誤判放回、盲目比對重量誤觸加水等 5 大邏輯 Bug
// ⚠️ 已修正：空秤穩定時誤判定為放回結算之嚴重邏輯漏洞（完美實作方案 B）
// ⚡ 2026 流暢度重磅升級：實作跨模式瞬時強切，並加入「正向斜率微分防禦」，根除過渡期誤判副作用！
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
// HX711 腳位設定
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
const float EMPTY_LIMIT = 7.00;        // 低於 7.00g 視為「完全空秤環境零點」
const float MODE_CUP_THRESHOLD = 40.00;// 首次放置時：7g ~ 40g 為藥盒，大於 40g 為水杯

const float DISPLAY_DEADBAND = 0.10;   // 儀表板零點死區 (g)

// 🔥【狀態機精密穩定度判定參數】
const float STABLE_NOISE_LIMIT = 0.06; // 靜態判定雜訊上限 (g)
const int   STABLE_THRESHOLD = 12;     // 靜態判定連續次數（約 1.8 秒）
const float NOISE_THRESHOLD = 3.00;     // 水杯放回之最大允許容忍雜訊 (g)
const float WATER_ADD_THRESHOLD = 8.00; // 判定為「真正加水」的最低重量增加量 (g)

const bool enBeautiful = false;

//====================================================
// 🎯 核心 2026 完整狀態機架構定義
//====================================================
enum ScaleState {
    STATE_IDLE,             // 1. 秤盤空秤待機 (重量 < 7g)
    CUP_SETTLED,            // 2. 水杯安穩放置中
    CUP_PICKED_UP,          // 3. 水杯正在被拿起（動態下降中）
    CUP_WAIT_RETURN,        // 4. 水杯完全離開秤台（等待重新放回，Base 凍結）
    BOX_SETTLED,            // 5. 藥盒安穩放置中
    BOX_PICKED_UP,          // 6. 藥盒正在被拿起（動態藥盒下降中）
    BOX_WAIT_RETURN         // 7. 藥盒完全離開秤台（等待重新放回，Base 凍結）
};

ScaleState currentState = STATE_IDLE;

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
const char* TOPIC_SERIAL_RAW = "esp/msg/seialraw";       

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
// 電子秤校正與濾波參數
//====================================================
float calibration_factor = 387.2; 
const int AVG_SIZE = 8;
float avgBuffer[AVG_SIZE];
int avgIndex = 0;
bool avgReady = false;

//====================================================
// 相對重量核心演算法暫存變數
//====================================================
float last_reported_weight = 0; // 當前鎖定的基準重 (Base)
float last_loop_weight = 0;      
int stable_count = 0;            
bool hx711_just_recovered = false; 

float weight_before_pickup = 0.0; // 核心暫存：拿起前的神聖重量

String serialInput = "";
unsigned long lastWiFiTryTime = 0;
unsigned long lastMQTTTryTime = 0;
unsigned long lastHX711TryTime = 0;
const unsigned long WIFI_RETRY_INTERVAL = 5000;  
const unsigned long MQTT_RETRY_INTERVAL = 5000;  
const unsigned long HX711_RETRY_INTERVAL = 2000; 

wl_status_t lastWiFiStatus = WL_DISCONNECTED;

//====================================================
// 通用狀態列印與重導向函式
//====================================================
void logPrint(String text) {
    Serial.println(text);
    if(mqttClient.connected()) {
        mqttClient.publish(TOPIC_MSG, text.c_str(), true);
        mqttClient.publish(TOPIC_SERIAL_RAW, text.c_str(), true); 
    }
}

void appPrint(String text) {
    Serial.println(text);
    if(bleDeviceConnected && txCharacteristic != nullptr) {
        txCharacteristic->setValue(text.c_str());
        txCharacteristic->notify();
    }
    if(mqttClient.connected()) {
        mqttClient.publish(TOPIC_MSG, text.c_str(), true);
        mqttClient.publish(TOPIC_SERIAL_RAW, text.c_str(), true); 
    }
}

String getStateString(ScaleState state) {
    switch(state) {
        case STATE_IDLE:        return "EMPTY_IDLE";
        case CUP_SETTLED:       return "CUP_SETTLED";
        case CUP_PICKED_UP:     return "CUP_PICKED_UP";
        case CUP_WAIT_RETURN:   return "CUP_WAIT_RETURN";
        case BOX_SETTLED:       return "BOX_SETTLED";
        case BOX_PICKED_UP:     return "BOX_PICKED_UP";
        case BOX_WAIT_RETURN:   return "BOX_WAIT_RETURN";
        default:                return "UNKNOWN";
    }
}

String getModeString() {
    if (currentState == CUP_SETTLED || currentState == CUP_PICKED_UP || currentState == CUP_WAIT_RETURN) return "CUP";
    if (currentState == BOX_SETTLED || currentState == BOX_PICKED_UP || currentState == BOX_WAIT_RETURN) return "BOX";
    return "EMPTY";
}

void forceSyncFilterBuffer(float target_weight) {
    for(int i = 0; i < AVG_SIZE; i++) {
        avgBuffer[i] = target_weight;
    }
    avgReady = true;
}

void executeTare() {
    if(scale.is_ready()) {
        appPrint("執行去皮歸零 (Tare)...");
        scale.tare(10); 
        float current_w = scale.get_units(5);
        last_reported_weight = current_w;
        last_loop_weight = current_w;
        weight_before_pickup = 0.0;
        
        if (current_w < EMPTY_LIMIT) {
            currentState = STATE_IDLE;
        } else if (current_w >= EMPTY_LIMIT && current_w < MODE_CUP_THRESHOLD) {
            currentState = BOX_SETTLED;
        } else {
            currentState = CUP_SETTLED;
        }
        hx711_just_recovered = false;
        appPrint("去皮完成。目前狀態: " + getStateString(currentState) + " 基準重: " + String(current_w, 2) + "g");
    }
}

void executeClear() {
    appPrint("執行紀錄清零 (Clear)...");
    weight_before_pickup = 0.0;
    if(mqttClient.connected()) {
        mqttClient.publish(TOPIC_MED_TAKEN, "0", true); 
        mqttClient.publish(TOPIC_WAT_TAKEN, "0", true);  
        appPrint("吃藥與飲水數據已重置歸零。");
    } else {
        appPrint("錯誤：MQTT 未連線，無法同步網頁端。");
    }
}

void loadWiFiFromNVS() {
    preferences.begin("wifi", false);
    currentSSID = preferences.getString("ssid", DEFAULT_WIFI_SSID);
    currentPassword = preferences.getString("pwd", DEFAULT_WIFI_PASSWORD);
    preferences.end();
}

void saveWiFiToNVS() {
    preferences.begin("wifi", false);
    preferences.putString("ssid", currentSSID);
    preferences.putString("pwd", currentPassword);
    preferences.end();
}

void handleCommand(String input) {
    input.trim();
    if(input.length() == 0) return;
    if(input == "tare")  { executeTare(); return; }
    if(input == "clear") { executeClear(); return; }

    int colonIndex = input.indexOf(':');
    if(colonIndex <= 0) return;

    currentSSID = input.substring(0, colonIndex);
    currentPassword = input.substring(colonIndex + 1);
    currentSSID.trim(); currentPassword.trim();
    saveWiFiToNVS();
    
    appPrint("💾 [NVS 儲存成功] WiFi 設定已寫入記憶體！");
    if(mqttClient.connected()) mqttClient.disconnect();
    WiFi.disconnect(true);
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());
    lastWiFiTryTime = millis(); 
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

void setupBLE() {
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

void mqttCallback(char* topic, byte* payload, unsigned int length) {
    String message;
    for(unsigned int i = 0; i < length; i++) message += (char)payload[i];
    handleCommand(message);
}

void handleSerialCommand() {
    while(Serial.available() > 0) {
        char c = Serial.read();
        if(c == '\n' || c == '\r') {
            serialInput.trim();
            if(serialInput.length() > 0) handleCommand(serialInput);
            serialInput = "";
        } else { serialInput += c; }
    }
}

void connectWiFiNonBlocking() {
    wl_status_t currentStatus = WiFi.status();
    if (currentStatus == WL_CONNECTED && lastWiFiStatus != WL_CONNECTED) {
        appPrint("🎉 [WiFi 連線成功] IP 位址: " + WiFi.localIP().toString());
    }
    lastWiFiStatus = currentStatus;
    if(currentStatus == WL_CONNECTED) return;
    
    unsigned long now = millis();
    if(now - lastWiFiTryTime < WIFI_RETRY_INTERVAL) return;
    lastWiFiTryTime = now;
    WiFi.disconnect();
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());
}

void connectMQTTNonBlocking() {
    if(WiFi.status() != WL_CONNECTED || mqttClient.connected()) return;
    unsigned long now = millis();
    if(now - lastMQTTTryTime < MQTT_RETRY_INTERVAL) return;
    lastMQTTTryTime = now;
    String clientId = "ESP32S3-"; clientId += String(random(0xffff), HEX);
    if(mqttClient.connect(clientId.c_str())) {
        mqttClient.subscribe(TOPIC_CMD);
        appPrint("🚀 [MQTT 連線成功] 已成功訂閱控制主題！");
    }
}

void initHX711NonBlocking() {
    scale.begin(HX711_DT, HX711_SCK);
    scale.set_scale(calibration_factor); 
    if (scale.is_ready()) {
        scale.tare(20);
        hx711Ready = true;
        currentState = STATE_IDLE;
        appPrint("✅ HX711 開機歸零成功！");
    } else {
        hx711Ready = false;
        currentState = STATE_IDLE;
        appPrint("⚠️ 未偵測到 HX711 晶片，將在背景持續重連...");
    }
}

float movingAverage(float value) {
    avgBuffer[avgIndex] = value; 
    avgIndex++;
    if(avgIndex >= AVG_SIZE) { avgIndex = 0; avgReady = true; }
    int count = avgReady ? AVG_SIZE : avgIndex;
    float sum = 0;
    for(int i = 0; i < count; i++) sum += avgBuffer[i];
    return sum / count;
}

//====================================================
// 📌 修正點：將 setup() 置於正確的生命週期位置
//====================================================
void setup() {
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
void loop() {
    handleSerialCommand();
    connectWiFiNonBlocking();
    connectMQTTNonBlocking();

    if(mqttClient.connected()) mqttClient.loop();

    if (bleRxBuffer.length() > 0) {
        if ((millis() - lastBleRxTime > BLE_PACKET_TIMEOUT) || (bleRxBuffer.indexOf('\n') >= 0)) {
            bleRxBuffer.trim(); handleCommand(bleRxBuffer); bleRxBuffer = ""; 
        }
    }

    if(!scale.is_ready()) { 
        hx711Ready = false;
        hx711_just_recovered = true;
        unsigned long now = millis();
        if(now - lastHX711TryTime > HX711_RETRY_INTERVAL) {
            lastHX711TryTime = now;
            logPrint("⚠️ [硬體錯誤] 找不到 HX711 晶片。正在嘗試重連...");
            scale.begin(HX711_DT, HX711_SCK);
            scale.set_scale(calibration_factor);
        }
        delay(200); return; 
    }

    long raw = scale.read(); 
    if (raw == -1) {
        hx711Ready = false;
        hx711_just_recovered = true;
        unsigned long now = millis();
        if(now - lastHX711TryTime > HX711_RETRY_INTERVAL) {
            lastHX711TryTime = now;
            logPrint("❌ [數據異常] 偵測到 RAW = -1！訊號中斷保護中。");
        }
        delay(200); return;
    }

    hx711Ready = true;
    float weight = scale.get_units(1); 
    weight = movingAverage(weight);

    // 斷線恢復防禦機制
    if (hx711_just_recovered) {
        last_reported_weight = weight;
        last_loop_weight = weight;
        if (weight < EMPTY_LIMIT) currentState = STATE_IDLE;
        else if (weight < MODE_CUP_THRESHOLD) currentState = BOX_SETTLED;
        else currentState = CUP_SETTLED;
        hx711_just_recovered = false;
        logPrint("🔄 [硬體恢復] 重新同步系統狀態為: " + getStateString(currentState) + " 重量: " + String(weight, 2) + "g");
    }

    // 激進濾波同步
    if (abs(weight - last_loop_weight) > 15.0) {
        forceSyncFilterBuffer(weight);
        logPrint("⚡【動態加速】偵測到重量劇烈變動，瞬間同步濾波緩衝區。");
    }

    // 輸出動態 Log 串流
    String fullSerialLine = "RAW = " + String(raw) + 
                            "    Weight = " + String(weight, 2) + 
                            " g    [Base: " + String(last_reported_weight, 2) + 
                            " g]   [State: " + getStateString(currentState) + "]";
    Serial.println(fullSerialLine);
    if(mqttClient.connected()) {
        mqttClient.publish(TOPIC_SERIAL_RAW, fullSerialLine.c_str(), true);
    }

    // ====================================================
    // 🔥 核心有限狀態機 (FSM) 核心移轉邏輯
    // ====================================================
    
    // 【動態瞬時事件捕捉】：第一時間捕捉拿起趨勢（不等待 stable）
    if (currentState == CUP_SETTLED && (last_reported_weight - weight) > 10.0) {
        weight_before_pickup = last_reported_weight;
        currentState = CUP_PICKED_UP;
        logPrint("🥛【狀態移轉】水杯被拿起！鎖定飲水前重量: " + String(weight_before_pickup, 2) + "g");
    }
    else if (currentState == BOX_SETTLED && (last_reported_weight - weight) > 1.50) {
        weight_before_pickup = last_reported_weight;
        currentState = BOX_PICKED_UP;
        logPrint("📦【狀態移轉】藥盒被拿起！鎖定拿藥前重量: " + String(weight_before_pickup, 2) + "g");
    }

    // 【動態瞬時事件捕捉】：拿起途中如果重量直接跌破空秤門檻，代表已完全移開秤盤
    if (currentState == CUP_PICKED_UP && weight <= EMPTY_LIMIT) {
        currentState = CUP_WAIT_RETURN;
        logPrint("🥛【狀態移轉】水杯已完全離開秤台！進入 WAIT_FOR_RETURN 鎖定狀態。");
    }
    if (currentState == BOX_PICKED_UP && weight <= EMPTY_LIMIT) {
        currentState = BOX_WAIT_RETURN;
        logPrint("📦【狀態移轉】藥盒已完全離開秤台！進入 WAIT_FOR_RETURN 鎖定狀態。");
    }

    // ⚡【2026 重大流暢度修正：跨模式瞬時強切機制（不等待 stable，加入微分防禦）】
    if (currentState == BOX_WAIT_RETURN && weight >= MODE_CUP_THRESHOLD) {
        logPrint("⚡【瞬時異常強切】藥盒模式下直接放回重物 (" + String(weight, 2) + "g)，拒絕結算，秒切至水杯模式！");
        forceSyncFilterBuffer(weight); 
        last_reported_weight = weight;
        currentState = CUP_SETTLED;
        weight_before_pickup = 0.0;
        stable_count = 0;
    }
    else if (currentState == CUP_WAIT_RETURN && weight > EMPTY_LIMIT && weight < MODE_CUP_THRESHOLD) {
        // 🛡️【加入正向斜率微分防禦】
        if ((weight - last_loop_weight) < 5.0) {
            logPrint("⚡【瞬時異常強切】水杯模式下確認放回輕物 (" + String(weight, 2) + "g)，拒絕結算，秒切至藥盒模式！");
            forceSyncFilterBuffer(weight);
            last_reported_weight = weight;
            currentState = BOX_SETTLED;
            weight_before_pickup = 0.0;
            stable_count = 0;
        } else {
            logPrint("⏳【動態攔截】偵測到重量快速上升中 (" + String(weight, 2) + "g)，判定為水杯放回過渡期，暫緩強切。");
        }
    }

    // 穩定度計數器累積
    if (abs(weight - last_loop_weight) < STABLE_NOISE_LIMIT) { 
        stable_count++;
    } else {
        stable_count = 0; 
    }

    // 【穩定狀態事件處理】：當數據滿足靜態判定條件
    if (stable_count >= STABLE_THRESHOLD) {
        stable_count = 0; 

        switch (currentState) {
            
            case STATE_IDLE:
                last_reported_weight = weight;
                if (weight >= MODE_CUP_THRESHOLD) {
                    currentState = CUP_SETTLED;
                    last_reported_weight = weight;
                    logPrint("🔄【新物體置入】偵測到重物放上，自動識別為：水杯模式。Base: " + String(weight, 2) + "g");
                } 
                else if (weight >= EMPTY_LIMIT) {
                    currentState = BOX_SETTLED;
                    last_reported_weight = weight;
                    logPrint("🔄【新物體置入】偵測到輕物放上，自動識別為：藥盒模式。Base: " + String(weight, 2) + "g");
                }
                break;

            case CUP_SETTLED:
                if (last_reported_weight - weight >= WAT_THRESHOLD) {
                    float direct_consumed = last_reported_weight - weight;
                    appPrint("【智慧紀錄】偵測到放置飲水（吸管模式）！單次減少: " + String(direct_consumed, 2) + " cc");
                    if(mqttClient.connected()) {
                        char msg[16]; dtostrf(direct_consumed, 0, 2, msg); 
                        mqttClient.publish(TOPIC_WAT_TAKEN, msg, true);
                    }
                    last_reported_weight = weight;
                }
                else if (weight - last_reported_weight > WATER_ADD_THRESHOLD) {
                    logPrint("🥛【基準校準】偵測到秤上水杯就地加水，更新基準重: " + String(weight, 2) + "g");
                    last_reported_weight = weight;
                }
                else if (abs(weight - last_reported_weight) < 1.00) {
                    last_reported_weight = weight;
                }
                break;

            case CUP_PICKED_UP:
                if (weight <= EMPTY_LIMIT) {
                    currentState = CUP_WAIT_RETURN;
                    logPrint("🥛【狀態移轉】水杯於 PICKED_UP 穩定於零點，切換至: CUP_WAIT_RETURN");
                }
                break;

            case CUP_WAIT_RETURN:
                if (weight <= EMPTY_LIMIT) {
                    logPrint("⏳【空秤維持】秤盤依然處於空秤範圍 (" + String(weight, 2) + "g)，持續等待水杯放回...");
                } 
                else {
                    float consumed_wat = weight_before_pickup - weight;

                    if (abs(consumed_wat) <= NOISE_THRESHOLD) {
                        logPrint("🥛【精密結算】原水杯放回，水量無顯著變化。回復 CUP_SETTLED 狀態。");
                        last_reported_weight = weight;
                        currentState = CUP_SETTLED;
                    }
                    else if (consumed_wat >= WAT_THRESHOLD) {
                        appPrint("【智慧紀錄】偵測到拿起飲水！飲用量: " + String(consumed_wat, 2) + " cc");
                        if(mqttClient.connected()) {
                            char msg[16]; dtostrf(consumed_wat, 0, 2, msg); 
                            mqttClient.publish(TOPIC_WAT_TAKEN, msg, true);
                        }
                        last_reported_weight = weight;
                        currentState = CUP_SETTLED;
                    }
                    else if (consumed_wat < -WATER_ADD_THRESHOLD) {
                        logPrint("🥛【精密結算】偵測到水杯加水放回，主動更新基準重: " + String(weight, 2) + "g");
                        last_reported_weight = weight;
                        currentState = CUP_SETTLED;
                    }
                    else {
                        logPrint("🔄【異常放回】放回重量超越門檻但數值異常，重新校準水杯基準。");
                        last_reported_weight = weight;
                        currentState = CUP_SETTLED;
                    }
                    weight_before_pickup = 0.0; 
                }
                break;

            case BOX_SETTLED:
            {  
                float box_delta = last_reported_weight - weight;
                if (box_delta < -0.20) { 
                    last_reported_weight = weight;
                    logPrint("📦【基準校準】藥盒重量顯著增加，主動更新基準: " + String(weight, 2) + "g");
                }
                break;
            }

            case BOX_PICKED_UP:
            {  
                if (weight <= EMPTY_LIMIT) {
                    currentState = BOX_WAIT_RETURN;
                    logPrint("📦【狀態移轉】藥盒於 PICKED_UP 穩定於零點，切換至: BOX_WAIT_RETURN");
                }
                break;
            }

            case BOX_WAIT_RETURN:
            {  
                if (weight <= EMPTY_LIMIT) {
                    logPrint("⏳【空秤維持】秤盤依然處於空秤範圍 (" + String(weight, 2) + "g)，持續等待藥盒放回...");
                }
                else {
                    float consumed_med = weight_before_pickup - weight;
                    
                    if (consumed_med >= MED_THRESHOLD && weight_before_pickup > EMPTY_LIMIT) {
                        appPrint("【智慧紀錄】偵測服用藥物！單次減少: " + String(consumed_med, 2) + " g");
                        if(mqttClient.connected()) {
                            char msg[16]; dtostrf(consumed_med, 0, 2, msg); 
                            mqttClient.publish(TOPIC_MED_TAKEN, msg, true);
                        }
                        last_reported_weight = weight;
                        currentState = BOX_SETTLED;
                    } else {
                        logPrint("📦【精密結算】藥盒放回，重量無變化或未達吃藥門檻。");
                        last_reported_weight = weight;
                        currentState = BOX_SETTLED;
                    }
                    weight_before_pickup = 0.0; 
                }
                break;
            }
        }

        // 🚀【超全域安全閥】
        if (weight <= EMPTY_LIMIT && currentState != CUP_WAIT_RETURN && currentState != BOX_WAIT_RETURN) {
            if (currentState != STATE_IDLE) {
                currentState = STATE_IDLE;
                weight_before_pickup = 0.0;
                logPrint("🛡️【全域安全閥】秤盤回復完全空秤，回歸狀態: EMPTY_IDLE");
            }
        }
    }

    if ((currentState == CUP_WAIT_RETURN || currentState == BOX_WAIT_RETURN) && stable_count < STABLE_THRESHOLD) {
        // 攔截過渡跳動
    }

    last_loop_weight = weight; 

    // MQTT 定時發送
    if(mqttClient.connected()) {
        char rawText[20]; sprintf(rawText, "%ld", raw);
        mqttClient.publish(TOPIC_RAW, rawText, true);

        float display_weight = weight;
        if (enBeautiful) {
            if (currentState == CUP_WAIT_RETURN || currentState == BOX_WAIT_RETURN) display_weight = 0.00;
            if (abs(display_weight) < DISPLAY_DEADBAND) display_weight = 0.00;
        } else {
            if (abs(display_weight) < DISPLAY_DEADBAND) display_weight = 0.00;
        }

        char weightText[20]; dtostrf(display_weight, 0, 2, weightText);
        mqttClient.publish(TOPIC_WEIGHT, weightText, true);
    }

    delay(150); 
}

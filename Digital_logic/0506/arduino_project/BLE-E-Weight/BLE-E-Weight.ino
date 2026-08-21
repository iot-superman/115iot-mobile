/*
 * | Arduino IDE 設定   | 建議                                      |
| ---------------- | --------------------------------------- |
| Board            | **ESP32S3 Dev Module**                  |
| USB CDC On Boot  | **Enabled**                             |
| CPU Frequency    | **240MHz (WiFi)**                       |
| Core Debug Level | **None**                                |
| Flash Mode       | **QIO 80MHz**                           |
| Partition Scheme | **Default**                             |
| Upload Speed     | **921600**；失敗再改 **460800** 或 **115200** |
| Port             | 選插入 ESP32-S3 後出現的 **COMx**              |

 */
//====================================================
// 2026 完整穩定版 - 終極修正版 v4（修復藥盒放回偵測問題）
// 修正：新增動態放回偵測，解決緩慢取藥導致狀態卡死
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
// 核心參數 - 調整門檻值
//====================================================
const float MED_THRESHOLD = 0.25;      
const float WAT_THRESHOLD = 3.00;      
const float EMPTY_LIMIT = 7.00;        
const float MODE_CUP_THRESHOLD = 30.00;  // 水杯識別門檻（>30g 視為水杯）
const float MODE_BOX_THRESHOLD = 10.00;  // 藥盒識別門檻（>10g 視為藥盒）
const float DISPLAY_DEADBAND = 0.10;   
const float STABLE_NOISE_LIMIT = 0.06; 
const int   STABLE_THRESHOLD = 12;     
const float NOISE_THRESHOLD = 3.00;    
const float WATER_ADD_THRESHOLD = 8.00;

// 🆕 藥盒放回偵測參數
const float BOX_RETURN_THRESHOLD = 1.80;   // 放回偵測門檻（低於拿起前重量 1.8g 視為放回）
const int   BOX_RETURN_STABLE_COUNT = 8;    // 需連續穩定幾次才算放回
const unsigned long BOX_PICKUP_TIMEOUT = 45000; // 拿起超時強制重置 (45秒)

bool AUTODET_ONCE = true;              
bool isModeLocked = false;             

enum ScaleState {
    STATE_IDLE,
    CUP_SETTLED,
    CUP_PICKED_UP,
    CUP_WAIT_RETURN,
    BOX_SETTLED,
    BOX_PICKED_UP,
    BOX_WAIT_RETURN
};

ScaleState currentState = STATE_IDLE;

//====================================================
// FSM 模式列舉
//====================================================
enum FsmMode {
    FSM_MODE_AUTO = 0,
    FSM_MODE_FORCE_CUP = 1,
    FSM_MODE_FORCE_BOX = 2
};
FsmMode currentFsmMode = FSM_MODE_AUTO;

//====================================================
// NVS 記憶體
//====================================================
Preferences preferences;
const char* DEFAULT_WIFI_SSID = "thmrb306";
const char* DEFAULT_WIFI_PASSWORD = "thmrbthmrb";
String currentSSID = "";
String currentPassword = "";

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

#define BLE_DEVICE_NAME "ESP32S3_SCALE"
#define SERVICE_UUID           "6E400001-B5A3-F393-E0A9-E50E24DCCA9E"
#define RX_CHARACTERISTIC_UUID "6E400002-B5A3-F393-E0A9-E50E24DCCA9E"
#define TX_CHARACTERISTIC_UUID "6E400003-B5A3-F393-E0A9-E50E24DCCA9E"

BLECharacteristic* txCharacteristic = nullptr;
bool bleDeviceConnected = false;
String bleRxBuffer = "";
unsigned long lastBleRxTime = 0;
const unsigned long BLE_PACKET_TIMEOUT = 50; 

float calibration_factor =  387.2;
const int AVG_SIZE = 8;
float avgBuffer[AVG_SIZE];
int avgIndex = 0;
bool avgReady = false;

float last_reported_weight = 0; 
float last_loop_weight = 0;      
int stable_count = 0;            
bool hx711_just_recovered = false; 
float weight_before_pickup = 0.0; 
bool pickup_triggered = false;    

// 🆕 藥盒放回偵測專用變數
float boxPickupWeight = 0.0;           // 記錄拿起瞬間的重量
int boxReturnStableCounter = 0;        // 放回穩定計數器
unsigned long boxPickupStartTime = 0;  // 拿起開始時間

String serialInput = "";
unsigned long lastWiFiTryTime = 0;
unsigned long lastMQTTTryTime = 0;
unsigned long lastHX711TryTime = 0;
const unsigned long WIFI_RETRY_INTERVAL = 10000;
const unsigned long MQTT_RETRY_INTERVAL = 5000;  
const unsigned long HX711_RETRY_INTERVAL = 2000; 

wl_status_t lastWiFiStatus = WL_DISCONNECTED;
bool wifiConnecting = false;

//====================================================
// 工具函式
//====================================================
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

void logPrint(String text) {
    Serial.println(text);
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

String getWiFiStatusString(wl_status_t status) {
    switch (status) {
        case WL_IDLE_STATUS:     return "WL_IDLE_STATUS";
        case WL_NO_SSID_AVAIL:   return "WL_NO_SSID_AVAIL";
        case WL_SCAN_COMPLETED:  return "WL_SCAN_COMPLETED";
        case WL_CONNECTED:       return "WL_CONNECTED";
        case WL_CONNECT_FAILED:  return "WL_CONNECT_FAILED";
        case WL_CONNECTION_LOST: return "WL_CONNECTION_LOST";
        case WL_DISCONNECTED:    return "WL_DISCONNECTED";
        default:                 return "UNKNOWN_STATUS";
    }
}

void forceSyncFilterBuffer(float target_weight) {
    for(int i = 0; i < AVG_SIZE; i++) avgBuffer[i] = target_weight;
    avgReady = true;
}

void executeTare() {
    if(scale.is_ready()) {
        appPrint("執行去皮歸零 (Tare)...");
        
        for (int i = 0; i < 5; i++) {
            scale.read();
            delay(50);
        }
        
        scale.tare(25); 
        delay(100);
        
        float current_w = 0;
        for (int i = 0; i < 8; i++) {
            current_w += scale.get_units(1);
            delay(20);
        }
        current_w = current_w / 8.0;
        
        if (abs(current_w) < MODE_BOX_THRESHOLD) current_w = 0.00;
        
        last_reported_weight = current_w;
        last_loop_weight = current_w;
        weight_before_pickup = 0.0;
        isModeLocked = false; 
        pickup_triggered = false;
        boxReturnStableCounter = 0;
        boxPickupStartTime = 0;
        forceSyncFilterBuffer(current_w);
        
        if (currentFsmMode == FSM_MODE_FORCE_CUP) {
            currentState = CUP_SETTLED;
            isModeLocked = true;
        } else if (currentFsmMode == FSM_MODE_FORCE_BOX) {
            currentState = BOX_SETTLED;
            isModeLocked = true;
        } else {
            if (abs(current_w) < MODE_BOX_THRESHOLD) {
                currentState = STATE_IDLE;
            }
            else if (current_w >= MODE_CUP_THRESHOLD) { 
                currentState = CUP_SETTLED; 
                if(AUTODET_ONCE) isModeLocked = true; 
            }
            else if (current_w >= MODE_BOX_THRESHOLD) {
                currentState = BOX_SETTLED; 
                if(AUTODET_ONCE) isModeLocked = true; 
            }
        }
        hx711_just_recovered = false;
        appPrint("去皮完成。目前狀態: " + getStateString(currentState) + " 基準重: " + String(current_w, 2) + "g");
    }
}

void executeClear() {
    appPrint("執行紀錄清零 (Clear)...");
    weight_before_pickup = 0.0;
    pickup_triggered = false;
    boxReturnStableCounter = 0;
    boxPickupStartTime = 0;
    if(mqttClient.connected()) {
        mqttClient.publish(TOPIC_MED_TAKEN, "0", true); 
        mqttClient.publish(TOPIC_WAT_TAKEN, "0", true);  
        appPrint("吃藥與飲水數據已重置歸零。");
    } else {
        appPrint("錯誤：MQTT 未連線，無法同步網頁端。");
    }
}

void executeForceCup() {
    if(!scale.is_ready()) return;
    float current_w = scale.get_units(5);
    forceSyncFilterBuffer(current_w);
    last_reported_weight = current_w;
    last_loop_weight = current_w;
    weight_before_pickup = 0.0;
    pickup_triggered = false;
    boxReturnStableCounter = 0;
    boxPickupStartTime = 0;
    isModeLocked = true;
    currentFsmMode = FSM_MODE_FORCE_CUP;
    currentState = CUP_SETTLED;
    appPrint("🎮 [人工強制] 系統死鎖為【水杯模式】！基準: " + String(current_w, 2) + "g");
}

void executeForceBox() {
    if(!scale.is_ready()) return;
    float current_w = scale.get_units(5);
    forceSyncFilterBuffer(current_w);
    last_reported_weight = current_w;
    last_loop_weight = current_w;
    weight_before_pickup = 0.0;
    pickup_triggered = false;
    boxReturnStableCounter = 0;
    boxPickupStartTime = 0;
    isModeLocked = true;
    currentFsmMode = FSM_MODE_FORCE_BOX;
    currentState = BOX_SETTLED;
    appPrint("🎮 [人工強制] 系統死鎖為【藥盒模式】！基準: " + String(current_w, 2) + "g");
}

void executeResetAuto() {
    AUTODET_ONCE = true;
    isModeLocked = false;
    currentFsmMode = FSM_MODE_AUTO;
    pickup_triggered = false;
    boxReturnStableCounter = 0;
    boxPickupStartTime = 0;
    if(scale.is_ready()) {
        float current_w = scale.get_units(5);
        if (abs(current_w) < MODE_BOX_THRESHOLD) {
            currentState = STATE_IDLE;
        }
        else if (current_w >= MODE_CUP_THRESHOLD) {
            currentState = CUP_SETTLED;
        }
        else if (current_w >= MODE_BOX_THRESHOLD) {
            currentState = BOX_SETTLED;
        }
    }
    appPrint("🔄 [指令回復] 已重新啟用 智慧單次自動識別。系統現在是 [UNLOCKED] 狀態");
}

void loadWiFiFromNVS() {
    preferences.begin("wifi", false);
    currentSSID = preferences.getString("ssid", DEFAULT_WIFI_SSID);
    currentPassword = preferences.getString("pwd", DEFAULT_WIFI_PASSWORD);
    preferences.end();
    Serial.println("📂 [NVS 讀取] 目前快取的 WiFi 名稱: " + currentSSID);
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
    
    Serial.println("📨 [指令接收] " + input);
    
    if(input == "tare")     { executeTare(); return; }
    if(input == "clear")    { executeClear(); return; }
    if(input == "forcecup") { executeForceCup(); return; }
    if(input == "forcebox") { executeForceBox(); return; }
    if(input == "auto" || input == "resetauto" || input == "unlock" || input == "reset") { 
        executeResetAuto(); 
        return; 
    }

    int colonIndex = input.indexOf(':');
    if(colonIndex <= 0) {
        Serial.println("⚠️ [指令錯誤] 未知指令: " + input);
        return;
    }

    currentSSID = input.substring(0, colonIndex);
    currentPassword = input.substring(colonIndex + 1);
    currentSSID.trim(); currentPassword.trim();
    
    saveWiFiToNVS();
    appPrint("💾 [NVS 儲存成功] 新 WiFi 寫入！SSID: " + currentSSID + "，準備重新嘗試連線...");
    
    if(mqttClient.connected()) {
        mqttClient.disconnect();
        delay(100);
    }
    
    WiFi.disconnect(true, true);
    delay(300);
    
    lastWiFiStatus = WL_DISCONNECTED;
    wifiConnecting = false;
    lastWiFiTryTime = 0;
    
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());
    wifiConnecting = true;
    Serial.println("📡 [WiFi] 已發起新連線到: " + currentSSID);
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
            if(serialInput.length() > 0) {
                Serial.print("📨 [Serial 接收] >>> ");
                Serial.println(serialInput);
                handleCommand(serialInput);
                serialInput = "";
            }
        } else { 
            serialInput += c; 
        }
    }
}

//====================================================
// BLE 設定
//====================================================
class MyServerCallbacks : public BLEServerCallbacks {
    void onConnect(BLEServer* server) { bleDeviceConnected = true; bleRxBuffer = ""; Serial.println("📱 [BLE] 手機已連線到本裝置。"); }
    void onDisconnect(BLEServer* server) { bleDeviceConnected = false; delay(300); BLEDevice::startAdvertising(); Serial.println("📱 [BLE] 手機已斷開連線，重新廣播中..."); }
};

class MyRXCallbacks : public BLECharacteristicCallbacks {
    void onWrite(BLECharacteristic* characteristic) {
        String rxValue = characteristic->getValue();
        if (rxValue.length() > 0) {
            bleRxBuffer += rxValue;
            lastBleRxTime = millis();
        }
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
    Serial.println("📡 [BLE] 初始化完成，藍牙廣播名稱: " BLE_DEVICE_NAME);
}

//====================================================
// WiFi/MQTT 連線
//====================================================
void connectWiFiNonBlocking() {
    wl_status_t currentStatus = WiFi.status();
    
    if (currentStatus != lastWiFiStatus) {
        Serial.println("🌐 [WiFi 狀態變更] " + getWiFiStatusString(lastWiFiStatus) + " ➡️ " + getWiFiStatusString(currentStatus));
        if (currentStatus == WL_CONNECTED) {
            appPrint("🎉 [WiFi 連線成功] IP: " + WiFi.localIP().toString());
            wifiConnecting = false;
        } else if (currentStatus == WL_CONNECT_FAILED || currentStatus == WL_NO_SSID_AVAIL || currentStatus == WL_CONNECTION_LOST || currentStatus == WL_DISCONNECTED) {
            wifiConnecting = false;
        }
        lastWiFiStatus = currentStatus;
    }
    
    if(currentStatus == WL_CONNECTED) return;
    if(wifiConnecting || currentStatus == WL_IDLE_STATUS || currentStatus == WL_SCAN_COMPLETED) return;
    
    unsigned long now = millis();
    if(now - lastWiFiTryTime < WIFI_RETRY_INTERVAL) return;
    
    lastWiFiTryTime = now;
    WiFi.disconnect(true);
    delay(100);
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());
    wifiConnecting = true;
}

void connectMQTTNonBlocking() {
    if(WiFi.status() != WL_CONNECTED) return;
    if(mqttClient.connected()) return;
    
    unsigned long now = millis();
    if(now - lastMQTTTryTime < MQTT_RETRY_INTERVAL) return;
    lastMQTTTryTime = now;
    
    String clientId = "ESP32S3-"; clientId += String(random(0xffff), HEX);
    if(mqttClient.connect(clientId.c_str())) {
        mqttClient.subscribe(TOPIC_CMD);
        appPrint("MQTT 成功連線！");
    }
}

void WiFiEvent(WiFiEvent_t event, WiFiEventInfo_t info) {
    switch(event) {
        case ARDUINO_EVENT_WIFI_STA_GOT_IP: Serial.print("✅ [WiFi 事件] 已獲取 IP: "); Serial.println(WiFi.localIP()); break;
        case ARDUINO_EVENT_WIFI_STA_DISCONNECTED: wifiConnecting = false; break;
        default: break;
    }
}

//====================================================
// HX711 初始化
//====================================================
void initHX711NonBlocking() {
    scale.begin(HX711_DT, HX711_SCK);
    scale.set_scale(calibration_factor); 
    
    unsigned long startTime = millis();
    while(!scale.is_ready()) {
        handleSerialCommand();
        if(millis() - startTime > 5000) {
            hx711Ready = false;
            appPrint("⚠️ HX711 初始化超時！請檢查接線");
            return;
        }
        delay(10);
    }
    
    hx711Ready = true;
    appPrint("⚖️ 正在進行開機精準去皮歸零 (30次採樣)...");
    scale.tare(30); 
    
    float current_w = scale.get_units(5);
    
    // 開機防虛擬雜訊安全鎖
    if (abs(current_w) < MODE_BOX_THRESHOLD) {
        current_w = 0.00;
        currentState = STATE_IDLE;
        isModeLocked = false;
    } else if (current_w >= MODE_CUP_THRESHOLD) {
        currentState = CUP_SETTLED;
        if(AUTODET_ONCE) isModeLocked = true;
    } else if (current_w >= MODE_BOX_THRESHOLD) {
        currentState = BOX_SETTLED;
        if(AUTODET_ONCE) isModeLocked = true;
    }
    
    last_reported_weight = current_w;
    last_loop_weight = current_w;
    weight_before_pickup = 0.0;
    pickup_triggered = false;
    boxReturnStableCounter = 0;
    boxPickupStartTime = 0;
    forceSyncFilterBuffer(current_w);
    
    hx711_just_recovered = false;
    appPrint("✅ HX711 開機歸零完成！目前基準重量: " + String(current_w, 2) + "g 狀態: " + getStateString(currentState));
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
// setup()
//====================================================
void setup() {
    Serial.begin(115200);
    delay(1000);
    Serial.println("\n--- [ESP32S3 系統啟動] ---");
    
    WiFi.onEvent(WiFiEvent);
    loadWiFiFromNVS();
    setupBLE();
    mqttClient.setServer(MQTT_SERVER, MQTT_PORT);
    mqttClient.setCallback(mqttCallback);
    initHX711NonBlocking();
    
    WiFi.begin(currentSSID.c_str(), currentPassword.c_str());
    wifiConnecting = true;
    lastWiFiTryTime = millis();
}

//====================================================
// loop()
//====================================================
void loop() {
    handleSerialCommand();
    connectWiFiNonBlocking();
    connectMQTTNonBlocking();
    if(mqttClient.connected()) mqttClient.loop();

    // BLE 資料處理
    if (bleRxBuffer.length() > 0) {
        int colonIndex = bleRxBuffer.indexOf(':');
        if (colonIndex >= 0 && (millis() - lastBleRxTime > 500)) {
            handleCommand(bleRxBuffer);
            bleRxBuffer = "";
        }
        else if (colonIndex == -1) {
            String lowerBuffer = bleRxBuffer; lowerBuffer.toLowerCase(); lowerBuffer.trim();
            if (lowerBuffer == "auto" || lowerBuffer == "forcecup" || lowerBuffer == "forcebox" || 
                lowerBuffer == "resetauto" || lowerBuffer == "tare" || lowerBuffer == "clear" ||
                lowerBuffer == "unlock" || lowerBuffer == "reset") {
                handleCommand(bleRxBuffer);
                bleRxBuffer = "";
            }
        }
        if (bleRxBuffer.length() > 64 || (millis() - lastBleRxTime > 5000)) bleRxBuffer = "";
    }

    // HX711 檢查
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
        delay(200); 
        return; 
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
        delay(200); 
        return;
    }

    hx711Ready = true;
    float weight = scale.get_units(1); 
    
    // 防突波污染滑動平均緩衝區
    if (abs(weight - last_loop_weight) > 15.0 && abs(weight) < 5000.0) {
        forceSyncFilterBuffer(weight);
        logPrint("⚡【動態加速】偵測到重量變動，瞬間同步濾波緩衝區。");
    }
    
    weight = movingAverage(weight);

    //====================================================
    // HX711 斷線恢復髒數據清洗
    //====================================================
    if (hx711_just_recovered) {
        logPrint("🔄 [硬體恢復中] 偵測到晶片重新連線，啟動髒數據清洗防線...");
        
        for (int i = 0; i < 10; i++) {
            scale.read(); 
            delay(50);
        }
        
        delay(200);
        
        logPrint("⚖️ 重新執行深度去皮歸零 (25次採樣)...");
        scale.tare(25);
        delay(100);
        
        float check_w = 0;
        for (int i = 0; i < 8; i++) {
            check_w += scale.get_units(1);
            delay(20);
        }
        check_w = check_w / 8.0;
        
        if (abs(check_w) < MODE_BOX_THRESHOLD) { 
            appPrint("🚨 [情況 A 保護解鎖] 恢復重量 " + String(check_w, 2) + "g 為虛擬雜訊！強制歸零...");
            scale.tare(15);
            check_w = 0.00;        
            currentState = STATE_IDLE; 
            isModeLocked = false;      
        } else if (check_w >= MODE_CUP_THRESHOLD) {
            currentState = CUP_SETTLED; 
            if(AUTODET_ONCE) isModeLocked = true;
            logPrint("🔄 [硬體恢復] 秤面確有放置水杯，同步狀態為: CUP_SETTLED，重量: " + String(check_w, 2) + "g");
        } else if (check_w >= MODE_BOX_THRESHOLD) {
            currentState = BOX_SETTLED; 
            if(AUTODET_ONCE) isModeLocked = true;
            logPrint("🔄 [硬體恢復] 秤面確有放置藥盒，同步狀態為: BOX_SETTLED，重量: " + String(check_w, 2) + "g");
        }
        
        weight = check_w;
        last_reported_weight = check_w;
        last_loop_weight = check_w;
        pickup_triggered = false;
        boxReturnStableCounter = 0;
        boxPickupStartTime = 0;
        forceSyncFilterBuffer(check_w); 
        
        hx711_just_recovered = false;
        logPrint("✅ [防線布署完成] 系統已重新定錨。目前狀態: " + getStateString(currentState) + "，重量: " + String(weight, 2) + "g");
    }

    // 強制模式狀態保護
    if (currentFsmMode == FSM_MODE_FORCE_CUP) {
        if (currentState == STATE_IDLE) {
            currentState = CUP_SETTLED;
            logPrint("🔄【強制保護】水杯模式：強制切換回 CUP_SETTLED");
        }
    } else if (currentFsmMode == FSM_MODE_FORCE_BOX) {
        if (currentState == STATE_IDLE) {
            currentState = BOX_SETTLED;
            logPrint("🔄【強制保護】藥盒模式：強制切換回 BOX_SETTLED");
        }
    }

    // 每秒 Log 輸出
    static unsigned long lastLogTime = 0;
    if (millis() - lastLogTime > 1000) {
        lastLogTime = millis();
        String lockStatusStr = isModeLocked ? "[LOCKED]" : "[UNLOCKED]";
        String modeStr = (currentFsmMode == FSM_MODE_FORCE_CUP) ? "FORCE_CUP" :
                        (currentFsmMode == FSM_MODE_FORCE_BOX) ? "FORCE_BOX" : "AUTO";
        String fullSerialLine = "RAW = " + String(raw) + 
                                "    Weight = " + String(weight, 2) + 
                                " g    [Base: " + String(last_reported_weight, 2) + 
                                " g]   [State: " + getStateString(currentState) + 
                                "] " + modeStr + " " + lockStatusStr;
        Serial.println(fullSerialLine);
        if(mqttClient.connected()) {
            mqttClient.publish(TOPIC_SERIAL_RAW, fullSerialLine.c_str(), true);
        }
    }

    //====================================================
    // 狀態移轉（拿起偵測）- 改良版
    //====================================================
    if (!pickup_triggered) {
        if (currentState == CUP_SETTLED && (last_reported_weight - weight) > 10.0) {
            weight_before_pickup = last_reported_weight;
            currentState = CUP_PICKED_UP;
            pickup_triggered = true;
            boxPickupWeight = 0.0;
            boxPickupStartTime = 0;
            logPrint("🥛【狀態移轉】水杯被拿起！鎖定飲水前重量: " + String(weight_before_pickup, 2) + "g");
        }
        else if (currentState == BOX_SETTLED && (last_reported_weight - weight) > 1.50) {
            weight_before_pickup = last_reported_weight;
            boxPickupWeight = weight;
            boxPickupStartTime = millis();
            currentState = BOX_PICKED_UP;
            pickup_triggered = true;
            boxReturnStableCounter = 0;
            logPrint("📦【狀態移轉】藥盒被拿起！鎖定拿藥前重量: " + String(weight_before_pickup, 2) + "g");
        }
    }

    // 如果使用者沒完全拿起（空秤前就放回），重置鎖
    if ((currentState == CUP_PICKED_UP || currentState == BOX_PICKED_UP) && weight > EMPTY_LIMIT) {
        pickup_triggered = false;
    }

    //====================================================
    // 穩定偵測
    //====================================================
    if (abs(weight - last_loop_weight) < STABLE_NOISE_LIMIT) { 
        stable_count++;
    } else {
        stable_count = 0; 
    }

    //====================================================
    // 穩定後結算（含藥盒放回加強偵測）
    //====================================================
    if (stable_count >= STABLE_THRESHOLD) {
        stable_count = 0; 

        switch (currentState) {
            case STATE_IDLE:
                // 空秤狀態防溫漂/防髒訊號強定錨
                if (abs(weight) < MODE_BOX_THRESHOLD) {
                    weight = 0.00;
                    last_reported_weight = 0.00;
                    forceSyncFilterBuffer(0.00); 
                } else {
                    last_reported_weight = weight;
                }
                
                if (currentFsmMode == FSM_MODE_FORCE_CUP) { 
                    currentState = CUP_SETTLED; 
                    break; 
                }
                if (currentFsmMode == FSM_MODE_FORCE_BOX) { 
                    currentState = BOX_SETTLED; 
                    break; 
                }
                
                // 自動識別：根據重量判斷是水杯還是藥盒
                if (weight >= MODE_CUP_THRESHOLD) {
                    currentState = CUP_SETTLED;
                    last_reported_weight = weight;
                    if(AUTODET_ONCE) { isModeLocked = true; }
                    logPrint("🔄【單次識別】偵測到水杯！重量: " + String(weight, 2) + "g");
                }
                else if (weight >= MODE_BOX_THRESHOLD) {
                    currentState = BOX_SETTLED;
                    last_reported_weight = weight;
                    if(AUTODET_ONCE) { isModeLocked = true; }
                    logPrint("🔄【單次識別】偵測到藥盒！重量: " + String(weight, 2) + "g");
                }
                break;

            case CUP_SETTLED:
                if (last_reported_weight - weight >= WAT_THRESHOLD) {
                    float direct_consumed = last_reported_weight - weight;
                    appPrint("【智慧紀錄】吸管模式飲水！單次減少: " + String(direct_consumed, 2) + " cc");
                    if(mqttClient.connected()) {
                        char msg[16]; dtostrf(direct_consumed, 0, 2, msg); 
                        mqttClient.publish(TOPIC_WAT_TAKEN, msg, true);
                    }
                    last_reported_weight = weight;
                }
                else if (weight - last_reported_weight > WATER_ADD_THRESHOLD) {
                    logPrint("🥛【基準校準】水杯就地加水，更新基準: " + String(weight, 2) + "g");
                    last_reported_weight = weight;
                }
                else if (abs(weight - last_reported_weight) < 1.00) {
                    last_reported_weight = weight;
                }
                break;

            case CUP_PICKED_UP:
                if (weight <= EMPTY_LIMIT) {
                    currentState = CUP_WAIT_RETURN;
                    pickup_triggered = false;
                } else if (weight > EMPTY_LIMIT) {
                    pickup_triggered = false;
                }
                break;

            case CUP_WAIT_RETURN:
                if (weight > EMPTY_LIMIT) {
                    float consumed_wat = weight_before_pickup - weight;
                    if (weight_before_pickup <= EMPTY_LIMIT) {
                        last_reported_weight = weight;
                        currentState = CUP_SETTLED;
                    }
                    else if (abs(consumed_wat) <= NOISE_THRESHOLD) {
                        last_reported_weight = weight;
                        currentState = CUP_SETTLED;
                    }
                    else if (consumed_wat >= WAT_THRESHOLD) {
                        appPrint("【智慧紀錄】水杯飲水結算！飲用量: " + String(consumed_wat, 2) + " cc");
                        if(mqttClient.connected()) {
                            char msg[16]; dtostrf(consumed_wat, 0, 2, msg); 
                            mqttClient.publish(TOPIC_WAT_TAKEN, msg, true);
                        }
                        last_reported_weight = weight;
                        currentState = CUP_SETTLED;
                    }
                    else if (consumed_wat < -WATER_ADD_THRESHOLD) {
                        last_reported_weight = weight;
                        currentState = CUP_SETTLED;
                    }
                    else {
                        last_reported_weight = weight;
                        currentState = CUP_SETTLED;
                    }
                    weight_before_pickup = 0.0;
                    pickup_triggered = false;
                }
                break;

            case BOX_SETTLED:
                // 強制模式：如果重量突然歸零（異常），不改變狀態
                if (currentFsmMode == FSM_MODE_FORCE_BOX && weight <= EMPTY_LIMIT) {
                    break;
                }
                if ((last_reported_weight - weight) < -0.20) { 
                    last_reported_weight = weight;
                }
                break;

            //====================================================
            // 🆕 BOX_PICKED_UP - 加強放回偵測
            //====================================================
            case BOX_PICKED_UP:
                // 原有邏輯：完全離開秤台
                if (weight <= EMPTY_LIMIT) {
                    currentState = BOX_WAIT_RETURN;
                    pickup_triggered = false;
                    boxReturnStableCounter = 0;
                    boxPickupStartTime = 0;
                    logPrint("📦【狀態移轉】藥盒已完全離開秤台！進入 WAIT_FOR_RETURN。");
                    break;
                }
                
                // 🆕 動態放回偵測（重量回升且穩定）
                if (weight > boxPickupWeight + 0.30 && weight > MODE_BOX_THRESHOLD) {
                    float weightDiff = weight_before_pickup - weight;
                    
                    // 如果重量回升到接近拿起前，且誤差在合理範圍內
                    if (weightDiff >= 0.0 && weightDiff <= BOX_RETURN_THRESHOLD) {
                        boxReturnStableCounter++;
                        if (boxReturnStableCounter >= BOX_RETURN_STABLE_COUNT) {
                            // 確實放回了，結算藥物
                            float consumed_med = weight_before_pickup - weight;
                            if (consumed_med >= MED_THRESHOLD) {
                                appPrint("【智慧紀錄】藥物服用結算！減少: " + String(consumed_med, 2) + " g");
                                if(mqttClient.connected()) {
                                    char msg[16]; dtostrf(consumed_med, 0, 2, msg); 
                                    mqttClient.publish(TOPIC_MED_TAKEN, msg, true);
                                }
                            } else {
                                logPrint("📦【動態放回】藥盒已放回，藥物無顯著減少: " + String(consumed_med, 2) + "g");
                            }
                            last_reported_weight = weight;
                            currentState = BOX_SETTLED;
                            pickup_triggered = false;
                            boxReturnStableCounter = 0;
                            boxPickupStartTime = 0;
                            logPrint("📦【動態放回】藥盒已放回！重量: " + String(weight, 2) + "g");
                            break;
                        }
                    } else {
                        // 重量回升但不在合理範圍，可能是加水或其他操作
                        boxReturnStableCounter = 0;
                    }
                } else {
                    // 重量還在下降或持平，重置計數器
                    boxReturnStableCounter = 0;
                }
                
                // 🆕 安全閥：如果拿起太久沒放回（超過設定時間），強制重置
                if (boxPickupStartTime > 0 && (millis() - boxPickupStartTime > BOX_PICKUP_TIMEOUT)) {
                    logPrint("⚠️【安全閥】藥盒拿起超過 " + String(BOX_PICKUP_TIMEOUT/1000) + " 秒，強制重置狀態");
                    last_reported_weight = weight;
                    currentState = BOX_SETTLED;
                    pickup_triggered = false;
                    boxReturnStableCounter = 0;
                    boxPickupStartTime = 0;
                }
                break;

            //====================================================
            // BOX_WAIT_RETURN - 優化放回偵測
            //====================================================
            case BOX_WAIT_RETURN:
                if (weight > EMPTY_LIMIT) {
                    float consumed_med = weight_before_pickup - weight;
                    
                    // 🆕 檢查是否真的放回了藥盒（重量回升到合理範圍）
                    if (weight >= MODE_BOX_THRESHOLD && abs(consumed_med) <= BOX_RETURN_THRESHOLD + 0.50) {
                        if (consumed_med >= MED_THRESHOLD) {
                            appPrint("【智慧紀錄】藥物服用結算！減少: " + String(consumed_med, 2) + " g");
                            if(mqttClient.connected()) {
                                char msg[16]; dtostrf(consumed_med, 0, 2, msg); 
                                mqttClient.publish(TOPIC_MED_TAKEN, msg, true);
                            }
                        }
                        last_reported_weight = weight;
                        currentState = BOX_SETTLED;
                        logPrint("📦【放回偵測】藥盒已放回！重量: " + String(weight, 2) + "g");
                    }
                    // 原本的結算邏輯（保留）
                    else if (consumed_med >= MED_THRESHOLD && weight_before_pickup > EMPTY_LIMIT) {
                        appPrint("【智慧紀錄】藥物服用結算！減少: " + String(consumed_med, 2) + " g");
                        if(mqttClient.connected()) {
                            char msg[16]; dtostrf(consumed_med, 0, 2, msg); 
                            mqttClient.publish(TOPIC_MED_TAKEN, msg, true);
                        }
                        last_reported_weight = weight;
                        currentState = BOX_SETTLED;
                    }
                    else if (abs(consumed_med) < 0.50) {
                        last_reported_weight = weight;
                        currentState = BOX_SETTLED;
                    }
                    else {
                        last_reported_weight = weight;
                        currentState = BOX_SETTLED;
                    }
                    weight_before_pickup = 0.0;
                    pickup_triggered = false;
                    boxReturnStableCounter = 0;
                    boxPickupStartTime = 0;
                }
                break;

            default:
                break;
        }
    }

    last_loop_weight = weight;

    // 網頁數據發布 - 直接發送實際重量
    if(mqttClient.connected()) {
        char rawText[20]; sprintf(rawText, "%ld", raw);
        mqttClient.publish(TOPIC_RAW, rawText, true);

        float display_weight = weight;
        if (abs(display_weight) < DISPLAY_DEADBAND) display_weight = 0.00;

        char weightText[20]; 
        dtostrf(display_weight, 0, 2, weightText);
        mqttClient.publish(TOPIC_WEIGHT, weightText, true);
    }

    delay(150);
}

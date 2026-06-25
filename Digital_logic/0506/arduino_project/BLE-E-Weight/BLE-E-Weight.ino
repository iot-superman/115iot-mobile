#include <WiFi.h>
#include <PubSubClient.h>
#include <HX711.h>
#include <Preferences.h>

#include <BLEDevice.h>
#include <BLEServer.h>
#include <BLEUtils.h>
#include <BLE2902.h>

//====================================================
// HX711 腳位
//====================================================
#define HX711_DT   6
#define HX711_SCK  5

HX711 scale;

bool hx711Ready = false;

//====================================================
// NVS 儲存 WiFi
//====================================================
Preferences preferences;

const char* DEFAULT_WIFI_SSID = "thmrb306";
const char* DEFAULT_WIFI_PASSWORD = "thmrbthmrb";

String currentSSID = "";
String currentPassword = "";

//====================================================
// MQTT
//====================================================
const char* MQTT_SERVER = "mqttgo.io";
const int MQTT_PORT = 1883;

const char* TOPIC_WEIGHT = "esp32/weight";
const char* TOPIC_RAW = "esp32/raw";
const char* TOPIC_CMD = "esp32/cmd";

WiFiClient espClient;
PubSubClient mqttClient(espClient);

//====================================================
// BLE UART
//====================================================
#define BLE_DEVICE_NAME "ESP32S3_SCALE"

#define SERVICE_UUID           "6E400001-B5A3-F393-E0A9-E50E24DCCA9E"
#define RX_CHARACTERISTIC_UUID "6E400002-B5A3-F393-E0A9-E50E24DCCA9E"
#define TX_CHARACTERISTIC_UUID "6E400003-B5A3-F393-E0A9-E50E24DCCA9E"

BLECharacteristic* txCharacteristic = nullptr;

bool bleDeviceConnected = false;

//====================================================
// 電子秤參數
//====================================================
float calibration_factor = 387.2;

long zeroOffset = 0;

//====================================================
// 移動平均
//====================================================
const int AVG_SIZE = 8;

float avgBuffer[AVG_SIZE];

int avgIndex = 0;

bool avgReady = false;

//====================================================
// Serial 輸入
//====================================================
String serialInput = "";

//====================================================
// 重連時間
//====================================================
unsigned long lastWiFiTryTime = 0;
unsigned long lastMQTTTryTime = 0;
unsigned long lastHX711TryTime = 0;

const unsigned long WIFI_RETRY_INTERVAL = 5000;
const unsigned long MQTT_RETRY_INTERVAL = 5000;
const unsigned long HX711_RETRY_INTERVAL = 2000;

//====================================================
// BLE + Serial 同時輸出
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
// 讀取 WiFi NVS
//====================================================
void loadWiFiFromNVS()
{
    preferences.begin("wifi", false);

    currentSSID =
        preferences.getString(
            "ssid",
            DEFAULT_WIFI_SSID
        );

    currentPassword =
        preferences.getString(
            "pwd",
            DEFAULT_WIFI_PASSWORD
        );

    Serial.println();
    Serial.println("========== LOAD WIFI ==========");

    Serial.print("SSID : ");
    Serial.println(currentSSID);

    Serial.println("Password : ********");
}

//====================================================
// 儲存 WiFi NVS
//====================================================
void saveWiFiToNVS()
{
    preferences.putString(
        "ssid",
        currentSSID
    );

    preferences.putString(
        "pwd",
        currentPassword
    );

    appPrint("WiFi setting saved to NVS.");
}

//====================================================
// 套用 WiFi 指令
// 格式：SSID:PASSWORD
//====================================================
void applyWiFiCommand(String input)
{
    input.trim();

    int colonIndex =
        input.indexOf(':');

    if(colonIndex <= 0)
    {
        appPrint("格式錯誤，請輸入：SSID:PASSWORD");
        return;
    }

    currentSSID =
        input.substring(
            0,
            colonIndex
        );

    currentPassword =
        input.substring(
            colonIndex + 1
        );

    currentSSID.trim();
    currentPassword.trim();

    if(currentSSID.length() == 0)
    {
        appPrint("SSID 不可空白");
        return;
    }

    Serial.println();
    Serial.println("========== WIFI SET ==========");

    Serial.print("New SSID : ");
    Serial.println(currentSSID);

    Serial.println("New Password : ********");

    saveWiFiToNVS();

    appPrint("WiFi reconnecting...");

    if(mqttClient.connected())
    {
        mqttClient.disconnect();
    }

    WiFi.disconnect(true);

    delay(300);

    WiFi.mode(WIFI_STA);

    WiFi.begin(
        currentSSID.c_str(),
        currentPassword.c_str()
    );

    lastWiFiTryTime = millis();
}

//====================================================
// BLE Server Callback
//====================================================
class MyServerCallbacks : public BLEServerCallbacks
{
    void onConnect(BLEServer* server)
    {
        bleDeviceConnected = true;

        Serial.println("BLE Connected");
    }

    void onDisconnect(BLEServer* server)
    {
        bleDeviceConnected = false;

        Serial.println("BLE Disconnected");

        delay(300);

        BLEDevice::startAdvertising();
    }
};

//====================================================
// BLE RX Callback
//====================================================
class MyRXCallbacks : public BLECharacteristicCallbacks
{
    void onWrite(BLECharacteristic* characteristic)
    {
        String rxValue =
            characteristic->getValue().c_str();

        rxValue.trim();

        if(rxValue.length() > 0)
        {
            Serial.println();
            Serial.println("========== BLE RX ==========");

            Serial.print("BLE CMD : ");
            Serial.println(rxValue);

            applyWiFiCommand(rxValue);
        }
    }
};

//====================================================
// BLE 初始化
//====================================================
void setupBLE()
{
    BLEDevice::init(BLE_DEVICE_NAME);

    BLEServer* server =
        BLEDevice::createServer();

    server->setCallbacks(
        new MyServerCallbacks()
    );

    BLEService* service =
        server->createService(
            SERVICE_UUID
        );

    txCharacteristic =
        service->createCharacteristic(
            TX_CHARACTERISTIC_UUID,
            BLECharacteristic::PROPERTY_NOTIFY
        );

    txCharacteristic->addDescriptor(
        new BLE2902()
    );

    txCharacteristic->setValue("BLE TX Ready");

    BLECharacteristic* rxCharacteristic =
        service->createCharacteristic(
            RX_CHARACTERISTIC_UUID,
            BLECharacteristic::PROPERTY_WRITE |
            BLECharacteristic::PROPERTY_WRITE_NR
        );

    rxCharacteristic->setValue("");

    rxCharacteristic->setCallbacks(
        new MyRXCallbacks()
    );

    service->start();

    BLEAdvertising* advertising =
        BLEDevice::getAdvertising();

    advertising->addServiceUUID(
        SERVICE_UUID
    );

    advertising->setScanResponse(true);

    BLEDevice::startAdvertising();

    Serial.println("BLE UART Started");

    Serial.print("BLE Name : ");
    Serial.println(BLE_DEVICE_NAME);
}

//====================================================
// MQTT Callback
//====================================================
void mqttCallback(
    char* topic,
    byte* payload,
    unsigned int length
)
{
    String message;

    for(unsigned int i = 0; i < length; i++)
    {
        message += (char)payload[i];
    }

    Serial.println();
    Serial.println("========== MQTT ==========");

    Serial.print("Topic : ");
    Serial.println(topic);

    Serial.print("Message : ");
    Serial.println(message);

    if(message == "tare")
    {
        if(scale.is_ready())
        {
            Serial.println("Remote Tare...");

            scale.tare();

            delay(300);

            zeroOffset =
                scale.read_average(30);

            Serial.print("New Offset = ");
            Serial.println(zeroOffset);
        }
        else
        {
            Serial.println("HX711 not ready, tare ignored.");
        }
    }
}

//====================================================
// Serial 指令
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
                Serial.println("========== SERIAL RX ==========");

                Serial.print("Serial CMD : ");
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
// WiFi 非阻塞
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
    Serial.println("WiFi Trying...");

    Serial.print("SSID : ");
    Serial.println(currentSSID);

    WiFi.disconnect();

    WiFi.mode(WIFI_STA);

    WiFi.begin(
        currentSSID.c_str(),
        currentPassword.c_str()
    );
}

//====================================================
// MQTT 非阻塞
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
    Serial.println("MQTT Trying...");

    String clientId = "ESP32S3-";

    clientId +=
        String(
            random(0xffff),
            HEX
        );

    if(mqttClient.connect(clientId.c_str()))
    {
        Serial.println("MQTT Connected");

        mqttClient.subscribe(TOPIC_CMD);

        Serial.print("Subscribe : ");
        Serial.println(TOPIC_CMD);
    }
    else
    {
        Serial.print("MQTT Failed rc=");
        Serial.println(mqttClient.state());
    }
}

//====================================================
// HX711 初始化，不可卡死
//====================================================
void initHX711NonBlocking()
{
    Serial.println("HX711 Init...");

    scale.begin(
        HX711_DT,
        HX711_SCK
    );

    unsigned long startTime =
        millis();

    while(!scale.is_ready())
    {
        handleSerialCommand();

        if(millis() - startTime > 3000)
        {
            hx711Ready = false;

            Serial.println("HX711 not ready.");
            Serial.println("Scale offline, WiFi/BLE still available.");

            return;
        }

        delay(10);
    }

    hx711Ready = true;

    Serial.println("Tare...");

    scale.tare();

    delay(300);

    zeroOffset =
        scale.read_average(30);

    Serial.print("Zero Offset = ");
    Serial.println(zeroOffset);

    Serial.println("HX711 Ready");
}

//====================================================
// HX711 重新偵測
//====================================================
void checkHX711Reconnect()
{
    if(hx711Ready)
    {
        return;
    }

    unsigned long now =
        millis();

    if(now - lastHX711TryTime < HX711_RETRY_INTERVAL)
    {
        return;
    }

    lastHX711TryTime = now;

    if(scale.is_ready())
    {
        Serial.println();
        Serial.println("HX711 detected.");

        hx711Ready = true;

        Serial.println("Tare...");

        scale.tare();

        delay(300);

        zeroOffset =
            scale.read_average(30);

        Serial.print("Zero Offset = ");
        Serial.println(zeroOffset);

        Serial.println("HX711 Ready");
    }
}

//====================================================
// 移動平均
//====================================================
float movingAverage(float value)
{
    avgBuffer[avgIndex] =
        value;

    avgIndex++;

    if(avgIndex >= AVG_SIZE)
    {
        avgIndex = 0;

        avgReady = true;
    }

    int count =
        avgReady
        ? AVG_SIZE
        : avgIndex;

    float sum = 0;

    for(int i = 0; i < count; i++)
    {
        sum +=
            avgBuffer[i];
    }

    return sum / count;
}

//====================================================
// 自動零點
//====================================================
void autoZero(float weight)
{
    if(abs(weight) < 0.5 && scale.is_ready())
    {
        long raw =
            scale.read_average(5);

        zeroOffset =
            (
                zeroOffset * 9999
                + raw
            )
            / 10000;
    }
}

//====================================================
// Setup
//====================================================
void setup()
{
    Serial.begin(115200);

    delay(1000);

    Serial.println();
    Serial.println("HX711 MQTT BLE SCALE");
    Serial.println("USB Serial / BLE Format:");
    Serial.println("SSID:PASSWORD");

    loadWiFiFromNVS();

    setupBLE();

    WiFi.mode(WIFI_STA);

    WiFi.begin(
        currentSSID.c_str(),
        currentPassword.c_str()
    );

    Serial.println();
    Serial.println("WiFi Start, scale can run offline.");

    mqttClient.setServer(
        MQTT_SERVER,
        MQTT_PORT
    );

    mqttClient.setCallback(
        mqttCallback
    );

    initHX711NonBlocking();

    Serial.println("System Ready");
}

//====================================================
// Loop
//====================================================
void loop()
{
    //------------------------------------------------
    // Serial 永遠最先處理
    //------------------------------------------------
    handleSerialCommand();

    //------------------------------------------------
    // WiFi / MQTT 永遠不能卡住
    //------------------------------------------------
    connectWiFiNonBlocking();

    connectMQTTNonBlocking();

    if(mqttClient.connected())
    {
        mqttClient.loop();
    }

    //------------------------------------------------
    // HX711 若原本沒成功，持續重新偵測
    //------------------------------------------------
    checkHX711Reconnect();

    //------------------------------------------------
    // HX711 沒 ready，不影響 BLE / Serial / WiFi
    //------------------------------------------------
    if(!hx711Ready || !scale.is_ready())
    {
        Serial.println("HX711 OFFLINE    WiFi/BLE config available.");

        delay(500);

        return;
    }

    //------------------------------------------------
    // RAW
    //------------------------------------------------
    long raw =
        scale.read_average(10);

    //------------------------------------------------
    // Weight
    //------------------------------------------------
    float weight =
        (
            raw
            - zeroOffset
        )
        / calibration_factor;

    weight =
        movingAverage(weight);

    autoZero(weight);

    if(abs(weight) < 0.3)
    {
        weight = 0;
    }

    //------------------------------------------------
    // Serial 輸出
    //------------------------------------------------
    Serial.print("RAW = ");
    Serial.print(raw);

    Serial.print("    Weight = ");
    Serial.print(weight, 2);

    Serial.print(" g");

    if(WiFi.status() == WL_CONNECTED)
    {
        Serial.print("    WiFi OK");
    }
    else
    {
        Serial.print("    WiFi OFFLINE");
    }

    if(mqttClient.connected())
    {
        Serial.print("    MQTT OK");
    }
    else
    {
        Serial.print("    MQTT OFFLINE");
    }

    Serial.println();

    //------------------------------------------------
    // MQTT 發送
    //------------------------------------------------
    if(mqttClient.connected())
    {
        char rawText[20];

        sprintf(
            rawText,
            "%ld",
            raw
        );

        mqttClient.publish(
            TOPIC_RAW,
            rawText,
            true
        );

        char weightText[20];

        dtostrf(
            weight,
            0,
            2,
            weightText
        );

        mqttClient.publish(
            TOPIC_WEIGHT,
            weightText,
            true
        );
    }

    delay(200);
}

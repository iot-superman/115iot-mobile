#include <ESP8266WiFi.h>
#include <WiFiClient.h>

// ========================================
// 老師版 AM2302 感測器 Library
// ========================================
#include <AM2302-Sensor.h>

// ========================================
// 【新增】老師版 Timer
// ESP8266 使用 Ticker
// ========================================
#include <Ticker.h>


// ========================================
// 建立 AM2302 感測器
// GPIO4 = NodeMCU D2
// ========================================
AM2302::AM2302_Sensor am2302(4);


// ========================================
// Wi-Fi
// ========================================
const char* ssid = "thmrb311";
const char* password = "thmrbthmrb";


// ========================================
// Wi-Fi 狀態
// ========================================
int status = WL_IDLE_STATUS;


// ========================================
// WiFi Client
// ========================================
WiFiClient client;

const int http_port = 80;


// ========================================
// 【新增】Timer
// ========================================
Ticker timer;


// ========================================
// 【修正】
// 你原本：
// voliate Booleaon setFlag=true;
//
// 正確應該建立 readFlag
// volatile：因為這個變數會由 Timer callback 修改
// ========================================
volatile boolean readFlag = true;


// ========================================
// ThingSpeak
// ========================================
char serverName_thingspeak[] = "api.thingspeak.com";

char sendData_write[] =
    "GET https://api.thingspeak.com/update?api_key=GI5MENZRYXUAZY04&field1=36&field2=46 HTTP/1.1\r\n";

char sendData_1[] =
    "Host:api.thingspeak.com \r\n";

char sendData_2[] =
    "Connection: close\r\n\r\n";


// ========================================
// 【新增】Timer callback
//
// 每次 Timer 時間到，就把 readFlag 設成 true
//
// 注意：
// callback 裡面不要直接讀 DHT22，
// 只設定 Flag，真正讀取放在 loop()
// ========================================
void setFlag()
{
    readFlag = true;
}


// ========================================
// setup()
// ========================================
void setup()
{
    Serial.begin(115200);

    pinMode(2, OUTPUT);
    digitalWrite(2, LOW);


    // ========================================
    // Wi-Fi 連線
    // ========================================
    WiFi.begin(ssid, password);

    Serial.println("");


    while (WiFi.status() != WL_CONNECTED)
    {
        delay(500);

        Serial.print(".");
    }


    // ========================================
    // Wi-Fi 連線成功
    // ========================================
    Serial.println("");

    Serial.print("Connected to :");

    Serial.println(ssid);

    Serial.print("IP address : ");

    Serial.println(WiFi.localIP());


    // ========================================
    // 【老師圖片】
    // 原本 client_send() 先註解
    // ========================================
    // client_send();


    // ========================================
    // 【老師圖片新增】
    //
    // 每 6 秒執行一次 setFlag()
    //
    // setFlag()：
    // readFlag = true;
    //
    // ========================================
    timer.attach(6.0, setFlag);
}


// ========================================
// loop()
// ========================================
void loop()
{
    // ========================================
    // 【老師圖片新增】
    //
    // Timer 每 6 秒：
    //
    // setFlag()
    //     ↓
    // readFlag = true
    //     ↓
    // 進入這個 if
    //
    // ========================================
    if (readFlag)
    {
        // ====================================
        // 讀取開始後立刻清除 Flag
        // 等下一次 Timer 再設定 true
        // ====================================
        readFlag = false;


        // ====================================
        // 讀取 AM2302 / DHT22
        // ====================================
        int status = am2302.read();


        // ====================================
        // status == 0
        // 表示 AM2302 讀取成功
        // ====================================
        if (status == 0)
        {
            // =================================
            // 取得溫度
            // =================================
            float t = am2302.get_Temperature();


            // =================================
            // 取得溼度
            // =================================
            float h = am2302.get_Humidity();


            // =================================
            // 顯示 DHT22 資料
            // =================================
            Serial.println("DHT 22 data");


            // =================================
            // 顯示溫度
            // =================================
            Serial.print("temp = ");

            Serial.println(t);


            // =================================
            // 顯示溼度
            // =================================
            Serial.print("hum = ");

            Serial.println(h);


            Serial.println();

        } // end of status

    } // end of readFlag
}


// ========================================
// 老師版 ThingSpeak 傳送函式
// ========================================
void client_send()
{
    Serial.println("Link to thingspeak");


    // ========================================
    // 連線 ThingSpeak
    // ========================================
    if (client.connect(serverName_thingspeak, http_port))
    {
        Serial.println("connected");


        // ====================================
        // 傳送 GET Request
        // ====================================
        client.print(sendData_write);

        delay(100);


        // ====================================
        // 傳送 Host
        // ====================================
        client.print(sendData_1);

        delay(100);


        // ====================================
        // 傳送 Connection: close
        // ====================================
        client.print(sendData_2);
    }


    delay(1000);

    client.stop();
}

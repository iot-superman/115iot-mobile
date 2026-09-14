#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>

// ========================================
// 載入老師的 HTML 網頁
// ========================================
#include "Switch_page.h"
#include "root.h"


// ========================================
// LED 腳位設定
// ========================================
#define LED1 D4
#define LED2 14
#define LED3 12
#define LED4 13
#define LED5 15


// ========================================
// Wi-Fi 設定
// ========================================
const char* ssid = "thmrb311";
const char* password = "thmrbthmrb";


// ========================================
// 建立 Web Server
// HTTP Port = 80
// ========================================
ESP8266WebServer server(80);


// ========================================
// 函式宣告
// ========================================
void handleRoot();
void handleLedOn();
void handleLedOff();
void handleLedOn_num();
void handleLedOff_num();
void controlLED(String ledNum, String ledStatus);
void handleLedOn1();
void handleLedOn2();
void handleLedOn3();
void handleLedOn4();
void handleLedOn5();
void handleLedOff1();
void handleLedOff2();
void handleLedOff3();
void handleLedOff4();
void handleLedOff5();


void setup() {

  Serial.begin(115200);


  // ========================================
  // LED1
  // ========================================
  pinMode(LED1, OUTPUT);
  digitalWrite(LED1, LOW);


  // ========================================
  // LED2
  // ========================================
  pinMode(LED2, OUTPUT);
  digitalWrite(LED2, HIGH);


  // ========================================
  // LED3
  // ========================================
  pinMode(LED3, OUTPUT);
  digitalWrite(LED3, HIGH);


  // ========================================
  // LED4
  // ========================================
  pinMode(LED4, OUTPUT);
  digitalWrite(LED4, HIGH);


  // ========================================
  // LED5
  // ========================================
  pinMode(LED5, OUTPUT);
  digitalWrite(LED5, HIGH);


  // ========================================
  // 等待 2 秒
  // ========================================
  delay(2000);


  // ========================================
  // 開機自測 (HIGH Active: HIGH=亮, LOW=熄)
  // 流程：
  //   1. 先全部熄滅
  //   2. LED1 → LED2 → LED3 → LED4 → LED5 依次亮起
  //   3. LED5 → LED4 → LED3 → LED2 → LED1 依次關閉
  // ========================================

  // 步驟 1：全部熄滅
  digitalWrite(LED1, LOW);
  digitalWrite(LED2, LOW);
  digitalWrite(LED3, LOW);
  digitalWrite(LED4, LOW);
  digitalWrite(LED5, LOW);
  delay(500);

  // 步驟 2：LED1 → LED5 依次亮起 (每顆間隔 500ms)
  digitalWrite(LED1, HIGH);
  delay(500);
  digitalWrite(LED2, HIGH);
  delay(500);
  digitalWrite(LED3, HIGH);
  delay(500);
  digitalWrite(LED4, HIGH);
  delay(500);
  digitalWrite(LED5, HIGH);
  delay(500);

  // 步驟 3：LED5 → LED1 依次關閉 (每顆間隔 500ms)
  digitalWrite(LED5, LOW);
  delay(500);
  digitalWrite(LED4, LOW);
  delay(500);
  digitalWrite(LED3, LOW);
  delay(500);
  digitalWrite(LED2, LOW);
  delay(500);
  digitalWrite(LED1, LOW);
  delay(500);


  // ========================================
  // 開始連接 Wi-Fi
  // ========================================
  WiFi.begin(ssid, password);

  Serial.println("");


  // ========================================
  // 等待 Wi-Fi 連線成功
  // ========================================
  while (WiFi.status() != WL_CONNECTED) {

    delay(500);

    Serial.print(".");
  }


  // ========================================
  // 顯示 Wi-Fi 連線資訊
  // ========================================
  Serial.println("");

  Serial.print("Connected to : ");
  Serial.println(ssid);

  Serial.print("IP address : ");
  Serial.println(WiFi.localIP());


  // ========================================
  // 設定首頁
  // 瀏覽器輸入：
  // http://ESP8266_IP/
  //
  // 就會呼叫 handleRoot()
  // ========================================
  server.on("/", handleRoot);

  // ========================================
  // 設定 LED1 開啟路由
  // 瀏覽器輸入：
  // http://ESP8266_IP/on
  //
  // 就會呼叫 handleLedOn()
  // ========================================
  server.on("/on", handleLedOn);

  // ========================================
  // 設定 LED1 關閉路由
  // 瀏覽器輸入：
  // http://ESP8266_IP/off
  //
  // 就會呼叫 handleLedOff()
  // ========================================
  server.on("/off", handleLedOff);

  // ========================================
  // 設定查詢參數路由（?led=N 寫法）
  // 瀏覽器輸入：
  // http://ESP8266_IP/ledon?led=3
  // http://ESP8266_IP/ledoff?led=5
  // ========================================
  server.on("/ledon", handleLedOn_num);
  server.on("/ledoff", handleLedOff_num);

  // ========================================
  // 設定 /on/1 ~ /on/5 路由
  // 瀏覽器輸入：
  // http://ESP8266_IP/on/1  → LED1 亮
  // http://ESP8266_IP/on/2  → LED2 亮
  // ...以此類推
  // ========================================
  server.on("/on/1", handleLedOn1);
  server.on("/on/2", handleLedOn2);
  server.on("/on/3", handleLedOn3);
  server.on("/on/4", handleLedOn4);
  server.on("/on/5", handleLedOn5);

  // ========================================
  // 設定 /off/1 ~ /off/5 路由
  // 瀏覽器輸入：
  // http://ESP8266_IP/off/1  → LED1 熄
  // http://ESP8266_IP/off/2  → LED2 熄
  // ...以此類推
  // ========================================
  server.on("/off/1", handleLedOff1);
  server.on("/off/2", handleLedOff2);
  server.on("/off/3", handleLedOff3);
  server.on("/off/4", handleLedOff4);
  server.on("/off/5", handleLedOff5);

  server.on("/switch", handleSwitch);
  // ========================================
  // 啟動 Web Server
  // ========================================
  server.begin();

  Serial.println("Node MCUHttp server start");
}


// ========================================
// 主迴圈
// ========================================
void loop() {

  // ========================================
  // 處理瀏覽器 HTTP Request
  // ========================================
  server.handleClient();
}
void handleSwitch()
{
  Serial.println("Switch control");
  if (server.method()==HTTP_POST){
    if (server.hasArg("led") && server.hasArg("state")) {
      String ledNum = server.arg("led");
      String ledStatus = server.arg("state");
      controlLED(ledNum, ledStatus);
      server.send(200, "text/html", "LED " + ledNum + " is " + ledStatus);
    } else {
      server.send(400, "text/html", "Error: missing led or state");
    }
  }else{
    Serial.println("show  switch page");
    String data = Switch_page;
    server.send(200,"text/html",data);
    
  }
}

// ========================================
// 查詢參數開燈 - /ledon?led=N
// ========================================
void handleLedOn_num()
{
  Serial.println("Led number on");
  if (server.method() == HTTP_GET) {
    if (server.hasArg("led")) {
      String ledNum = server.arg("led");
      String ledStatus = "on";
      controlLED(ledNum, ledStatus);
      server.send(200, "text/html", "LED on : " + ledNum);
    } else {
      server.send(200, "text/html", "Error: missing ?led=N");
    }
  }
}


// ========================================
// 查詢參數關燈 - /ledoff?led=N
// ========================================
void handleLedOff_num()
{
  Serial.println("Led number off");
  if (server.method() == HTTP_GET) {
    if (server.hasArg("led")) {
      String ledNum = server.arg("led");
      String ledStatus = "off";
      controlLED(ledNum, ledStatus);
      server.send(200, "text/html", "LED off : " + ledNum);
    } else {
      server.send(200, "text/html", "Error: missing ?led=N");
    }
  }
}


// ========================================
// 控制 LED 共用函式
// 所有 LED 皆是 HIGH = 亮、LOW = 熄（低態驅動）, but led1 low active
// ========================================
void controlLED(String ledNum, String ledStatus)
{
  int ledNo = ledNum.toInt();
  switch (ledNo) {
    case 1:
      if (ledStatus == "on")
        digitalWrite(LED1, LOW);
      else
        digitalWrite(LED1, HIGH);
      break;
    case 2:
      if (ledStatus == "on")
        digitalWrite(LED2, HIGH);
      else
        digitalWrite(LED2, LOW);
      break;
    case 3:
      if (ledStatus == "on")
        digitalWrite(LED3, HIGH);
      else
        digitalWrite(LED3, LOW);
      break;
    case 4:
      if (ledStatus == "on")
        digitalWrite(LED4, HIGH);
      else
        digitalWrite(LED4, LOW);
      break;
    case 5:
      if (ledStatus == "on")
        digitalWrite(LED5, HIGH);
      else
        digitalWrite(LED5, LOW);
      break;
  }
}


// ========================================
// LED1 開啟處理函式
// 瀏覽器輸入 http://ESP8266_IP/on
// ========================================
void handleLedOn()
{
  Serial.println("Led on");
  digitalWrite(LED1, LOW);
  server.send(200, "text/html", "Led 1 is on");
}


// ========================================
// LED1 關閉處理函式
// 瀏覽器輸入 http://ESP8266_IP/off
// ========================================
void handleLedOff()
{
  Serial.println("Led off");
  digitalWrite(LED1, HIGH);
  server.send(200, "text/html", "Led 1 is off");
}


// ========================================
// LED1 亮 - /on/1
// ========================================
void handleLedOn1()
{
  Serial.println("Led 1 on");
  digitalWrite(LED1, LOW);
  server.send(200, "text/html", "Led 1 is on");
}

// ========================================
// LED2 亮 - /on/2
// ========================================
void handleLedOn2()
{
  Serial.println("Led 2 on");
  digitalWrite(LED2, LOW);
  server.send(200, "text/html", "Led 2 is on");
}

// ========================================
// LED3 亮 - /on/3
// ========================================
void handleLedOn3()
{
  Serial.println("Led 3 on");
  digitalWrite(LED3, LOW);
  server.send(200, "text/html", "Led 3 is on");
}

// ========================================
// LED4 亮 - /on/4
// ========================================
void handleLedOn4()
{
  Serial.println("Led 4 on");
  digitalWrite(LED4, LOW);
  server.send(200, "text/html", "Led 4 is on");
}

// ========================================
// LED5 亮 - /on/5
// ========================================
void handleLedOn5()
{
  Serial.println("Led 5 on");
  digitalWrite(LED5, LOW);
  server.send(200, "text/html", "Led 5 is on");
}


// ========================================
// LED1 熄 - /off/1
// ========================================
void handleLedOff1()
{
  Serial.println("Led 1 off");
  digitalWrite(LED1, HIGH);
  server.send(200, "text/html", "Led 1 is off");
}

// ========================================
// LED2 熄 - /off/2
// ========================================
void handleLedOff2()
{
  Serial.println("Led 2 off");
  digitalWrite(LED2, HIGH);
  server.send(200, "text/html", "Led 2 is off");
}

// ========================================
// LED3 熄 - /off/3
// ========================================
void handleLedOff3()
{
  Serial.println("Led 3 off");
  digitalWrite(LED3, HIGH);
  server.send(200, "text/html", "Led 3 is off");
}

// ========================================
// LED4 熄 - /off/4
// ========================================
void handleLedOff4()
{
  Serial.println("Led 4 off");
  digitalWrite(LED4, HIGH);
  server.send(200, "text/html", "Led 4 is off");
}

// ========================================
// LED5 熄 - /off/5
// ========================================
void handleLedOff5()
{
  Serial.println("Led 5 off");
  digitalWrite(LED5, HIGH);
  server.send(200, "text/html", "Led 5 is off");
}


// ========================================
// 首頁處理函式
// 老師圖片第 70～75 行
// ========================================
void handleRoot()
{
  Serial.println("handleRoot connect\n");

  // ========================================
  // 從 root.h 取得網頁內容 (PROGMEM)
  // 使用 send_P 直接從 Flash 讀取，節省 RAM
  // ========================================
  server.send_P(200, "text/html", root_page);
}

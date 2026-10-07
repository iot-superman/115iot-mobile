/*
 * WebServer_1_Modified - ESP8266 NodeMCU
 * 依使用者原始 WebServer_1 改寫：維持全部原有 HTTP 路由，修正 LED 極性不一致，新增 /flash。
 * Arduino IDE: Tools > Board > NodeMCU 1.0 (ESP-12E Module)
 * 新增：依老師最新截圖改為 SoftwareSerial(GPIO4 RX, GPIO5 TX)，115200 bps。
 * STM32 USART3 接收到字元後仍需在 STM32 韌體呼叫相同命令處理邏輯。
 */
#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <SoftwareSerial.h>
#include "bluetooth.h"  // ESP8266 相容版：保留老師的單字元指令定義
#include "root.h"
#include "Switch_page.h"

// 原專案的 Wi-Fi 名稱；為避免將密碼散布到 ZIP，請在這裡自行填入。
const char* ssid = "thmrb311";
const char* password = "YOUR_WIFI_PASSWORD";
ESP8266WebServer server(80);

// D2(GPIO4) 是 RX，D1(GPIO5) 是 TX；9600 8N1 對應 STM32 USART3。
// Arduino/ESP8266 SoftwareSerial 建構子參數順序：RX, TX。
// ===== 2026-10-07 老師最新版：Arduino 軟體 UART =====
// 老師程式：#define rxPin 4、#define txPin 5
// NodeMCU GPIO4 = D2（RX），GPIO5 = D1（TX）。
// 注意：SoftwareSerial 順序是 (RX, TX)，請勿接反。
#define rxPin 4
#define txPin 5
SoftwareSerial mySerial(rxPin, txPin);

// 老師截圖使用 115200。STM32 USART3 也必須修改為相同鮑率。
// ESP8266 SoftwareSerial 在 115200 可能因 Wi-Fi 忙碌而漏資料；
// 如遇到亂碼或遺漏命令，請「兩邊一起」改成 9600。
static const uint32_t STM32_UART_BAUD = 115200;
static const char LED_ON_COMMANDS[5] = {Lamp1_on_cmd, Lamp2_on_cmd, Lamp3_on_cmd, Lamp4_on_cmd, Lamp5_on_cmd};
static const char LED_OFF_COMMANDS[5] = {Lamp1_off_cmd, Lamp2_off_cmd, Lamp3_off_cmd, Lamp4_off_cmd, Lamp5_off_cmd};

// LED1 為 NodeMCU 板載 D4/GPIO2，一般 LOW 亮；其餘依原本開機自測註解 HIGH 亮。
// 如果外接 LED 是從 3.3V 經電阻接至 GPIO（下拉點亮），改對應項目為 LOW。
#define LED1 D4
#define LED2 14
#define LED3 12
#define LED4 13
#define LED5 15
const uint8_t LED_PINS[5] = { LED1, LED2, LED3, LED4, LED5 };
const uint8_t LED_ON_LEVEL[5] = { LOW, HIGH, HIGH, HIGH, HIGH };
bool ledState[5] = { false, false, false, false, false };

// 使用 millis() 非阻塞式閃燈，HTTP 請求不會因 delay(1800) 而卡住。
const uint32_t FLASH_INTERVAL_MS = 300;
const uint8_t FLASH_TIMES = 3;
bool flashing = false;
bool flashSnapshot[5] = { false, false, false, false, false };
uint8_t flashTransitions = 0;
uint32_t lastFlashAt = 0;

// 新增：透過 UART 傳送老師的單字元控制指令（與 HC-06 命令表相容）。
// 每顆 LED 的開關操作，同步到 STM32；不附加 CR/LF。
void sendLampCommand(uint8_t index, bool on) {
  if (index >= 5) return;
  const char command = on ? LED_ON_COMMANDS[index] : LED_OFF_COMMANDS[index];
  mySerial.write((uint8_t)command);
  Serial.printf("[UART3 TX] LED%d %s -> %c\n", index + 1, on ? "ON" : "OFF", command);
}

// 僅這個函式直接操作 LED 輸出，避免原始程式中 HIGH/LOW 意義相反的問題。
void writeLED(uint8_t index, bool on) {
  if (index >= 5) return;
  ledState[index] = on;
  digitalWrite(LED_PINS[index], on ? LED_ON_LEVEL[index] :
               (LED_ON_LEVEL[index] == HIGH ? LOW : HIGH));
  sendLampCommand(index, on);
}

void writeAllLED(bool on) {
  for (uint8_t i = 0; i < 5; ++i) writeLED(i, on);
}

void cancelFlash() {
  flashing = false;
}

// 每個閃燈請求：亮 300ms、暗 300ms，共三輪；之後恢復請求前的狀態。
void startFlash() {
  if (flashing) {
    // 若正在閃爍，再次 /flash 不會覆蓋原先保存的狀態。
    return;
  }
  for (uint8_t i = 0; i < 5; ++i) flashSnapshot[i] = ledState[i];
  flashing = true;
  flashTransitions = 0;
  lastFlashAt = millis();
  writeAllLED(true);   // 第一次全部亮
  Serial.println(F("[FLASH] Start: all LEDs x3"));
}

void updateFlash() {
  if (!flashing || millis() - lastFlashAt < FLASH_INTERVAL_MS) return;
  lastFlashAt = millis();
  ++flashTransitions;
  if (flashTransitions >= FLASH_TIMES * 2) {
    flashing = false;
    for (uint8_t i = 0; i < 5; ++i) writeLED(i, flashSnapshot[i]);
    Serial.println(F("[FLASH] Finished; LED states restored"));
    return;
  }
  // 1 = 暗，2 = 亮，3 = 暗，4 = 亮，5 = 暗
  writeAllLED((flashTransitions % 2) == 0);
}

void sendText(int status, const String& body) {
  server.send(status, "text/plain; charset=utf-8", body);
}

bool parseLedNumber(int& number) {
  String value = server.hasArg("led") ? server.arg("led") : server.arg("num");
  if (value.length() != 1 || value[0] < '1' || value[0] > '5') return false;
  number = value[0] - '0';
  return true;
}

void handleRoot() {
  server.send_P(200, "text/html; charset=utf-8", root_page);
}

void handleLedOn() {
  cancelFlash();
  writeLED(0, true);
  Serial.println(F("[LED] LED1 ON"));
  sendText(200, "Led 1 is on");
}
void handleLedOff() {
  cancelFlash();
  writeLED(0, false);
  Serial.println(F("[LED] LED1 OFF"));
  sendText(200, "Led 1 is off");
}

void handleLedNumber(bool on, int number) {
  cancelFlash();
  if (number < 1 || number > 5) {
    sendText(400, "Invalid LED (must be 1..5)");
    return;
  }
  writeLED(number - 1, on);
  Serial.printf("[LED] LED%d %s\n", number, on ? "ON" : "OFF");
  sendText(200, "Led " + String(number) + (on ? " is on" : " is off"));
}

void handleLedOn_num() {
  int n;
  if (!parseLedNumber(n)) { sendText(400, "Use /ledon?led=1..5 (or ?num=1..5)"); return; }
  handleLedNumber(true, n);
}
void handleLedOff_num() {
  int n;
  if (!parseLedNumber(n)) { sendText(400, "Use /ledoff?led=1..5 (or ?num=1..5)"); return; }
  handleLedNumber(false, n);
}

// 保留老師原版的 GET /switch 顯示控制頁、POST /switch 操作指定 LED。
void handleSwitch() {
  if (server.method() == HTTP_GET) {
    server.send_P(200, "text/html; charset=utf-8", Switch_page);
    return;
  }
  if (!server.hasArg("led") || !server.hasArg("state")) {
    sendText(400, "Missing POST fields: led, state");
    return;
  }
  int n;
  if (!parseLedNumber(n)) { sendText(400, "Invalid LED number"); return; }
  String state = server.arg("state");
  state.toLowerCase();
  if (state != "on" && state != "off") {
    sendText(400, "state must be on or off");
    return;
  }
  handleLedNumber(state == "on", n);
}

void handleFlash() {
  if (flashing) {
    sendText(409, "FLASH already running");
    return;
  }
  startFlash();
  sendText(200, "FLASH started: all 5 LEDs blink 3 times");
}

// 新增：播放老師 bluetooth.h 所定義曲目。
// GET /song?num=0~5，傳出單一字元。
void handleSong() {
  if (!server.hasArg("num")) { sendText(400, "Use /song?num=0..5"); return; }
  String v = server.arg("num");
  if (v.length() != 1 || v[0] < '0' || v[0] > '5') {
    sendText(400, "Song number must be 0..5"); return;
  }
  mySerial.write((uint8_t)v[0]);
  Serial.printf("[UART3 TX] Song: %c\n", v[0]);
  sendText(200, "STM32 song command sent: " + v);
}

void handleStatus() {
  String json = "{\"ip\":\"" + WiFi.localIP().toString() + "\",\"flashing\":";
  json += flashing ? "true" : "false";
  json += ",\"leds\":[";
  for (uint8_t i = 0; i < 5; i++) {
    if (i) json += ',';
    json += ledState[i] ? "true" : "false";
  }
  json += "]}";
  server.send(200, "application/json; charset=utf-8", json);
}



// ============================================================
// 2026/10/07 老師最新版：/home 與 /home/song
// 截圖中的 handleHomeSong()：POST 參數名稱為 song，
// 直接把 songNum[0] 當成單一 ASCII 命令送到 STM32。
// 例如：song=1 -> 傳送字元 '1'。
// ============================================================
void handleHomeSong() {
  Serial.print("Song control");

  if (server.method() == HTTP_POST) {
    if (!server.hasArg("song")) {
      Serial.println(" missing song");
      server.send(400, "text/html", "Song play fail: missing song");
      return;
    }

    String songNum = server.arg("song");
    Serial.println(songNum);

    if (songNum.length() == 0) {
      server.send(400, "text/html", "Song play fail: empty song");
      return;
    }

    // 老師最新版核心：送出第一個字元，不附加 CR/LF。
    mySerial.write((uint8_t)songNum[0]);

    server.send(200, "text/html", "Song play ok: " + songNum);
  } else {
    server.send(200, "text/html", "Song play fail");
  }
}

void handleHome() {
  Serial.println("Home control");
  String data = "Home control link ok";
  server.send(200, "text/html", data);
}

void setup() {
  Serial.begin(115200);
  // 依老師版本初始化 UART；Serial 是 USB 除錯，mySerial 是 STM32。
  pinMode(rxPin, INPUT);
  pinMode(txPin, OUTPUT);
  mySerial.begin(STM32_UART_BAUD);
  delay(200);

  // 先設定輸出鎖存位準再設 pinMode，減少初始化 LED 閃動。
  for (uint8_t i = 0; i < 5; ++i) {
    uint8_t off = LED_ON_LEVEL[i] == HIGH ? LOW : HIGH;
    digitalWrite(LED_PINS[i], off);
    pinMode(LED_PINS[i], OUTPUT);
    ledState[i] = false;
  }
  // GPIO15(D8) 必須在上電／重置時保持 LOW，硬體不可外拉 HIGH。

  // 保留原課堂程式開機測試，但改成正確的各燈有效電位。
  Serial.println(F("[BOOT] LED sequential test"));
  for (uint8_t i = 0; i < 5; ++i) { writeLED(i, true); delay(200); }
  for (int i = 4; i >= 0; --i) { writeLED(i, false); delay(200); }

  Serial.printf("[UART] RX GPIO%d (D2), TX GPIO%d (D1), %lu bps\n", rxPin, txPin, (unsigned long)STM32_UART_BAUD);
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  Serial.print(F("Connecting Wi-Fi"));
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print('.');
  }
  Serial.printf("\nConnected to: %s\n", ssid);
  Serial.print(F("IP address: "));
  Serial.println(WiFi.localIP());

  server.on("/", HTTP_GET, handleRoot);
  server.on("/home", HTTP_GET, handleHome);
  server.on("/home/led", HTTP_GET, handleRoot);
  server.on("/home/song", HTTP_POST, handleHomeSong);
  server.on("/on", HTTP_GET, handleLedOn);
  server.on("/off", HTTP_GET, handleLedOff);
  server.on("/ledon", HTTP_GET, handleLedOn_num);
  server.on("/ledoff", HTTP_GET, handleLedOff_num);
  for (int n = 1; n <= 5; ++n) {
    // 使用 n 複製值捕捉，確保每條路由對應正確 LED。
    server.on(("/on/" + String(n)).c_str(), HTTP_GET, [n]() { handleLedNumber(true, n); });
    server.on(("/off/" + String(n)).c_str(), HTTP_GET, [n]() { handleLedNumber(false, n); });
  }
  server.on("/switch", HTTP_ANY, handleSwitch);
  server.on("/flash", HTTP_GET, handleFlash);
  server.on("/status", HTTP_GET, handleStatus);
  server.on("/song", HTTP_GET, handleSong);
  server.onNotFound([]() { sendText(404, "404 Not Found"); });
  server.begin();
  Serial.println(F("Node MCU HTTP server start"));
}

void loop() {
  server.handleClient();
  updateFlash();
  // 除錯：STM32 USART3 傳回字元時，顯示於 USB 序列埠監控視窗。
  while (mySerial.available()) Serial.write(mySerial.read());
}

/*
 * WebServer_1_Modified - ESP8266 NodeMCU
 * 依使用者原始 WebServer_1 改寫：維持全部原有 HTTP 路由，修正 LED 極性不一致，新增 /flash。
 * Arduino IDE: Tools > Board > NodeMCU 1.0 (ESP-12E Module)
 * 注意：此版本不包含 STM32 UART 功能；GPIO4/5 可留作後續 UART。
 */
#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include "root.h"
#include "Switch_page.h"

// 原專案的 Wi-Fi 名稱；為避免將密碼散布到 ZIP，請在這裡自行填入。
const char* ssid = "thmrb311";
const char* password = "YOUR_WIFI_PASSWORD";
ESP8266WebServer server(80);

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

// 僅這個函式直接操作 LED 輸出，避免原始程式中 HIGH/LOW 意義相反的問題。
void writeLED(uint8_t index, bool on) {
  if (index >= 5) return;
  ledState[index] = on;
  digitalWrite(LED_PINS[index], on ? LED_ON_LEVEL[index] :
               (LED_ON_LEVEL[index] == HIGH ? LOW : HIGH));
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

void setup() {
  Serial.begin(115200);
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
  server.on("/home", HTTP_GET, handleRoot);
  server.on("/home/led", HTTP_GET, handleRoot);
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
  server.onNotFound([]() { sendText(404, "404 Not Found"); });
  server.begin();
  Serial.println(F("Node MCU HTTP server start"));
}

void loop() {
  server.handleClient();
  updateFlash();
}

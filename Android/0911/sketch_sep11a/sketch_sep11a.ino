#include <ESP8266WiFi.h>
#include <WiFiClient.h>

// ==============================
// Wi-Fi 設定
// ==============================
const char* ssid = "thmrb311";
const char* password = "thmrbthmrb";

void setup() {

  // ==============================
  // 初始化序列埠
  // ==============================
  Serial.begin(115200);

  // ESP8266 NodeMCU GPIO2
  pinMode(2, OUTPUT);
  digitalWrite(2, LOW);

  // ==============================
  // 開始連線 Wi-Fi
  // ==============================
  WiFi.begin(ssid, password);

  Serial.println("");
  Serial.print("Connecting to WiFi");

  // 等待 Wi-Fi 連線完成
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  // ==============================
  // Wi-Fi 連線成功 n   
  // ==============================
  Serial.println("");
  Serial.print("WiFi connected to:");
  Serial.println(ssid);

  // 顯示 ESP8266 取得的 IP
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}

void loop() {

  // 目前沒有需要重複執行的程式

}

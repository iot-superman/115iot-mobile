#include <ESP8266WiFi.h>

// 請改成你的 WiFi 資訊
const char* ssid = "thmrb311";
const char* password = "thmrbthmrb";

void setup() {
  Serial.begin(115200);
  delay(10);

  Serial.println();
  Serial.println("開始連接 WiFi...");

  WiFi.begin(ssid, password);

  // 等待連線
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi 連線成功！");
  Serial.print("IP 位址: ");
  Serial.println(WiFi.localIP());
}

void loop() {
  Serial.println("Hello World");
  delay(10000);  // 每10秒顯示一次
}

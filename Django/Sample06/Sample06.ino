
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <DHT.h>

// ===== WiFi 設定 =====
const char* ssid = "thmrb311";
const char* password = "thmrbthmrb";

// ===== API 設定 =====
const char* serverUrl = "http://192.168.63.7:8080/add_temperature_API/";

// ===== DHT22 設定 =====
#define DHTPIN 4         // GPIO4 = NodeMCU D2
#define DHTTYPE DHT22

DHT dht(DHTPIN, DHTTYPE);

void connectWiFi() {
  WiFi.begin(ssid, password);
  Serial.print("正在連線 WiFi");

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println();
  Serial.println("WiFi 已連線");
  Serial.print("IP 位址: ");
  Serial.println(WiFi.localIP());
}

void setup() {
  Serial.begin(115200);
  delay(1000);

  dht.begin();
  connectWiFi();
}

void loop() {
  // 若 WiFi 斷線，自動重連
  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("WiFi 斷線，重新連線中...");
    connectWiFi();
  }

  float humidity = dht.readHumidity();
  float temperature = dht.readTemperature(); // 攝氏

  // 檢查是否讀取失敗
  if (isnan(humidity) || isnan(temperature)) {
    Serial.println("DHT22 讀取失敗，5 秒後重試");
    delay(5000);
    return;
  }

  Serial.print("溫度: ");
  Serial.print(temperature);
  Serial.print(" °C, 濕度: ");
  Serial.print(humidity);
  Serial.println(" %");

  WiFiClient client;
  HTTPClient http;

  http.begin(client, serverUrl);
  http.addHeader("Content-Type", "application/x-www-form-urlencoded");

  String postData = "sensor_id=2";
  postData += "&temperature=" + String(temperature, 2);
  postData += "&humidity=" + String(humidity, 2);

  int httpResponseCode = http.POST(postData);

  Serial.print("HTTP 回應碼: ");
  Serial.println(httpResponseCode);

  if (httpResponseCode > 0) {
    String response = http.getString();
    Serial.println("伺服器回應內容:");
    Serial.println(response);
  } else {
    Serial.print("POST 失敗，錯誤: ");
    Serial.println(http.errorToString(httpResponseCode));
  }

  http.end();

  // 每 10 秒送一次
  delay(10000);
}

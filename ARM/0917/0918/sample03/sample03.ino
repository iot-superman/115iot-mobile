#include "DHT.h"

#define DHTPIN 4        // GPIO04 (D2)
#define DHTTYPE DHT22   // 感測器型號

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(115200);
  Serial.println("DHT22 test");

  dht.begin();
}

void loop() {
  delay(2000);  // 每2秒讀一次

  float h = dht.readHumidity();
  float t = dht.readTemperature(); // 攝氏

  if (isnan(h) || isnan(t)) {
    Serial.println("讀取失敗！");
    return;
  }

  Serial.print("濕度: ");
  Serial.print(h);
  Serial.print(" %\t");

  Serial.print("溫度: ");
  Serial.print(t);
  Serial.println(" °C");
}

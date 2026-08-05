#include <DHT.h>

#define DHTPIN 4
#define DHTTYPE DHT22

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(115200);
  delay(1000);
  dht.begin();
}

void loop() {
  delay(2000);

  float h = dht.readHumidity();
  float t = dht.readTemperature();

  if (isnan(h) || isnan(t)) {
    Serial.println("讀取失敗！");
    return;
  }

  Serial.print("濕度: ");
  Serial.print(h);
  Serial.print("%  溫度: ");
  Serial.print(t);
  Serial.println("C");
}

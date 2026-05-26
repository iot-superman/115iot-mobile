// =====================================
// RGB LED 腳位
// D2 = R
// D3 = G
// D4 = B
// =====================================
int LedRGBPin[] = {2, 3, 4};

// =====================================
// 指標
// =====================================
int *prtLEDPin;

void setup() {

  // =====================================
  // 指向陣列第一個元素
  // =====================================
  prtLEDPin = LedRGBPin;

  Serial.begin(115200);

  Serial.println("RGB LED Control");

  // =====================================
  // 初始化 RGB LED
  // =====================================
  for (int i = 0; i < 3; i++) {

    pinMode(*(prtLEDPin + i), OUTPUT);

    digitalWrite(*(prtLEDPin + i), LOW);
  }
}

void loop() {

  // =====================================
  // R ON
  // =====================================
  digitalWrite(*(prtLEDPin + 0), HIGH);

  Serial.println("RED ON");

  delay(1000);

  digitalWrite(*(prtLEDPin + 0), LOW);

  // =====================================
  // G ON
  // =====================================
  digitalWrite(*(prtLEDPin + 1), HIGH);

  Serial.println("GREEN ON");

  delay(1000);

  digitalWrite(*(prtLEDPin + 1), LOW);

  // =====================================
  // B ON
  // =====================================
  digitalWrite(*(prtLEDPin + 2), HIGH);

  Serial.println("BLUE ON");

  delay(1000);

  digitalWrite(*(prtLEDPin + 2), LOW);

  for (int i=0;i<3;i++){

  // =====================================
  // RGB ALL ON
  // =====================================
  Serial.println("RGB ALL ON");

  for (int i = 0; i < 3; i++) {

    digitalWrite(*(prtLEDPin + i), HIGH);
  }

  // 全亮 1 秒
  delay(1000);

  // =====================================
  // RGB ALL OFF
  // =====================================
  Serial.println("RGB ALL OFF");
  

  for (int i = 0; i < 3; i++) {

    digitalWrite(*(prtLEDPin + i), LOW);
  }

  delay(500);
  }
}7

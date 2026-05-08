int LedPin[] = {2, 3, 4, 5, 6, 7, 8, 9};

int *p;

void setup() {

  // =========================
  // 指標 p 指向陣列第一個元素
  // =========================
  p = LedPin; // 修正：不能寫 *p=LedPin

  Serial.begin(115200);
  Serial.println("Led Control");

  // =========================
  // 初始化 D2~D9
  // =========================
  for (int i = 0; i < 8; i++) {

    pinMode(*(p + i), OUTPUT);

    // 修正：初始化為 LOW
    digitalWrite(*(p + i), LOW);
  }

  delay(300);
}

void loop() {

  // // =========================
  // // 全部 LED 亮
  // // =========================
  // for (int i = 0; i < 8; i++) {

  //   digitalWrite(*(p + i), HIGH);
  // }

  // delay(1000);

  // // =========================
  // // 全部 LED 滅
  // // =========================
  // for (int i = 0; i < 8; i++) {

  //   digitalWrite(*(p + i), LOW);
  // }

  for (int j = 0; j < 3; j++) {
    led_onOff();
  }

  for (int j = 0; j < 3; j++) {
    led_oddEven();
  }
  delay(1000);
}

void led_onOff() {}

void led_oddEven() {

  for (int i = 0; i < 8; i++) {
    if (i % 2 == 0) {
      digitalWrite(*(p + i), HIGH);
    }
  }
  delay(1000);
  for (int i = 0; i < 8; i++) {
    if (i % 2 == 0) {
      digitalWrite(*(p + i), LOW);
    }
  }
  delay(1000);
  for (int i = 0; i < 8; i++) {
    if (i % 2 != 0) {
      digitalWrite(*(p + i), HIGH);
    }
  }
  delay(1000);
  for (int i = 0; i < 8; i++) {
    if (i % 2 != 0) {
      digitalWrite(*(p + i), LOW);
    }
  }
  delay(1000);
}
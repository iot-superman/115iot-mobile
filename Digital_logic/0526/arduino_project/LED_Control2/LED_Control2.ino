// =====================================
// RGB LED 腳位
// D2 = R   
// D3 = G   
// D4 = B  
// =====================================
int LedRGBPin[] = {10, 11, 12};

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

  Serial.println("RGB Binary Counter");

  // =====================================
  // 初始化 RGB
  // =====================================
  for (int i = 0; i < 3; i++) {

    pinMode(*(prtLEDPin + i), OUTPUT);

    digitalWrite(*(prtLEDPin + i), LOW);
  }
}

void loop() {

  // =====================================
  // Binary Counter
  // 000 ~ 111
  // =====================================
  for (int value = 0; value < 8; value++) {

    // =========================
    // bit0 -> R (D2)
    // =========================
    if (value & 0b001) {

      digitalWrite(*(prtLEDPin + 0), HIGH);

    } else {

      digitalWrite(*(prtLEDPin + 0), LOW);
    }

    // =========================
    // bit1 -> G (D3)
    // =========================
    if (value & 0b010) {

      digitalWrite(*(prtLEDPin + 1), HIGH);

    } else {

      digitalWrite(*(prtLEDPin + 1), LOW);
    }

    // =========================
    // bit2 -> B (D4)
    // =========================
    if (value & 0b100) {

      digitalWrite(*(prtLEDPin + 2), HIGH);

    } else {

      digitalWrite(*(prtLEDPin + 2), LOW);
    }

    // =====================================
    // Serial 顯示 Binary
    // =====================================
    Serial.print("Binary = ");

    Serial.print((value & 0b100) >> 2); // B
    Serial.print(" ");

    Serial.print((value & 0b010) >> 1); // G
    Serial.print(" ");

    Serial.println(value & 0b001);      // R

    delay(1000);
  }
}

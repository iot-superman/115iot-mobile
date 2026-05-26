// =====================================
// RGB LED 腳位
// D10 = R
// D11 = G
// D12 = B
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
  // 初始化腳位
  // =====================================
  for (int i = 0; i < 3; i++) {

    pinMode(*(prtLEDPin + i), OUTPUT);

    digitalWrite(*(prtLEDPin + i), LOW);
  }

  // =====================================
  // 開機測試
  // =====================================
  allON();

  delay(1000);

  allOFF();

  delay(1000);
}

// =====================================
// 全亮
// =====================================
void allON() {

  digitalWrite(*(prtLEDPin + 0), HIGH);

  digitalWrite(*(prtLEDPin + 1), HIGH);

  digitalWrite(*(prtLEDPin + 2), HIGH);
}

// =====================================
// 全滅
// =====================================
void allOFF() {

  digitalWrite(*(prtLEDPin + 0), LOW);

  digitalWrite(*(prtLEDPin + 1), LOW);

  digitalWrite(*(prtLEDPin + 2), LOW);
}

// =====================================
// RGB Binary Counter
// =====================================
void color_change() {

  byte bit_value;

  for (int i = 0; i < 8; i++) {

    // =====================================
    // bit0 -> R
    // =====================================
    bit_value = i & 0x1;

    digitalWrite(10, bit_value);

    // =====================================
    // bit1 -> G
    // =====================================
    bit_value = (i >> 1) & 0x1;

    digitalWrite(11, bit_value);

    // =====================================
    // bit2 -> B
    // =====================================
    bit_value = (i >> 2) & 0x1;

    digitalWrite(12, bit_value);

    // =====================================
    // Serial 顯示
    // =====================================
    Serial.print("Binary = ");

    Serial.print((i >> 2) & 0x1);

    Serial.print(" ");

    Serial.print((i >> 1) & 0x1);

    Serial.print(" ");

    Serial.println(i & 0x1);

    delay(1000);
  }
}

void loop() {

  color_change();
}

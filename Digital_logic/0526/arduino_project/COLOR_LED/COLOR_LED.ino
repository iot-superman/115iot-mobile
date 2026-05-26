//https://chatgpt.com/s/m_6a15308fbe248191a12b3f4823e0c9ee

// =====================================
// RGB LED 腳位
// D10 = R
// D11 = G
// D12 = B
// =====================================

void setup() {

  // =====================================
  // 設定 RGB LED 腳位為輸出
  // =====================================
  pinMode(10, OUTPUT);

  pinMode(11, OUTPUT);

  pinMode(12, OUTPUT);

  // =====================================
  // Serial 初始化
  // =====================================
  Serial.begin(115200);

  Serial.println("RGB Binary Counter");

  // =====================================
  // 全亮測試
  // =====================================
  digitalWrite(10, HIGH);

  digitalWrite(11, HIGH);

  digitalWrite(12, HIGH);

  delay(1000);

  // =====================================
  // 全滅測試
  // =====================================
  digitalWrite(10, LOW);

  digitalWrite(11, LOW);

  digitalWrite(12, LOW);

  delay(1000);
}

void loop() {

  color_change();
}

// =====================================
// RGB Binary Counter
// =====================================
void color_change() {

  byte bit_value;

  // =====================================
  // 0 ~ 7
  // =====================================
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
    // Serial 顯示 Binary
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

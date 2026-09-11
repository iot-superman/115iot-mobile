// ========================================
// LED 腳位設定
// ========================================
#define LED1 2
#define LED2 14
#define LED3 12
#define LED4 13
#define LED5 15


void setup() {
  // put your setup code here, to run once:

  Serial.begin(115200);


  // ========================================
  // LED1
  // ========================================
  pinMode(LED1, OUTPUT);
  digitalWrite(LED1, LOW);


  // ========================================
  // LED2
  // ========================================
  pinMode(LED2, OUTPUT);
  digitalWrite(LED2, HIGH);


  // ========================================
  // LED3
  // ========================================
  pinMode(LED3, OUTPUT);
  digitalWrite(LED3, HIGH);


  // ========================================
  // LED4
  // ========================================
  pinMode(LED4, OUTPUT);
  digitalWrite(LED4, HIGH);


  // ========================================
  // LED5
  // ========================================
  pinMode(LED5, OUTPUT);
  digitalWrite(LED5, HIGH);


  // ========================================
  // 等待 2 秒
  // ========================================
  delay(2000);


  // ========================================
  // LED 狀態切換
  // ========================================
  digitalWrite(LED1, HIGH);
  digitalWrite(LED2, LOW);
  digitalWrite(LED3, LOW);
  digitalWrite(LED4, LOW);
  digitalWrite(LED5, LOW);
}


void loop() {
  // put your main code here, to run repeatedly:

}

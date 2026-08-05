#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <DHT.h>

// ==================================================
// 使用者設定
// ==================================================

// Wi-Fi 名稱。
const char* WIFI_SSID = "thmrb311";

// Wi-Fi 密碼。
const char* WIFI_PASSWORD = "thmrbthmrb";

// MQTT Broker 的 IP 位址。
// 請確認 ESP8266 與 Broker 位於相同網路。
const char* MQTT_SERVER = "192.168.63.57";

// MQTT Broker 預設連接埠。
const uint16_t MQTT_PORT = 1883;

// 【新增】MQTT Broker 登入帳號。
const char* MQTT_USERNAME = "cubie";

// 【新增】MQTT Broker 登入密碼。
const char* MQTT_PASSWORD = "1234";

// DHT22 資料發布主題。
const char* MQTT_TOPIC = "dht22/311";

// ==================================================
// DHT22 與 LED 設定
// ==================================================

// NodeMCU／Wemos D1 mini 的 D2 對應 ESP8266 GPIO4。
//
// 這裡直接使用 GPIO4，不使用 D2，避免選錯開發板時出現：
// 'D2' was not declared in this scope
#define DHT_PIN 4

// 指定感測器型號為 DHT22。
#define DHT_TYPE DHT22

// 使用 ESP8266 開發板上的內建 LED。
#define LED_PIN LED_BUILTIN

// DHT22 建議至少間隔約 2 秒再讀取一次。
const unsigned long PUBLISH_INTERVAL_MS = 2000;

// 每次成功發布 MQTT 後，內建 LED 亮 1 秒。
const unsigned long LED_ON_TIME_MS = 1000;

// ==================================================
// 建立物件
// ==================================================

// 建立 DHT22 感測器物件。
DHT dht(DHT_PIN, DHT_TYPE);

// 建立 Wi-Fi 用戶端。
WiFiClient wifiClient;

// 建立 MQTT 用戶端，底層使用前面的 Wi-Fi 用戶端。
PubSubClient mqttClient(wifiClient);

// ==================================================
// 執行狀態變數
// ==================================================

// 記錄上一次發布 MQTT 的時間。
unsigned long lastPublishTime = 0;

// 記錄 LED 開始亮起的時間。
unsigned long ledTurnedOnTime = 0;

// 記錄 LED 是否正在亮燈。
bool ledIsOn = false;

/**
 * 連線到 Wi-Fi。
 */
void connectWiFi() {
  Serial.print("Connecting to Wi-Fi: ");
  Serial.println(WIFI_SSID);

  // 將 ESP8266 設定為無線基地台用戶端模式。
  WiFi.mode(WIFI_STA);

  // 開始連線到指定的 Wi-Fi。
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  // 等待 Wi-Fi 連線完成。
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println();
  Serial.println("Wi-Fi connected");

  // 顯示 ESP8266 從路由器取得的 IP 位址。
  Serial.print("ESP8266 IP: ");
  Serial.println(WiFi.localIP());
}

/**
 * 檢查 Wi-Fi，如果斷線就重新連線。
 */
void ensureWiFiConnected() {
  if (WiFi.status() == WL_CONNECTED) {
    return;
  }

  Serial.println("Wi-Fi disconnected");
  connectWiFi();
}

/**
 * 連線到 MQTT Broker。
 */
void connectMQTT() {
  // MQTT 尚未連線時持續嘗試重新連線。
  while (!mqttClient.connected()) {
    Serial.print("Connecting to MQTT...");

    // 使用 ESP8266 晶片 ID 產生不重複的 MQTT Client ID。
    // 避免不同 ESP8266 使用相同 Client ID 而互相斷線。
    String clientId = "ESP8266-DHT22-";
    clientId += String(ESP.getChipId(), HEX);

    Serial.print(" Client ID: ");
    Serial.print(clientId);
    Serial.print("...");

    // 【修改】使用 MQTT 帳號及密碼登入 Broker。
    bool connectionSucceeded = mqttClient.connect(
      clientId.c_str(),
      MQTT_USERNAME,
      MQTT_PASSWORD
    );

    if (connectionSucceeded) {
      Serial.println("connected");

      // 顯示成功登入使用的帳號，基於安全考量不顯示密碼。
      Serial.print("MQTT username: ");
      Serial.println(MQTT_USERNAME);
    } else {
      Serial.print("failed, MQTT state = ");
      Serial.println(mqttClient.state());

      // 顯示常見 MQTT 錯誤原因。
      switch (mqttClient.state()) {
        case -4:
          Serial.println("Error: MQTT connection timeout");
          break;

        case -3:
          Serial.println("Error: MQTT connection lost");
          break;

        case -2:
          Serial.println("Error: MQTT network connection failed");
          break;

        case -1:
          Serial.println("Error: MQTT client disconnected");
          break;

        case 1:
          Serial.println("Error: Unsupported MQTT protocol version");
          break;

        case 2:
          Serial.println("Error: MQTT Client ID rejected");
          break;

        case 3:
          Serial.println("Error: MQTT Broker unavailable");
          break;

        case 4:
          Serial.println("Error: MQTT username or password format is invalid");
          break;

        case 5:
          Serial.println("Error: MQTT account or password is incorrect / unauthorized");
          break;

        default:
          Serial.println("Error: Unknown MQTT connection error");
          break;
      }

      // 連線失敗，等待 2 秒後重新嘗試。
      delay(2000);

      // 如果等待期間 Wi-Fi 斷線，先重新連線 Wi-Fi。
      ensureWiFiConnected();
    }
  }
}

/**
 * 開啟 ESP8266 內建 LED。
 */
void startLedBlink() {
  // 多數 ESP8266 開發板的內建 LED 是 Active LOW：
  // LOW 表示亮燈，HIGH 表示關燈。
  digitalWrite(LED_PIN, LOW);

  ledIsOn = true;
  ledTurnedOnTime = millis();
}

/**
 * 檢查 LED 是否已經亮滿指定時間。
 */
void updateLed() {
  // 使用 millis() 進行非阻塞計時。
  // 不使用 delay(1000)，避免 MQTT 網路處理被暫停。
  if (
    ledIsOn &&
    millis() - ledTurnedOnTime >= LED_ON_TIME_MS
  ) {
    // HIGH 關閉 ESP8266 內建 LED。
    digitalWrite(LED_PIN, HIGH);

    ledIsOn = false;
  }
}

/**
 * 讀取 DHT22 並將溫度與濕度發布至 MQTT。
 */
void readAndPublishDHT22() {
  // 讀取相對濕度，單位為百分比。
  float humidity = dht.readHumidity();

  // 讀取攝氏溫度。
  float temperature = dht.readTemperature();

  // 如果讀取失敗，DHT 函式會傳回 NaN。
  if (isnan(humidity) || isnan(temperature)) {
    Serial.println("DHT22 read failed");
    return;
  }

  // 建立 JSON 格式的 MQTT 訊息。
  //
  // 發布內容範例：
  // {"temperature":25.60,"humidity":65.20}
  char payload[100];

  // 將溫度與濕度寫入 payload 字元陣列。
  snprintf(
    payload,
    sizeof(payload),
    "{\"temperature\":%.2f,\"humidity\":%.2f}",
    temperature,
    humidity
  );

  // 在序列埠監控視窗顯示感測資料。
  Serial.print("Temperature: ");
  Serial.print(temperature, 2);
  Serial.print(" °C, Humidity: ");
  Serial.print(humidity, 2);
  Serial.println(" %");

  Serial.print("MQTT topic: ");
  Serial.println(MQTT_TOPIC);

  Serial.print("MQTT payload: ");
  Serial.println(payload);

  // 將 JSON 資料發布到指定 MQTT Topic。
  //
  // 第三個參數 true 代表使用 retained message：
  // 新訂閱的用戶端可以立刻收到最近一次的資料。
  bool publishSucceeded = mqttClient.publish(
    MQTT_TOPIC,
    payload,
    true
  );

  if (publishSucceeded) {
    Serial.println("MQTT publish succeeded");

    // 發布成功後讓內建 LED 亮 1 秒。
    startLedBlink();
  } else {
    Serial.println("MQTT publish failed");
  }

  Serial.println("----------------------------------------");
}

/**
 * ESP8266 開機時只執行一次。
 */
void setup() {
  // 啟動序列埠，方便查看 Wi-Fi、MQTT 與 DHT22 狀態。
  Serial.begin(115200);

  // 稍微等待序列埠初始化。
  delay(100);

  Serial.println();
  Serial.println("ESP8266 DHT22 MQTT starting...");

  // 設定內建 LED 為輸出模式。
  pinMode(LED_PIN, OUTPUT);

  // ESP8266 內建 LED通常為 Active LOW，
  // 因此開機時輸出 HIGH，先將 LED 關閉。
  digitalWrite(LED_PIN, HIGH);

  // 啟動 DHT22 感測器。
  dht.begin();

  // 連線到 Wi-Fi。
  connectWiFi();

  // 設定 MQTT Broker 的 IP 位址與連接埠。
  mqttClient.setServer(MQTT_SERVER, MQTT_PORT);

  // 連線到 MQTT Broker。
  connectMQTT();

  // 讓第一次進入 loop() 時可以立即讀取並發布資料。
  lastPublishTime = millis() - PUBLISH_INTERVAL_MS;
}

/**
 * ESP8266 開機後持續重複執行。
 */
void loop() {
  // 確認 Wi-Fi 是否仍然保持連線。
  ensureWiFiConnected();

  // MQTT 斷線時重新連線。
  if (!mqttClient.connected()) {
    connectMQTT();
  }

  // 維持 MQTT 連線並處理網路封包。
  mqttClient.loop();

  // 更新 LED 狀態。
  updateLed();

  // 取得目前開機後經過的毫秒數。
  unsigned long currentTime = millis();

  // 每隔 2 秒讀取一次 DHT22 並發布 MQTT。
  if (
    currentTime - lastPublishTime >= PUBLISH_INTERVAL_MS
  ) {
    lastPublishTime = currentTime;

    readAndPublishDHT22();
  }

  // 短暫讓出 CPU，維持 ESP8266 Wi-Fi 系統正常運作。
  yield();
}

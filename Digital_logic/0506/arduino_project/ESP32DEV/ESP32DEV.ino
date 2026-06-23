/*
Board               ESP32S3 Dev Module
Upload Speed        115200
USB Mode            Hardware CDC and JTAG
USB CDC On Boot     Disabled
Upload Mode         UART0 / Hardware CDC
CPU Frequency       240MHz
Flash Mode          QIO 80MHz
Flash Size          16MB
Partition Scheme    16M Flash (2MB APP/12.5MB FATFS)
PSRAM               Disabled

*/
// ESP32-S3 UNO + 內建 RGB LED(GPIO48)
// MQTT RGB 控制
//
// 支援格式：
// #FF0000
// FF0000
// 255,0,0

#include <WiFi.h>
#include <PubSubClient.h>
#include <Adafruit_NeoPixel.h>

//==================================================
// WiFi 設定
//==================================================

const char* WIFI_SSID = "iPhone12m";
const char* WIFI_PASSWORD = "12345678";

//==================================================
// MQTT 設定
//==================================================

const char* MQTT_SERVER = "broker.emqx.io";
const int MQTT_PORT = 1883;

const char* MQTT_TOPIC = "esp32s3/rgb";
const char* MQTT_CLIENT_ID = "esp32s3_uno_rgb_001";

//==================================================
// RGB LED 設定
//==================================================

#define RGB_PIN 48
#define NUMPIXELS 1

Adafruit_NeoPixel pixels(
  NUMPIXELS,
  RGB_PIN,
  NEO_GRB + NEO_KHZ800
);

WiFiClient espClient;
PubSubClient mqttClient(espClient);

//==================================================
// 設定 RGB 顏色
//==================================================

void setRGBColor(int r, int g, int b)
{
  r = constrain(r, 0, 255);
  g = constrain(g, 0, 255);
  b = constrain(b, 0, 255);

  pixels.setPixelColor(
    0,
    pixels.Color(r, g, b)
  );

  pixels.show();

  Serial.print("RGB = ");
  Serial.print(r);
  Serial.print(",");
  Serial.print(g);
  Serial.print(",");
  Serial.println(b);
}

//==================================================
// HEX 色碼解析
// 支援：
// #FF0000
// FF0000
//==================================================

bool parseHexColor(
  String colorCode,
  int &r,
  int &g,
  int &b
)
{
  colorCode.trim();

  // 移除 #
  if (colorCode.startsWith("#"))
  {
    colorCode.remove(0, 1);
  }

  // 必須為 6 碼
  if (colorCode.length() != 6)
  {
    return false;
  }

  // 驗證 HEX 字元
  for (int i = 0; i < 6; i++)
  {
    if (!isxdigit(colorCode.charAt(i)))
    {
      return false;
    }
  }

  // 網頁標準格式：
  // RRGGBB

  String rText = colorCode.substring(0, 2);
  String gText = colorCode.substring(2, 4);
  String bText = colorCode.substring(4, 6);

  r = strtol(
        rText.c_str(),
        NULL,
        16
      );

  g = strtol(
        gText.c_str(),
        NULL,
        16
      );

  b = strtol(
        bText.c_str(),
        NULL,
        16
      );

  return true;
}

//==================================================
// RGB文字解析
// 支援：
// 255,0,0
//==================================================

bool parseRgbText(
  String rgbText,
  int &r,
  int &g,
  int &b
)
{
  rgbText.trim();

  int comma1 = rgbText.indexOf(',');
  int comma2 = rgbText.indexOf(',', comma1 + 1);

  if (comma1 == -1 || comma2 == -1)
  {
    return false;
  }

  String rText =
    rgbText.substring(
      0,
      comma1
    );

  String gText =
    rgbText.substring(
      comma1 + 1,
      comma2
    );

  String bText =
    rgbText.substring(
      comma2 + 1
    );

  rText.trim();
  gText.trim();
  bText.trim();

  r = rText.toInt();
  g = gText.toInt();
  b = bText.toInt();

  if (r < 0 || r > 255) return false;
  if (g < 0 || g > 255) return false;
  if (b < 0 || b > 255) return false;

  return true;
}

//==================================================
// MQTT Callback
//==================================================

void mqttCallback(
  char* topic,
  byte* payload,
  unsigned int length
)
{
  String message = "";

  for (unsigned int i = 0; i < length; i++)
  {
    message += (char)payload[i];
  }

  message.trim();

  Serial.println();
  Serial.print("Topic: ");
  Serial.println(topic);

  Serial.print("Payload: ");
  Serial.println(message);

  int r = 0;
  int g = 0;
  int b = 0;

  // 先判斷 HEX

  if (parseHexColor(
        message,
        r,
        g,
        b
      ))
  {
    setRGBColor(
      r,
      g,
      b
    );

    return;
  }

  // 再判斷 RGB

  if (parseRgbText(
        message,
        r,
        g,
        b
      ))
  {
    setRGBColor(
      r,
      g,
      b
    );

    return;
  }

  Serial.println(
    "格式錯誤"
  );

  Serial.println(
    "請使用："
  );

  Serial.println(
    "#FF0000"
  );

  Serial.println(
    "FF0000"
  );

  Serial.println(
    "255,0,0"
  );
}

//==================================================
// WiFi 連線
//==================================================

void connectWiFi()
{
  Serial.print("連線 WiFi：");
  Serial.println(WIFI_SSID);

  WiFi.begin(
    WIFI_SSID,
    WIFI_PASSWORD
  );

  while (
    WiFi.status()
    != WL_CONNECTED
  )
  {
    delay(500);
    Serial.print(".");
  }

  Serial.println();
  Serial.println("WiFi 已連線");

  Serial.print("IP：");
  Serial.println(
    WiFi.localIP()
  );
}

//==================================================
// MQTT 連線
//==================================================

void connectMQTT()
{
  while (!mqttClient.connected())
  {
    Serial.print(
      "連線 MQTT..."
    );

    if (
      mqttClient.connect(
        MQTT_CLIENT_ID
      )
    )
    {
      Serial.println("成功");

      mqttClient.subscribe(
        MQTT_TOPIC
      );

      Serial.print(
        "已訂閱："
      );

      Serial.println(
        MQTT_TOPIC
      );
    }
    else
    {
      Serial.print(
        "失敗 code="
      );

      Serial.println(
        mqttClient.state()
      );

      delay(3000);
    }
  }
}

//==================================================
// Setup
//==================================================

void setup()
{
  Serial.begin(115200);

  delay(5000);

  Serial.println();
  Serial.println(
    "ESP32-S3 RGB MQTT 啟動"
  );

  pixels.begin();

  pixels.clear();

  pixels.show();

  connectWiFi();

  mqttClient.setServer(
    MQTT_SERVER,
    MQTT_PORT
  );

  mqttClient.setCallback(
    mqttCallback
  );

  connectMQTT();

  // 啟動顯示白燈
  setRGBColor(
    50,
    50,
    50
  );
}

//==================================================
// Loop
//==================================================

void loop()
{
  if (!mqttClient.connected())
  {
    connectMQTT();
  }

  mqttClient.loop();
}

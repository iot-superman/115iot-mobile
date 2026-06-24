#include <HX711.h>

#define HX711_DT   6
#define HX711_SCK  5

HX711 scale;

//====================================================
// 50g校正值
//====================================================

float calibration_factor = 387.2;

//====================================================
// 移動平均
//====================================================

const int AVG_SIZE = 8;

float avgBuffer[AVG_SIZE];

int avgIndex = 0;

bool avgReady = false;

//====================================================
// Offset
//====================================================

long zeroOffset = 0;

//====================================================
// 移動平均濾波
//====================================================

float movingAverage(float value)
{
    avgBuffer[avgIndex] = value;

    avgIndex++;

    if(avgIndex >= AVG_SIZE)
    {
        avgIndex = 0;
        avgReady = true;
    }

    int count =
        avgReady
        ? AVG_SIZE
        : avgIndex;

    float sum = 0;

    for(int i = 0; i < count; i++)
    {
        sum += avgBuffer[i];
    }

    return sum / count;
}

//====================================================
// 自動零點追蹤
//====================================================

void autoZero(float weight)
{
    if(abs(weight) < 0.5)
    {
        long raw =
            scale.read_average(5);

        zeroOffset =
            (zeroOffset * 9999 + raw)
            / 10000;
    }
}

//====================================================
// Setup
//====================================================

void setup()
{
    Serial.begin(115200);

    delay(1000);

    Serial.println();
    Serial.println("HX711 Stable Mode");

    scale.begin(
        HX711_DT,
        HX711_SCK
    );

    delay(1000);

    scale.tare();

    zeroOffset =
        scale.read_average(30);

    Serial.print("Zero Offset = ");
    Serial.println(zeroOffset);

    Serial.println("Ready");
}

//====================================================
// Loop
//====================================================

void loop()
{
    //------------------------------------------------
    // HX711尚未有新資料
    //------------------------------------------------

    if(!scale.is_ready())
    {
        delay(5);
        return;
    }

    //------------------------------------------------
    // RAW平均10次
    //------------------------------------------------

    long raw =
        scale.read_average(10);

    //------------------------------------------------
    // 重量
    //------------------------------------------------

    float weight =
        (raw - zeroOffset)
        / calibration_factor;

    //------------------------------------------------
    // 移動平均
    //------------------------------------------------

    weight =
        movingAverage(weight);

    //------------------------------------------------
    // 自動歸零
    //------------------------------------------------

    autoZero(weight);

    //------------------------------------------------
    // 小重量歸零
    //------------------------------------------------

    if(abs(weight) < 0.3)
    {
        weight = 0;
    }

    //------------------------------------------------
    // 顯示
    //------------------------------------------------

    Serial.print("RAW = ");
    Serial.print(raw);

    Serial.print("    Weight = ");
    Serial.print(weight, 2);

    Serial.println(" g");

    delay(200);
}

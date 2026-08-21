import RPi.GPIO as GPIO
import time

ledsPin = 5
ledPin = 10
lightPin = ledsPin

GPIO.setmode(GPIO.BCM)

# GPIO5 ~ GPIO10 全部設定成輸出
for i in range(5, 11):
    GPIO.setup(i, GPIO.OUT)
    GPIO.output(i, False)   # 一開始全部關閉

try:
    while True:

        # 先將全部 LED 關閉
        for i in range(5, 11):
            GPIO.output(i, True)

        # 只點亮目前這一顆
        GPIO.output(lightPin, False)

        # 等待 1 秒
        time.sleep(1)

        # 移動到下一顆
        if lightPin < ledPin:
            lightPin = lightPin + 1
        else:
            # GPIO10 完成後回 GPIO5
            lightPin = ledsPin

except KeyboardInterrupt:

    # Ctrl + C 時清除 GPIO
    GPIO.cleanup()
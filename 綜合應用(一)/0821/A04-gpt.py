import RPi.GPIO as GPIO
import time

# GPIO5 ~ GPIO10，共 6 顆 LED
ledsPin = 5
ledPin = 10

# 第一顆開始的位置
lightPin = ledsPin

GPIO.setmode(GPIO.BCM)

# ============================================================
# GPIO5 ~ GPIO10 全部設定成輸出
# 你的接法：
# True  = LED 關閉
# False = LED 點亮
# ============================================================
for i in range(5, 11):
    GPIO.setup(i, GPIO.OUT)
    GPIO.output(i, True)     # 一開始全部關閉

try:
    while True:

        # ====================================================
        # 1. 先將全部 LED 關閉
        # ====================================================
        for i in range(5, 11):
            GPIO.output(i, True)

        # ====================================================
        # 2. 一次點亮連續三顆 LED
        #
        # % 6 是為了讓 GPIO10 後面可以循環回 GPIO5
        #
        # 例如：
        # lightPin = 5 → GPIO5、6、7
        # lightPin = 6 → GPIO6、7、8
        # lightPin = 7 → GPIO7、8、9
        # lightPin = 8 → GPIO8、9、10
        # lightPin = 9 → GPIO9、10、5
        # lightPin =10 → GPIO10、5、6
        # ====================================================

        # 第一顆
        led1 = lightPin

        # 第二顆，超過 GPIO10 就循環回 GPIO5
        led2 = ((lightPin - 5 + 1) % 6) + 5

        # 第三顆，超過 GPIO10就循環回 GPIO5
        led3 = ((lightPin - 5 + 2) % 6) + 5

        # ====================================================
        # 3. 三顆同時點亮
        # False = LED 亮
        # ====================================================
        GPIO.output(led1, False)
        GPIO.output(led2, False)
        GPIO.output(led3, False)

        # 停留 1 秒
        time.sleep(1)

        # ====================================================
        # 4. 起始位置往下一顆移動
        # ====================================================
        if lightPin < ledPin:
            lightPin = lightPin + 1
        else:
            # GPIO10 後重新回 GPIO5
            lightPin = ledsPin

except KeyboardInterrupt:

    # ========================================================
    # Ctrl + C 時，先將全部 LED 關閉
    # ========================================================
    for i in range(5, 11):
        GPIO.output(i, True)

    # 清除 GPIO
    GPIO.cleanup()
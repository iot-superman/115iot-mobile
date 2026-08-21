# A02.py
# Raspberry Pi 6 LED 正向 + 反向流水燈

import RPi.GPIO as GPIO
import time


# ============================================================
# LED 腳位設定
# 使用 GPIO.BOARD，因此這些數字是 Raspberry Pi「實體 Pin 編號」
# ============================================================

ledPin1 = 29     # Pin 29 = GPIO5
ledPin2 = 31     # Pin 31 = GPIO6
ledPin3 = 26     # Pin 26 = GPIO7
ledPin4 = 24     # Pin 24 = GPIO8
ledPin5 = 21     # Pin 21 = GPIO9
ledPin6 = 19     # Pin 19 = GPIO10


# ============================================================
# GPIO 初始化
# ============================================================

# 使用實體 Pin 編號
GPIO.setmode(GPIO.BOARD)

# 將 6 個 LED 腳位設定為輸出
GPIO.setup(ledPin1, GPIO.OUT)
GPIO.setup(ledPin2, GPIO.OUT)
GPIO.setup(ledPin3, GPIO.OUT)
GPIO.setup(ledPin4, GPIO.OUT)
GPIO.setup(ledPin5, GPIO.OUT)
GPIO.setup(ledPin6, GPIO.OUT)


try:

    # ========================================================
    # 無限循環
    # ========================================================
    while True:

        # ====================================================
        # 第一層：正向循環
        # LED1 → LED2 → LED3 → LED4 → LED5 → LED6
        #
        # 依照課堂接法：
        # False (LOW) = LED 亮
        # True  (HIGH) = LED 滅
        # ====================================================


        # ----------------------------------------------------
        # LED1 亮
        # ----------------------------------------------------
        GPIO.output(ledPin1, False)
        GPIO.output(ledPin2, True)
        GPIO.output(ledPin3, True)
        GPIO.output(ledPin4, True)
        GPIO.output(ledPin5, True)
        GPIO.output(ledPin6, True)

        time.sleep(1)


        # ----------------------------------------------------
        # LED2 亮
        # ----------------------------------------------------
        GPIO.output(ledPin1, True)
        GPIO.output(ledPin2, False)
        GPIO.output(ledPin3, True)
        GPIO.output(ledPin4, True)
        GPIO.output(ledPin5, True)
        GPIO.output(ledPin6, True)

        time.sleep(1)


        # ----------------------------------------------------
        # LED3 亮
        # ----------------------------------------------------
        GPIO.output(ledPin1, True)
        GPIO.output(ledPin2, True)
        GPIO.output(ledPin3, False)
        GPIO.output(ledPin4, True)
        GPIO.output(ledPin5, True)
        GPIO.output(ledPin6, True)

        time.sleep(1)


        # ----------------------------------------------------
        # LED4 亮
        # ----------------------------------------------------
        GPIO.output(ledPin1, True)
        GPIO.output(ledPin2, True)
        GPIO.output(ledPin3, True)
        GPIO.output(ledPin4, False)
        GPIO.output(ledPin5, True)
        GPIO.output(ledPin6, True)

        time.sleep(1)


        # ----------------------------------------------------
        # LED5 亮
        # ----------------------------------------------------
        GPIO.output(ledPin1, True)
        GPIO.output(ledPin2, True)
        GPIO.output(ledPin3, True)
        GPIO.output(ledPin4, True)
        GPIO.output(ledPin5, False)
        GPIO.output(ledPin6, True)

        time.sleep(1)


        # ----------------------------------------------------
        # LED6 亮
        # ----------------------------------------------------
        GPIO.output(ledPin1, True)
        GPIO.output(ledPin2, True)
        GPIO.output(ledPin3, True)
        GPIO.output(ledPin4, True)
        GPIO.output(ledPin5, True)
        GPIO.output(ledPin6, False)

        time.sleep(1)


        # ====================================================
        # 第二層：反向循環
        # LED5 → LED4 → LED3 → LED2
        # ====================================================


        # ----------------------------------------------------
        # LED5 亮
        # ----------------------------------------------------
        GPIO.output(ledPin1, True)
        GPIO.output(ledPin2, True)
        GPIO.output(ledPin3, True)
        GPIO.output(ledPin4, True)
        GPIO.output(ledPin5, False)
        GPIO.output(ledPin6, True)

        time.sleep(1)


        # ----------------------------------------------------
        # LED4 亮
        # ----------------------------------------------------
        GPIO.output(ledPin1, True)
        GPIO.output(ledPin2, True)
        GPIO.output(ledPin3, True)
        GPIO.output(ledPin4, False)
        GPIO.output(ledPin5, True)
        GPIO.output(ledPin6, True)

        time.sleep(1)


        # ----------------------------------------------------
        # LED3 亮
        # ----------------------------------------------------
        GPIO.output(ledPin1, True)
        GPIO.output(ledPin2, True)
        GPIO.output(ledPin3, False)
        GPIO.output(ledPin4, True)
        GPIO.output(ledPin5, True)
        GPIO.output(ledPin6, True)

        time.sleep(1)


        # ----------------------------------------------------
        # LED2 亮
        # ----------------------------------------------------
        GPIO.output(ledPin1, True)
        GPIO.output(ledPin2, False)
        GPIO.output(ledPin3, True)
        GPIO.output(ledPin4, True)
        GPIO.output(ledPin5, True)
        GPIO.output(ledPin6, True)

        time.sleep(1)


# ============================================================
# 按 Ctrl + C 停止程式
# ============================================================

except KeyboardInterrupt:
    print("\nA02.py 已停止")


# ============================================================
# 程式結束時清除 GPIO
# ============================================================

finally:
    GPIO.cleanup()
    print("GPIO cleanup 完成")
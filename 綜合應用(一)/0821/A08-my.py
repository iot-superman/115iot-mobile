import RPi.GPIO as GPIO
import time

# ============================================================
# HC-SR04 超音波測距
#
# BOARD 實體腳位：
#
# VCC  → Pin 2  (5V)
# GND  → Pin 6  (GND)
# TRIG → Pin 13 (GPIO27)
# ECHO → 510Ω / 1KΩ 分壓 → Pin 15 (GPIO22)
# ============================================================

TRIG = 13
ECHO = 15

# 使用實體 Pin 編號
GPIO.setmode(GPIO.BOARD)

# TRIG 輸出
GPIO.setup(TRIG, GPIO.OUT)

# ECHO 輸入
GPIO.setup(ECHO, GPIO.IN)

# TRIG 初始 LOW
GPIO.output(TRIG, GPIO.LOW)

print("HC-SR04 初始化中...")
time.sleep(2)

print("開始測量距離")

try:
    while True:

        # ====================================================
        # 1. 發送 10us Trigger
        # ====================================================

        GPIO.output(TRIG, GPIO.LOW)
        time.sleep(0.000002)

        GPIO.output(TRIG, GPIO.HIGH)
        time.sleep(0.00001)

        GPIO.output(TRIG, GPIO.LOW)


        # ====================================================
        # 2. 等待 ECHO 變 HIGH
        #
        # 最多等待 0.05 秒
        # 避免 ECHO 沒訊號時程式卡死
        # ====================================================

        timeout = time.time() + 0.05

        while GPIO.input(ECHO) == GPIO.LOW:

            if time.time() > timeout:
                print("Timeout：ECHO 沒有變 HIGH")
                break

        else:

            # ECHO 開始 HIGH
            pulse_start = time.time()


            # =================================================
            # 3. 等待 ECHO 由 HIGH 變 LOW
            # =================================================

            timeout = time.time() + 0.05

            while GPIO.input(ECHO) == GPIO.HIGH:

                if time.time() > timeout:
                    print("Timeout：ECHO 一直維持 HIGH")
                    break

            else:

                # ECHO 結束
                pulse_end = time.time()


                # =============================================
                # 4. 計算時間
                # =============================================

                pulse_duration = pulse_end - pulse_start


                # =============================================
                # 5. 計算距離
                #
                # 聲速 = 34300 cm/s
                #
                # ÷ 2：
                # 超音波走出去再反射回來
                # =============================================

                distance = pulse_duration * 34300 / 2


                # =============================================
                # 6. 顯示結果
                # =============================================

                print("距離：%.2f cm" % distance)


        # 每 0.5 秒測一次
        time.sleep(0.5)


except KeyboardInterrupt:

    print("\n停止 HC-SR04 測距")


finally:

    GPIO.cleanup()

    print("GPIO cleanup 完成")
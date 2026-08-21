import RPi.GPIO as GPIO
import time

# ============================================================
# 4 路繼電器 CH1 測試
#
# Raspberry Pi 實體 Pin 38 = BCM GPIO20
#
# 接線：
# Pin 2  (5V)     → Relay DC+
# Pin 6  (GND)    → Relay DC-
# Pin 38 (GPIO20) → Relay IN1
#
# Relay 跳線設定：LOW Trigger
# ============================================================

relayPin = 38

# 使用 Raspberry Pi「實體 Pin 編號」
GPIO.setmode(GPIO.BOARD)

# ============================================================
# LOW Trigger：
#
# HIGH = 繼電器 OFF
# LOW  = 繼電器 ON
#
# 一開始設定 HIGH，避免程式啟動就吸合
# ============================================================

GPIO.setup(
    relayPin,
    GPIO.OUT,
    initial=GPIO.HIGH
)

try:
    while True:

        # ====================================================
        # Pin 38 → LOW
        # CH1 繼電器吸合
        # ====================================================

        print("Pin 38 LOW → Relay CH1 ON")

        GPIO.output(relayPin, GPIO.LOW)

        # 維持 2 秒
        time.sleep(2)


        # ====================================================
        # Pin 38 → HIGH
        # CH1 繼電器釋放
        # ====================================================

        print("Pin 38 HIGH → Relay CH1 OFF")

        GPIO.output(relayPin, GPIO.HIGH)

        # 維持 2 秒
        time.sleep(2)


except KeyboardInterrupt:

    print("\n停止實驗")


finally:

    # ========================================================
    # 離開前先關閉 Relay
    # ========================================================

    GPIO.output(relayPin, GPIO.HIGH)

    # 清除 GPIO
    GPIO.cleanup()

    print("GPIO cleanup 完成")
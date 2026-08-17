import RPi.GPIO as GPIO
import time

# 使用 Raspberry Pi 實體腳位編號
GPIO.setmode(GPIO.BOARD)

# Raspberry Pi 實體 Pin 29 = BCM GPIO5
ledPin = 29

# 將 Pin 29 設定為輸出
GPIO.setup(ledPin, GPIO.OUT)

try:
    while True:
        # LED 亮
        GPIO.output(ledPin, True)
        time.sleep(1)

        # LED 滅
        GPIO.output(ledPin, False)
        time.sleep(1)

except KeyboardInterrupt:
    # Ctrl + C 結束程式時清除 GPIO
    GPIO.cleanup()
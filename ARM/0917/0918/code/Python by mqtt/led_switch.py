import RPi.GPIO as GPIO
import time

# 設置 GPIO 模式為 BCM
GPIO.setmode(GPIO.BCM)

# 設置 GPIO 引腳
LED_PIN = 17

# 設置 GPIO 引腳為輸出模式
GPIO.setup(LED_PIN, GPIO.OUT)

def led_on():
    GPIO.output(LED_PIN, GPIO.HIGH)
    print("LED is ON")

def led_off():
    GPIO.output(LED_PIN, GPIO.LOW)
    print("LED is OFF")

try:
    while True:
        command = input("Enter 'on' to turn LED on, 'off' to turn LED off, 'exit' to quit: ").strip().lower()
        if command == 'on':
            led_on()
        elif command == 'off':
            led_off()
        elif command == 'exit':
            break
        else:
            print("Invalid command")
finally:
    GPIO.cleanup()
    print("GPIO cleanup done")


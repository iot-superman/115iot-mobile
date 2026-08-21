#teacher version is better
import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)

for i in range(6):
    GPIO.setup(i+5, GPIO.OUT)
    GPIO.output(i+5, True)

try:
    while (True):
        for i in range(0, 6):
            pinA=((i+0)%6)+5
            pinB=((i+1)%6)+5
            pinC=((i+2)%6)+5

            GPIO.output(pinA, False)
            GPIO.output(pinB, False)
            GPIO.output(pinC, False)

            time.sleep(0.5)

            GPIO.output(pinA, True)
            GPIO.output(pinB, True)
            GPIO.output(pinC, True)

except KeyboardInterrupt:
    GPIO.cleanup()
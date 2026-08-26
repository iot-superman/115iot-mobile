import time
import pyfirmata


port="/dev/ttyUSB0"
board=pyfirmata.Arduino(port)
LedPin=board.get_pin('d:10:o')

print("Board is ready")

try:
    while (True):
        LedPin.write(0)
        print("LED is ON\n")
        time.sleep(1)
        LedPin.write(1)
        print("LED is OFF\n")
        time.sleep(2)
except KeyboardInterrupt:
    board.cleanup()


board.exit()

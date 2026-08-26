import time
import serial

port="/dev/ttyUSB0"
ser=serial.Serial(port, 9600, timeout=1)

time.sleep(3)
ser.reset_input_buffer()

while (True):
    if (ser.in_waiting>0):
        data=ser.read(ser.in_waiting)
        for byte in data:
            print(byte)

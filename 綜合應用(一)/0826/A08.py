import time
import serial

port="/dev/ttyUSB0"
ser=serial.Serial(port, 9600, timeout=1)

time.sleep(3)
ser.reset_input_buffer()

while (True):
    if (ser.in_waiting>0):
        line=ser.readline()
        text=line.decode("utf-8", errors="ignore").strip()
        if not text:
            continue
        try:
            value=int(text)
        except ValueError:
            print(f"non-digital:{text!r}")
            continue
        print(value)

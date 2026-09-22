import time
import board
import adafruit_dht

# 使用 GPIO 4 引腳
dhtDevice = adafruit_dht.DHT22(board.D4)

while True:
    try:
        # 讀取溫度和濕度
        temperature = dhtDevice.temperature
        humidity = dhtDevice.humidity
        
        # 顯示結果
        print(f'溫度: {temperature:.1f}°C 濕度: {humidity:.1f}%')

    except RuntimeError as error:
        # DHT22 可能會偶爾傳遞錯誤數據，需要忽略
        print(f"讀取失敗，錯誤訊息: {error.args[0]}")

    time.sleep(2.0)
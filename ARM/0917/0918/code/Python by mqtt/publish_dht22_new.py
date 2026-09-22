import time
import board
import adafruit_dht
import paho.mqtt.client as mqtt
import json

# MQTT 設定
MQTT_BROKER = '192.168.63.40'
MQTT_TOPIC = 'dht22/data'
MQTT_USERNAME = 'cubie'
MQTT_PASSWORD = '1234'

# 初始化 DHT22（接在 GPIO4）
dhtDevice = adafruit_dht.DHT22(board.D4)

# 設定 MQTT client 並登入
client = mqtt.Client()
client.username_pw_set(MQTT_USERNAME, MQTT_PASSWORD)
client.connect(MQTT_BROKER, 1883, 60)
client.loop_start()
print("開始讀取 DHT22，按 Ctrl+C 可中止")

try:
    while True:
        try:
            temperature = dhtDevice.temperature
            humidity = dhtDevice.humidity

            print(f'溫度: {temperature:.1f}°C 濕度: {humidity:.1f}%')

            payload = json.dumps({
                'temperature': temperature,
                'humidity': humidity
            })

            client.publish(MQTT_TOPIC, payload)

        except RuntimeError as error:
            print(f"讀取失敗，錯誤訊息: {error.args[0]}")

        time.sleep(2.0)

except KeyboardInterrupt:
    print("\n偵測到 Ctrl+C，準備結束程式...")

finally:
    client.loop_stop()
    client.disconnect()
    dhtDevice.exit()
    print("已正常關閉 DHT22 與 MQTT 連線")






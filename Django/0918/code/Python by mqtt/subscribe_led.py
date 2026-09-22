import RPi.GPIO as GPIO
import paho.mqtt.client as mqtt

# MQTT 設置
MQTT_BROKER = '192.168.57.246' # MQTT 代理的地址
MQTT_TOPIC = 'led/control' # 用於 LED 控制訊息的訂閱主題
MQTT_USERNAME = 'cubie'
MQTT_PASSWORD = '1234'

# GPIO 設置
LED_PIN = 17
GPIO.setmode(GPIO.BCM)
GPIO.setup(LED_PIN, GPIO.OUT)

def on_connect(client, userdata, flags, rc):
    print(f"Connected with result code {rc}")
    client.subscribe(MQTT_TOPIC)

def on_message(client, userdata, msg):
    message = msg.payload.decode()
    if message == 'on':
        GPIO.output(LED_PIN, GPIO.HIGH)
    elif message == 'off':
        GPIO.output(LED_PIN, GPIO.LOW)

client = mqtt.Client()
client.username_pw_set(MQTT_USERNAME, MQTT_PASSWORD)
client.on_connect = on_connect
client.on_message = on_message

client.connect(MQTT_BROKER, 1883, 60)

try:
    client.loop_forever()
except KeyboardInterrupt:
    GPIO.cleanup()





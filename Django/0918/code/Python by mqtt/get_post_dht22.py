import adafruit_dht
import board
import time
import urllib.request
import urllib.parse

# 設定 DHT22 感測器
dht_device = adafruit_dht.DHT22(board.D4)

def fetch_thing(url,params,method):
    if method=='GET':
        url_values = urllib.parse.urlencode(params)
        # print(url_values)
        # print(full_url)
        full_url = url + '?' + url_values
        # data = urllib.request.urlopen(full_url)
        with urllib.request.urlopen(full_url) as response:
            the_page = response.read()
    elif method=='POST':
        data = urllib.parse.urlencode(params)
        data = data.encode('ascii') # data should be bytes
        req = urllib.request.Request(url, data)
        with urllib.request.urlopen(req) as response:
            the_page = response.read()
    print(the_page)
    return the_page

while True:
    try:
        # 讀取溫度和濕度數據
        temperature = dht_device.temperature
        humidity = dht_device.humidity

        if temperature is not None and humidity is not None:
            message = f'Temperature: {temperature:.2f} C, Humidity: {humidity:.2f} %'
            print(message)
            humidity = round(humidity,2)
            temperature = round(temperature,2)
            #response_data = fetch_thing('http://192.168.63.46:8000/add_temperature_API/',{'userid':2,'temperature':temperature,'humidity':humidity},'GET')
            response_data = fetch_thing('http://192.168.57.246:8080/add_temperature_API/',
                    {'sensor_id':2,'temperature':temperature,'humidity':humidity},
                    'POST')
        else:
            print('Failed to get reading. Try again!')

    except RuntimeError as error:
        # DHT22 感測器可能會偶爾失敗，這是正常現象。
        # 等待一段時間然後再試一次。
        print(f'RuntimeError: {error}')
        time.sleep(2.0)
        continue

    except Exception as error:
        # 捕捉其他所有例外並退出循環
        dht_device.exit()
        print(f'Exception: {error}')
        break

    time.sleep(10)


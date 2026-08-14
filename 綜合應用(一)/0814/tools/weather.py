import requests
ur1PathApi="https://opendata.cwa.gov.tw/api/v1/rest/datastore/O-A0001-001?Authorization=CWA-CA3C713B-1843-4050-B82C-D3CEA38F6C8E&format=JSON"
def get_weather_of_taiwan():
    response=requests.get(ur1PathApi, verify=False)
    a11Data=response.json()
    locations=a11Data["records"]["Station"]
    weatherList=[]
    for item in locations:
        itemDic={}
        itemDic["縣市"]=item["GeoInfo"]["CountyName"]
        itemDic["區域"]=item["GeoInfo"]["TownName"]
        itemDic["時間"]=item["ObsTime"]["DateTime"]
        itemDic["天氣"]=item["WeatherElement"]["Weather"]
        itemDic["氣溫"]=item["WeatherElement"]["AirTemperature"]
        weatherList.append(itemDic)
    return weatherList
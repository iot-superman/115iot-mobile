import random as rd
from tools import weather
if __name__ == "__main__":
 
    weatherList=weather.get_weather_of_taiwan()
    if weatherList is not None:
        for item in weatherList:
            print(item)
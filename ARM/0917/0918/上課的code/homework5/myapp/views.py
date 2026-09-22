from django.shortcuts import render
from django.http import HttpResponse
from myapp.models import *
from django.forms.models import model_to_dict
from django.http import JsonResponse

def view_history_temperature(request):
    resultList = Temperature_db.objects.all().order_by('-timestamp')
    for data in resultList:
        print(model_to_dict(data))

    # return HttpResponse("Viewing history of temperature.")
    return render(request, 'view_history_temperature.html', {'resultList': resultList})
from django.views.decorators.csrf import csrf_exempt
@csrf_exempt
def add_temperature_API(request):
    try:
        if request.method == 'GET':
            sensor_id = request.GET['sensor_id']
            temperature = request.GET['temperature']
            humidity = request.GET['humidity']
            print("GET")
            print(f"sensor_id: {sensor_id}, temperature: {temperature}, humidity: {humidity}")
        elif request.method == 'POST':
            sensor_id = request.POST['sensor_id']
            temperature = request.POST['temperature']
            humidity = request.POST['humidity']
            print("POST")
            print(f"sensor_id: {sensor_id}, temperature: {temperature}, humidity: {humidity}")
        Temperature_db.objects.create(sensor_id=sensor_id, temperature=temperature, humidity=humidity)
        return JsonResponse({"status": "success"})
    except:
        return JsonResponse({"status": "error"})
    # return HttpResponse("Adding temperature via API.")
from django.shortcuts import render
from django.http import HttpResponse
from myapp.models import *
from django.forms.models import model_to_dict

def view_history_temperature(request):
    resultList = Temperature_db.objects.all().order_by('-timestamp')
    for data in resultList:
        print(model_to_dict(data))

    # return HttpResponse("Viewing history of temperature.")
    return render(request, 'view_history_temperature.html', {'resultList': resultList})

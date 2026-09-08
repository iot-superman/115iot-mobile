from django.shortcuts import render
from django.http import HttpResponse
from myapp.models import *
from django.forms.models import model_to_dict

def test(request):
    # # orm
    # datas = Students.objects.all()
    # # print(datas)
    # # 檢查資料內容
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # datas = Students.objects.values('cID','cName','cAddr')
    # for data in datas:
    #     print(data)
    #####################################
    #　想知道全班有幾種性別
    # datas = Students.objects.values('cSex').distinct()
    # # print(datas)
    # for data in datas:
    #     print(data)
    #####################################
    #　想要由 students 資料表中挑出 cID 的學生資料 
    # data = Students.objects.get(cID='2') #只會取得一筆資料
    # print(model_to_dict(data))
    # 想要由 students 資料表中挑出所有男性的資料 
    # datas = Students.objects.filter(cSex='M') #有可能一筆或多筆資料
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # __gte: greater than or equal to, >=
    # __lte: less than or equal to, <=
    # __gt: greater than, >
    # __lt: less than, <
    # 想要由 students 資料表中找出座號大於 5 的男生
    # datas = Students.objects.filter(cID__gt=5, cSex='M')
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # from django.db.models import Q
    # datas = Students.objects.filter(Q(cID=1)|Q(cID__gte=8))
    # # datas = Students.objects.filter(Q(cID__gt=5) & Q(cSex='M'))
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # 由 students 資料表中找出座號大於等於 4 且小於等於 6 的學生資料 
    # datas = Students.objects.filter(cID__range=(4, 6))
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # 想要由 students 資料表中找出座號為 1,3,5,7,9 的學生資料：
    datas = Students.objects.filter(cID__in=[1,3,5,8,9])
    for data in datas:
        print(model_to_dict(data))
    return HttpResponse("This is a test view.")

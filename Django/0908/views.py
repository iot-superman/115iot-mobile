from django.shortcuts import render
from django.http import HttpResponse
from myapp.models import *
from django.forms.models import model_to_dict

def test(request):
    # # orm
    # datas = Student.objects.all()
    # # print(datas)
    # # 檢查資料內容
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # datas = Student.objects.values('cID','cName','cAddr')
    # for data in datas:
    #     print(data)
    #####################################
    #　想知道全班有幾種性別
    # datas = Student.objects.values('cSex').distinct()
    # # print(datas)
    # for data in datas:
    #     print(data)
    #####################################
    #　想要由 Student 資料表中挑出 cID 的學生資料 
    # data = Student.objects.get(cID='2') #只會取得一筆資料
    # print(model_to_dict(data))
    # 想要由 Student 資料表中挑出所有男性的資料 
    # datas = Student.objects.filter(cSex='M') #有可能一筆或多筆資料
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # __gte: greater than or equal to, >=
    # __lte: less than or equal to, <=
    # __gt: greater than, >
    # __lt: less than, <
    # 想要由 Student 資料表中找出座號大於 5 的男生
    # datas = Student.objects.filter(cID__gt=5, cSex='M')
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # from django.db.models import Q
    # datas = Student.objects.filter(Q(cID=1)|Q(cID__gte=8))
    # # datas = Student.objects.filter(Q(cID__gt=5) & Q(cSex='M'))
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # 由 Student 資料表中找出座號大於等於 4 且小於等於 6 的學生資料 
    # datas = Student.objects.filter(cID__range=(4, 6))
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # 想要由 Student 資料表中找出座號為 1,3,5,7,9 的學生資料：
    # datas = Student.objects.filter(cID__in=[1,3,5,8,9])
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # :想要由 Student 資料表中，出電話號碼是「0918」開頭的學生資料
    # datas = Student.objects.filter(cPhone__startswith='0918')
    # for data in datas:
    #     print(model_to_dict(data))
    #  想要由 Student 資料表中，找出學生的地址中有「建國」這個字的資料
    # datas = Student.objects.filter(cAddr__contains='建國')
    # for data in datas:
    #     print(model_to_dict(data))
    #####################################
    # 想要由 Student 資料表所有同學的資料依生日遞減排序 
    # datas = Student.objects.all().order_by('-cBirthday') # 依生日遞減排序
    # datas = Student.objects.all().order_by('cBirthday') # 依生日遞增排序
    # 想要由 Student 資料表所有同學的資料依性別遞增排序，再依生日遞減排序 
    # datas = Student.objects.all().order_by('cSex', '-cBirthday') # 依性別遞增排序，再依生日遞減排序

    # for data in datas:
    #     print(model_to_dict(data))   
    ##################################### 
    # datas = Student.objects.all()[:2] # 只取前兩筆資料
    #datas = Student.objects.all()[0:2] # 只取前兩筆資料,0起始索引,2結束索引(不包含)
    # datas = Student.objects.all()[4:6] # 只取第 5 筆到第 6 筆資料,4起始索引,6結束索引(不包含)
    # for data in datas:
    #     print(model_to_dict(data))
    ##################################### 
    # 想要算出全班國文、英文及數學總分
    from django.db.models import Sum, Count, Max, Min, Avg
    # data = Scorelist.objects.aggregate(Sum('score'))
    # print(f"Total score: {data}")
    # 想要算出全班國文總分 
    # data = Scorelist.objects.filter(course='國文').aggregate(Sum('score'))
    # print(f"Total Chinese score: {data}")
    ##################################### 
    # 想要顯示每個學生的總分
    #datas = Scorelist.objects.values('cID').annotate(total_score=Sum('score'))
    # datas = Scorelist.objects.values('cID').annotate(Avg('score'))
    #datas2 = Scorelist.objects.values_list('cID').annotate(total_score=Sum('score'))
    # values vs values_list:差異在於values回傳的是字典列表，而values_list回傳的是元組列表
    # print(datas2)
    # print(datas)
    # for data in datas:
    #     print(data)
    ##################################### 
    # 想要顯示座號 1 到 5 同學的分數總計
    # datas = Scorelist.objects.filter(cID__lte=5).values('cID').annotate(total_score=Sum('score'))
    # for data in datas:
    #     print(data)
    ##################################### 
    # add data
    # 第一種新增資料方式
    student_exists = Student.objects.filter(cName='Bill4').exists()  # 檢查名字為 Bill1 的學生是否存在
    if not student_exists:
        Student.objects.create(cName='Bill4', cSex='M', cBirthday='2000-01-01', 
                               cPhone='0912345678', cAddr='新竹',cEmail = 'bill4@example.com',
                               cHeight = 170, cWeight = 65)  # 若不存在則新增一筆資料
        print("Added new student")
    else:
        print("Student already exists")

    # 第二種新增資料方式
    # student_exists = Student.objects.filter(cName='Bill3').exists()  # 檢查名字為 Bill3 的學生是否存在
    # if not student_exists:
    #     add = Student(cName='Bill3', cSex='M', cBirthday='2000-01-01', 
    #                   cPhone='0912345678', cAddr='新竹',cEmail = 'bill3@example.com', 
    #                   cHeight = 170, cWeight = 65)
    #     add.save()
    #     print("Added new student")
    # else:
    #     print("Student already exists")
    ##################################### 
    # update data
    # 要修改座號為 11 同學的信箱
    # 單筆更新
    # try:
    #     update = Student.objects.get(cID=11)
    #     update.cEmail = 'bill1@example.com'
    #     update.save()
    #     print("Updated student")
    # except Student.DoesNotExist:
    #     print("does not exist.")
    # # 多筆更新
    # try:
    #     Student.objects.filter(cID__gte=11).update(cAddr='台北')  # 將所有座號大於等於 11 的學生地址更新為 新竹
    #     print("Updated students")
    # except Exception:
    #     print("Error occurred.")
    ##################################### 
    # delete data
    # 單筆刪除
    # try:
    #     delete = Student.objects.get(cID=11)
    #     delete.delete()
    #     print("Deleted student")
    # except Student.DoesNotExist:
    #     print("does not exist.")
    # # 多筆刪除
    # try:
    #     Student.objects.filter(cID__gte=11).delete()  # 將所有座號大於等於 11 的學生刪除
    #     print("Deleted students")
    # except Exception:
    #     print("Error occurred.")

    return HttpResponse("This is a test view.")

from django.shortcuts import render
from django.http import HttpResponse
from datetime import datetime

def sayhello(request):
    return HttpResponse("<b>Hello Django, number 40!!!!</b>")

def hello1(request, username):
    print(username)
    return HttpResponse(f"<b>Hello {username}!</b>")

def hello2(request, username):
    print(username)
    now = datetime.now() #取得當前時間
    print(now)
    # return HttpResponse("Hello!")
    return render(request, "hello2.html",locals())


def hello3(request, username):
    print(username)
    now = datetime.now() #取得當前時間
    print(now)
    # return HttpResponse("Hello!")
    return render(request, "hello3.html",locals())

def hello4(request, username1, username2):
    print(f"username1: {username1}, username2: {username2}")
    return HttpResponse(f"<b>hello {username1} and {username2}!</b>")

import random
def dice1(request):
    no1 = random.randint(1,6) # 產生1~6的隨機整數
    no2 = random.randint(1,6)
    no3 = random.randint(1,6)
    print(f"no1: {no1}, no2: {no2}, no3: {no3}")

    # return HttpResponse("<b>Dice1!</b>")
    #return render(request, "dice1.html",locals())
    return render(request, "dice1.html", {'no1': no1, 'no2': no2, 'no3': no3})

def dice2(request):
    student = {'id':1234,'name':'John','sex':'M'}
    fruit = ['apple','banana','orange']
    # return HttpResponse("<b>Dice2!</b>")
    # return render(request, "dice2.html", locals())
    return render(request, "dice2.html", {'s': student, 'f': fruit})

def dice3(request):
    person1 = {'name':'John','phone':'09111111','age':20}
    person2 = {'name':'Mary','phone':'09222222','age':31}
    person3 = {'name':'Tom','phone':'09333333','age':42}
    persons = [person1, person2, person3]
    print(persons)
    # return HttpResponse("<b>Dice3!</b>")
    # persons=[] #假設 persons 是空的，測試迴圈
    return render(request, "dice3.html", {'persons': persons})

# basci-07.py
def get1(request):
    if request.method == 'GET':
        # name = request.GET["name"]
        # city = request.GET["city"]
        name = request.GET.get("name", None) #若沒有name參數，預設為Guest
        city = request.GET.get("city", None) #若沒有city參數，預設為None
        print(f"name: {name}, city: {city}")
    # return HttpResponse(f"<b>get1</b>")
    return render(request, "get1.html", locals())


def get2(request):
    if request.method == 'GET':
        try:
            name = request.GET["name"]
            city = request.GET["city"]
            print(f"name: {name}, city: {city}")
            status = True
        except:
            status = False
            print("name or city is missing")

    # return HttpResponse(f"<b>get1</b>")
    return render(request, "get2.html", locals())

from django.shortcuts import redirect
def get3(request, mode):
    # print(f"mode: {mode}")
    if mode == "save":
        # username = request.GET["username"]
        # passwd = request.GET["passwd"]
        username = request.GET.get("username", None)
        passwd = request.GET.get("passwd", None)

        # 若帳號或密碼為空，重新導向到 /get3/load/ 網址
        if username is None or passwd is None:
            return redirect("/get3/load/") # 重新導向到 /get3/load/ 網址

        # 遮蔽密碼，只顯示最後三個字元
        if len(passwd) > 3:
            passwd = '*' * (len(passwd) - 3) + passwd[-3:]
        else:
            passwd = '*' * len(passwd)  # 若長度小於等於3，全部遮蔽

        print(f"username: {username}, passwd: {passwd}")
        # return HttpResponse(f"<b>已送出</b>")
        return render(request, "get3_response.html", locals())
    elif mode == "load":
        return render(request, "get3.html", locals())
    return HttpResponse(f"<b>網址錯誤!</b>")

def post1(request):
    if request.method == 'POST':
        # username = request.POST.get("username", None)
        # passwd = request.POST.get("passwd", None)
        username = request.POST["username"]
        passwd = request.POST["passwd"]
        print(f"username: {username}, passwd: {passwd}")

        if username == "david" and passwd == "1234":
            status = True
            print("登入成功")
        else:
            status = False
            print("登入失敗")
        # return HttpResponse(f"<b>已送出</b>")
        return render(request, "post1_response.html", {"status": status, "username": username})
    else:
        return render(request, "post1.html")
    # return HttpResponse(f"<b>post1</b>")

def post1_old(request):
    if request.method == 'POST':
        # username = request.POST.get("username", None)
        # passwd = request.POST.get("passwd", None)
        username = request.POST["username"]
        passwd = request.POST["passwd"]
        print(f"username: {username}, passwd: {passwd}")

        if username == "david" and passwd == "1234":
            status = True
            print("登入成功")
        else:
            status = False
            print("登入失敗")
        # return HttpResponse(f"<b>已送出</b>")
        return render(request, "post1_old_response.html", {"status": status, "username": username})
    else:
        return render(request, "post1_old.html")
    # return HttpResponse(f"<b>post1</b>")    


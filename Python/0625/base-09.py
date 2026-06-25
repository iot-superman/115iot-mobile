import os

os.system('cls')

var1=100
if(var1==100):print("var1=100")
print("Good bye!")

var2=80
if(var2==80):
    print("var2=80")
print("Good bye!")
pw = input("請輸入密碼：")

print(type(pw))
if(pw=="123456"):     # 檢查密碼是否正確一定要用""引號
    print("密碼正確")
else:
    print("密碼錯誤")


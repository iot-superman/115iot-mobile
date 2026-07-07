try:
    num1= int(input("請輸入數字1："))
    num2= int(input("請輸入數字2："))
    print(f"數字1是：{num1}")
    r = num1%num2
except ValueError:
    print("輸入的不是數字")
except Exception as e:
    print(f"未知錯誤：{e}")
else:
    print(f"程式執行完成ans={r}")
finally:
    print("一定會執行的程式碼")

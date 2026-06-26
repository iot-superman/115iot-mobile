import os

# os.system('cls')

# 第3題：單位轉換
print("...............3..............")
print("要轉換 (1)公尺->英尺 (2)公斤->英磅：")
choice = input("請輸入欲轉換的數字：")

if choice == "1":
    meters = float(input("請輸入欲轉換的數字："))
    feet = meters * 3.28
    print(f"{meters:.6f}公尺={feet:.6f}英尺")
elif choice == "2":
    kg = float(input("請輸入欲轉換的數字："))
    pounds = kg * 2.2
    print(f"{kg:.6f}公斤={pounds:.6f}英磅")
else:
    print("無此選項")

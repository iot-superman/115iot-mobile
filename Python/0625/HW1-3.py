import os

os.system('cls')

#  第1題：

# 根據使用者輸入梯形的上底、下底、高的數值，計算其梯形面積，梯形面積的計

# 結果如下：
print("...................1..............")
upper_base = float(input("輸入上底長度："))
lower_base = float(input("輸入下底長度："))
height = float(input("輸入高長度："))
area = (upper_base + lower_base) / 2 * height
print(f"梯形面積為：{area:.2f}")

print("...............2..............")

"""
"""

# 第2題：BMI計算
height = float(input("輸入身高(cm)："))
weight = float(input("輸入體重(kg)："))
bmi = weight / (height / 100) ** 2
print(f"BMI值為{bmi:.2f}，", end="")

if bmi < 18.5:
    print("屬體重過輕")
elif 18.5 <= bmi < 24:
    print("屬正常範圍")
elif 24 <= bmi < 27:
    print("屬稍重")
elif 27 <= bmi < 30:
    print("屬輕度肥胖")
elif 30 <= bmi < 35:
    print("屬中度肥胖")
else:
    print("屬重度肥胖")

print("...............3..............")
# 第3題：單位轉換
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

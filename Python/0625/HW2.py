import os

# os.system('cls')

# 第2題：BMI計算
print("...............2..............")
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

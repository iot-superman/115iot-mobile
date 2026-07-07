height = float(input("請輸入身高（公尺）: "))
weight = float(input("請輸入體重（公斤）: "))

bmi = weight / (height ** 2)
print("你的身高是bmi：", bmi, "公尺")
print("你的 BMI 是：", round(bmi, 2))

if bmi < 18.5:
    print("體重過輕")
elif bmi < 24:
    print("正常範圍")
elif bmi < 27:
    print("過重")
elif bmi < 30:
    print("輕度肥胖")
elif bmi < 35:
    print("中度肥胖")
else:
    print("重度肥胖")
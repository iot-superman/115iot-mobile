# eval  eval() 是 Python 的內建函式，用來執行字串形式的運算式並回傳結果。
 
height=eval(input("請輸入您的身高（公分）："))
weight=eval(input("請輸入您的體重（公斤）："))  
BMI = weight / (height / 100) ** 2
print("您的BMI值為：", BMI)
if(BMI<18.5):
    print("體重過輕")
elif(BMI<24):
    print("體重正常")
elif(BMI<27):
    print("體重過重")
else:
    print("肥胖")

import os

os.system('cls')

ch = input("請輸入國文成績：")
math = input("請輸入數學成績：")
eng = input("請輸入英文成績：")
print(f"國文成績：{ch},數學成績：{math},英文成績：{eng}")
# print(type(ch))
# print(type(math))
# print(type(eng))
sum =  int(ch) + int(math) + int(eng) # 轉換為整數後相加

avg = sum / 3 # 除以3得到平均分
 
# print(type(avg))

print(f"總成績：{sum},平均成績：{avg}")  
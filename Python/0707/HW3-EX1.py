#運用while迴圈，寫出一個程式，讓使用者輸入分數，將之存入串列(list)之中。輸入「-1」會停止輸入，計算成績的總分與平均，最後印出總分與平均分數。
 
num_list = []
num = int(input("請輸入分數："))
while num != -1:
    num_list.append(num)
    num = int(input("請輸入分數："))
print(f"總分是：{sum(num_list)}分, 全班平均是：{sum(num_list)/len(num_list)}")
        
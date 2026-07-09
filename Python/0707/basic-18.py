score = total = person =0 
while True:
    score = int(input("請輸入分數(輸入-1結束):"))
    if score == -1:
        break
    total += score
    person += 1

if person > 0:
    average = total / person
    print(f"總分: {total}, 人數: {person}, 總分： {total}平均分數: {average:.2f}")
else:
    print("沒有輸入有效的分數。")
 
 # 運用while迴圈，寫出一個程式，顯示使用者每次的捐款次數與金額，
 
 
total  = 0
count = 0
while True:
    count += 1
    money = int(input(f"請輸入第{count}次捐款金額：(按0結束)"))
    if money == 0:
        count -= 1
        break
    total += money
    print(f"{count}次捐款金額是：{money}")

print(f"總捐金額合計：{total}")

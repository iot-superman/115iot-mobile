print(".........1........")
for i in range(1, 11):
    if (i==6):
        break # 跳出迴圈
    print(i)

print(".........2........")
for i in range(1, 11):
    if (i==6):
        continue # 跳過本次迴圈，繼續下一次迴圈
    print(i)
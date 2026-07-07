
print("..........1.........")
#break
for num in range(1, 11):
    if (num==6):
        break  #跳出迴圈
    print(num)

print("..........2.........")
#continue
for num in range(1, 11):
    if (num==6):
        continue  #跳過本次迴圈，進入下一次迴圈
    print(num)  


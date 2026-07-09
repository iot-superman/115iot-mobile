
# 1+2+3+4+5+...+num
num = int(input("請輸入一個正整數："))
sum = 0
i=1
while(i<=num):
    print(f"the number is {i}")
    sum += i # sum = sum + i
    i += 1 # i = i + 1
print(f"total:{num} = {sum}")
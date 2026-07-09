
#1+2+3.... +num
num = int(input("請輸入一個正整數："))
sum = 0
i = 1
while i <= num:
    print(f"i={i}, sum={sum}")
    sum += i
    i += 1

print(f"1+2+3+...+{num} = {sum}")
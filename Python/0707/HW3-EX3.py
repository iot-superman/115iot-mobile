import random
"""
大樂透中獎號碼6個1到49之間的數字，再加一個特別號:撰寫程式取得大樂透中獎號碼(第一次取得就是特別號)，並由小到大顯示方便對獎。
"""
numbers = random.sample(range(1, 50), 7)
numbers[1:] = sorted(numbers[1:])
print("大樂透中獎號碼是：")
for i in numbers[1:]:
    if i != numbers[-1]:
        print(i, end=" ")
    else:
        print(i, end="")
print()
print(f"特別號是：{numbers[0]}")
    
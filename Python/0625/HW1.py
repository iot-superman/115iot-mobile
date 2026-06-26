import os

os.system('cls')

# 第1題：梯形面積
print("...................1..............")
upper_base = float(input("輸入上底長度："))
lower_base = float(input("輸入下底長度："))
height = float(input("輸入高長度："))
area = (upper_base + lower_base) / 2 * height
print(f"梯形面積為：{area:.2f}")

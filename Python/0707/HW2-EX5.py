 
#請利用for迴圈寫出如下圖所示的4層金字塔圖形。
print("===========1========")
for i in range(1, 5):
    for j in range(1, i+1):
        print("*", end="")
    print()
print("===========2========")

for i in range(4):
    for j in range(4 - i - 1):
        print(" ", end="")
    for k in range(2 * i + 1):
        print("*", end="")
    print()
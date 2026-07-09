print("........1.........")
# 10到20之間的數字是否為質數
# for num in range(10, 21):
#     print(f"num={num}")
#     for i in range(2, num):
#         print(i)

print("........2.........")
# 10到20之間的數字是否為質數
for num in range(10, 21):
    # for else: 若for迴圈沒有被break中斷，則執行else區塊
    for i in range(2, num):
        if num%i == 0:
            print(f"{num}不是質數")
            break
    else:
        print(f"{num}是質數")

print("........3.........")
# 10到20之間的數字是否為質數
for num in range(10, 21):
    # for else: 若for迴圈沒有被break中斷，則執行else區塊
    for i in range(2, int(num/2)+1): #根據因數反覆運算，計算到num的一半即可
        if num%i == 0:
            print(f"{num}不是質數")
            break
    else:
        print(f"{num}是質數")

print("........4.........")
# 10到20之間的數字是否為質數
for num in range(10, 21):
    # for else: 若for迴圈沒有被break中斷，則執行else區塊
    for i in range(2, int(num**0.5)+1): #根據因數反覆運算，計算到num平方根即可
        if num%i == 0:
            print(f"{num}不是質數")
            break
    else:
        print(f"{num}是質數")
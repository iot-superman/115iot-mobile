# 10-20之間的數字是否為質數
#Coplit veriosn
# def is_prime(num):
#     if num < 2:
#         return False
#     for i in range(2, int(num**0.5) + 1):
#         if num % i == 0:
#             return False
#     return True

# for num in range(10, 21):
#     if is_prime(num):
#         print(f"{num} 是 質數")
#     else:
#         print(f"{num} 不是 質數")    


# 老師版
# 若for迴圈沒有被break中斷，則執行else區塊

print("10-20之間的數字是否為質數")
print("..........1..........")
for num in range(10, 21):
    for i in range(2, num):
        if num % i == 0:
            print(f"{num} 不是 質數")
            break
    else:
        print(f"{num} 是 質數")


print("..........2..........")
for num in range(10, 21):
    for i in range(2, num):
        if num % i == 0:
            print(f"{num} 不是 質數")
            break
    else:
        print(f"{num} 是 質數")


print("..........3..........")
for num in range(10, 21):
    for i in range(2, int(num/2) + 1):  #根據數學反覆運算，計算到num的一半即可
        if num % i == 0:
            print(f"{num} 不是 質數")
            break
    else:
        print(f"{num} 是 質數")

print("..........4..........")
for num in range(10, 21): 
    for i in range(2, int(num**0.5) + 1):  #根據數學反覆運算，計算到num的平方根即可
        if num % i == 0:
            print(f"{num} 不是 質數")
            break
    else:
        print(f"{num} 是 質數")
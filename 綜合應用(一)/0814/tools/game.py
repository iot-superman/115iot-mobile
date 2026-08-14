import random


def guess_num():
    min=1
    max=100
    count=0
    target=random.randint(min,max)
    print("請猜一個介於",min,"到",max,"的數字")
    while True:
        guess=int(input("請輸入您的猜測："))
        if guess<target:
            print("太小了！")
            count+=1
            print("你已經猜了", count, "次。")
        elif guess>target:
            print("太大了！")
            count+=1
            print("你已經猜了", count, "次。")        
        else:
            print("恭喜你猜對了！你總共猜了", count, "次。")
            break

 
 
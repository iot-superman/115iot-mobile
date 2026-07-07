# 不固定數量的關鍵字參數
def sell(**price):
    print(price)

print("......1......")
sell(apple=10, banana=20, orange=30) 

print("......2......")
data = {"apple": 10, "banana": 20, "orange": 30}
print(data) 
sell(**data) #解包元組,則凁待維，格到改為Dict格式
# sell(data) #false

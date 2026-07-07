# *: 可變長度位置參數
def buy(*price):
    print(price)

####################
print("................1................") 
buy(1,2,3) #tuple
buy("ABC","DEF",3) #tuple

print("................2................") 
data =("Bill",1,"TT")
buy(*data) #tuple   ,  解包元組,則凁待維，格到改為Tuple格式

buy(data) #tuple 會多一層Tuple格式

print("................3................") 

data =["Bill",2,"TT"]
buy(*data) #tuple   ,  解包元組,則凁待維，格到改為Tuple格式

buy(data) #tuple 會多一層Tuple格式
#input 0 output 0
def test():
    print("Hello, World!")


#input 2, output 1    #多對一
def mul(x,y):
    return x*y
def minus(x,y):
    return x-y

def add(x,y):
    return x+y

def div(x,y):
    return x/y


#####################
# input 2, output 1 #多對一
print(".........1........")
test()
test()
print(".........2........")
print(f"mul(2,3): {num1}")
num2 = minus(5,7)
print(f"minus(5,7): {num2}")
num3 = div(10,2)
print(f"div(10,2): {num3}") 
num4 = add(10,5)
print(f"add(10,5): {num4}")

#iput 2, output 4  # 多對多
def  operations1(x,y):
    mul = x*y
    add = x+y
    div = x/y
    minus = x-y
    return [mul,add,div,minus]

def operations2(x,y):
    mul = x*y
    add = x+y
    div = x/y
    minus = x-y
    return mul,add,div,minus

print(".........3........")
list_num = operations1(9,2)
for vale in list_num:
    print(vale)

print(".........4........")
num_num1, num_num2, num_num3, num_num4 = operations2(9,2)
print(f"mul: {num_num1}")
print(f"add: {num_num2}")
print(f"div: {num_num3}")
print(f"minus: {num_num4}") 

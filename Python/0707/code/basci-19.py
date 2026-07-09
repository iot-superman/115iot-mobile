# input 0 output 0
def test():
    print("Hello, World!")
# input 2 output 1 #多對一
def mul(x,y):
    return x * y
def add(x,y):
    return x + y
def div(x,y):
    return x / y
def minus(x,y):
    return x - y
# input 2 output 1(4) #多對多
def operations1(x,y):
    mul = x * y
    add = x + y
    div = x / y
    minus = x - y
    return [mul, add, div, minus]
def operations2(x,y):
    mul = x * y
    add = x + y
    div = x / y
    minus = x - y
    return mul, add, div, minus
#########################
print(".......1.........")
test()
test()
print(".......2.........")
num1 = mul(9,2)
print(f"mul(9,2) = {num1}")
num2 = add(9,2)
print(f"add(9,2) = {num2}")
num3 = div(9,2)
print(f"div(9,2) = {num3}")
num4 = minus(9,2)
print(f"minus(9,2) = {num4}")
print(".......3.........")
list_num = operations1(9,2)
for value in list_num:
    print(value)
print(f"mul(9,2) = {list_num[0]}")
print(f"add(9,2) = {list_num[1]}")
print(f"div(9,2) = {list_num[2]}")
print(f"minus(9,2) = {list_num[3]}")
print(".......4.........")
num_num1, num_num2, num_num3, num_num4 = operations2(9,2)
print(f"mul(9,2) = {num_num1}")
print(f"add(9,2) = {num_num2}")
print(f"div(9,2) = {num_num3}")
print(f"minus(9,2) = {num_num4}")


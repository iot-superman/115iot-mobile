import os
"""
tuple 元組型態
"""
os.system('cls')
print("...................1..............")
#tuple
tuple_data = ('abc', 786, 2.23, 'John', 70.2, 786, 786)
print(tuple_data)
tinytuple = ('123', 'Joe')
print(tuple_data)
print(tinytuple)
print(tinytuple[0])
print(tinytuple[1:3])
print(tinytuple[1:])
print(tinytuple*2)
print(tuple_data+tinytuple)
print("######################")
print("...................2..............")
  
# tuple_data[1] = "aaaa"    # false不能更新元組的元素
# print("......update......")
# print(tuple_data)

#走訪元組


print(tuple_data[2])
for item in tuple_data:
    print(item)

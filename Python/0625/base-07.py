import os
f"""
Dictionary 字典型態  
"""
os.system('cls')
print("...................1..............")
# dict
dict1 = {'name': 'John', 'code': 6734, 'dept': 'sales'}
print(dict1)

dict2 = {}
dict2['one'] = "one"
dict2[2] = "two"
print(dict2)

print(".........3..............")
# 修改
dict1['name'] = "Mary"
print(dict1)

print(".........4..............")
# 刪除
# del dict1['code']
print(dict1)

print(".........5..............")
# 取值
print(dict1['dept'])
# print(dict1['sample'])  # error
print(dict1.get('sample'))     # 不存在傳回 None
print(dict1.get('sample', "no"))  # 不存在傳回 "no"
print(dict1.get('dept', "no"))

print(".........6..............")
# 走訪
for key in dict1:
    print(f"{key}->{dict1[key]}")

print(".........7..............")
for item in dict1.items():
    #print(item)
    print(f"{item[0]}->{item[1]}")

print(".........8..............")
#使用ｉｔｅｍｓ，走訪 dict1 中的每個項目
for item in dict1.items():
    key, value = item
    print(f"{key}->{value}")

print(".........9..............")
dict1.clear()    # 清空 dict1
print(dict1)

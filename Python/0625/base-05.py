import os
"""
list 列表型態
"""
os.system('cls')
print("...................1..............")
#list
list_data = ['abc', 786, 2.23, 'John', 70.2, 786, 786]
print(list_data)
tinylist = [123, 'Joe']
print(list_data)
print(tinylist)
print(tinylist[0])
print(tinylist[1:3])
print(tinylist[1:])
print(tinylist*2)
print(list_data+tinylist)
print("##############################")
# update
list_data[2] = "aaaa"
print("......update......")
print(list_data)
#delete
del list_data[1]
list_data.remove(70.2)
print("......delete......")
print(list_data)
#append
list_data.append('dddd')
print("......append......")
print(list_data)
#insert
list_data.insert(1, 'bbbb')  # 插入在索引 1 位置
print("......insert......")
print(list_data)

#sort (使用純數字列表示範，避免混合型別錯誤)
num_list = [3, 1, 4, 1, 5, 9, 2, 6]
num_list.sort()
print("......sort......")
print(num_list)

#reverse (使用 num_list 示範)
num_list.reverse()
print("......reverse (num_list)......")
print(num_list)

#reverse (list_data 示範)
list_data.reverse()
print("......reverse......")
print(list_data)

#length
print(len(list_data))

#count
print(list_data.count(786)) # 786 出現了 2 次
 
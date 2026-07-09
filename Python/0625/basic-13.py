
print("..........1.........")
# String formatting in Python
for letter in "Hello, World!":    # 遍歷字串 str 中的每個字元
    print(f"current letter: {letter}")

#list
print("..........2.........")
fruits = ["apple", "banana", "cherry"]
for fruit in fruits:    # 遍歷列表 list 中的每個元素
    print(f"current fruit: {fruit}")

print("..........3.........")
person = {"name": "Alice", "age": 30, "city": "New York"
           }
for key in person:    # 遍歷字典 dict 中的每個鍵
    print(f"current {key}: {person[key]}")

print("..........4.........")
for key, value in person.items():    # 遍歷字典 dict 中的每個鍵值對
    print(f"current {key}: {value}")

print("..........5.........")
# 將字典 dict 中的鍵值對存入列表 list 中
list_data=[]
for key, value in person.items():    # 遍歷字典 dict 中的每個鍵值對
    # print(f"current {key}: {value}")
    list_data.append(key)
    list_data.append(value)
print(list_data)

#list 內有多個dict or (object)
print("..........6.........")
items = [{"name": "Alice", "score": 30},
         {"name": "Bob", "score": 25},
        {"name": "Charlie", "score": 35}]

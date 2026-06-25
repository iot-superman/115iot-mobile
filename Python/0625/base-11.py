import os

os.system('cls')

dict_data = {"林小明":85, "曾山水":93, "鄭美麗":67}
name = input("輸入學生姓名：") #input 接收任意任性输入
if name in dict_data:  
    print(f"{name}的成績為{dict_data[name]}")
else:
    print("無此學生")
    score = int(input("輸入學生分數："))
    dict_data[name] = score

print(f"字典內容：{dict_data}")

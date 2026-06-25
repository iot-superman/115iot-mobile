from string import Template
print('hello world')

# 1. 基礎%格式化（舊式）
name = "Alice"
age = 30
print("Hello, %s! You are %d years old." % (name, age))  # 輸出：Hello, Alice! You are 30 years old.

# 2. str.format()方法（Python 3.0以上適用）
print("Hello, {}! You are {} years old.".format(name, age))  # 依位置順序
print("Hello, {1}! You are {0} years old.".format(age, name))  # 明確指定索引
print("Hello, {name}! You are {age} years old.".format(name="Bob", age=25))  # 命名參數

# 3. f-string（格式化字符串字面值，Python 3.6以上適用）- 最常用
print(f"Hello, {name}! You are {age} years old.")  # 直接嵌入變數
# 在f-string中使用表達式
print(f"Next year you will be {age + 1} years old.")
# 在f-string中使用格式規範
pi = 3.1415926
print(f"Pi rounded to 2 decimal places: {pi:.2f}")  # 輸出：3.14
print(f"Number padded with zeros: {age:05d}")  # 輸出：00030

# 4. Template字符串（用於安全的字符串替換，需匯入string模組）
t = Template("Hello, $name! You are $age years old.")
print(t.substitute(name="Charlie", age=35))

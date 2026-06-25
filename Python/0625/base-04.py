import os
"""
1. 第 1 行 ：建立字串 str1 = "Hello, World!"
2. 第 2 行 ：印出整個字串 Hello, World!
3. 第 3 行 ：使用索引 str1[4] 取得第 5 個字元（索引從 0 開始），輸出 o
4. 第 4 行 ：使用切片 str1[1:5] 取得索引 1 到 4 的子字串（不含 5），輸出 ello
"""
os.system('cls')
str1 = "Hello, World!"

print(str1) 
print(str1[4])
print(str1[1:5])
print(str1[2:])  # 輸出：ello, World!
print(str1*2)  # 輸出：Hello, World!Hello, World!
print(f"length of str1 is: {len(str1)}")  # 輸出：Hello, World! is a string.

#for in 
for letter in str1:    # 遍歷字串 str1 中的每個字元
    print(f"current letter: {letter}")

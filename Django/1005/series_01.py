import pandas as pd

# 建立 Series
print("..........1 建立 Series..........")
s1 = pd.Series([10, 20, 30, 40, 50])                # 預設索引
s2 = pd.Series([15, 25, 35], index=['a', 'b', 'c']) # 指定索引
print("s1")
print(s1)
print("s2")
print(s2)

# 修改
print("..........2 修改..........")
s1[1] = 99
s2['b'] = 88
print("s1")
print(s1)
print("s2")
print(s2)

# 取值
print("..........3 取值..........")
print(f"s1[0] = {s1[0]}")
print(f"s2['a'] = {s2['a']}")

# 新增
print("..........4 新增..........")
s1[5] = 60          # 新索引 5
s2['d'] = 45        # 新索引 d
print("s1")
print(s1)
print("s2")
print(s2)

# 刪除
print("..........5 刪除..........")
s1_new = s1.drop(2)
s2_new = s2.drop('c')
s1 = s1.drop(2) # 才會真正刪除
print("s1 刪除 index=2")
print(s1_new)
print("s2 刪除 index='c'")
print(s2_new)


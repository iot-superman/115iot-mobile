import pandas as pd

# 建立 Series
s3 = pd.Series([10, 35, 50, 20, 45], index=[0, 1, 2, 3, 4])
s4 = pd.Series([5, 40, 25, 60, 30], index=[0, 1, 2, 3, 4])
print("s3")
print(s3)
print("s4")
print(s4)

# 條件篩選
print("..........1 條件篩選..........")
print("s3 > 30")
print(s3[s3 > 30])

print("s4 <= 40")
print(s4[s4 <= 40])

# 統計方法
print("..........2 統計方法..........")
print("s3 平均:", s3.mean())
print("s4 平均:", s4.mean())
print("s3 最大:", s3.max())
print("s4 最大:", s4.max())

# 運算（索引相同，不會有 NaN）
print("..........3 基本算術運算..........")
print("s3 + s4")
print(s3 + s4)

print("s3 - s4")
print(s3 - s4)

print("s3 * s4")
print(s3 * s4)

print("s3 / s4")
print(s3 / s4)

print("..........4 「數值」運算..........")
print("s3 + 10")
print(s3 + 10)

print("s3 * 2")
print(s3 * 2)

print("s4 - 5")
print(s4 - 5)


print("..........5 比較運算..........")
print("s3 > s4")
print(s3 > s4)

print("s3 == s4")
print(s3 == s4)

print("s3 >= 30")
print(s3 >= 30)


# 排序
print("..........6 排序..........")
print("s3 依值排序")
print(s3.sort_values())
# 遞減排序 s3
print("s3 依值遞減排序")
print(s3.sort_values(ascending=False))

print("s4 依索引排序")
print(s4.sort_index())
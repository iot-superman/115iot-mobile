# 情境設定:
# 老師記錄 5 位學生的期中考成績
# s1：座號成績表（沒有學生姓名）
# s2：學生姓名成績表（有姓名索引）

import pandas as pd
print(".................1..................")
# s1：用座號記錄成績（預設索引）
s1 = pd.Series([60, 70, 80, 90, 100])

# s2：用學生姓名記錄成績（指定索引）
s2 = pd.Series([85, 90, 95], index=['Andy', 'Betty', 'Charlie'])

# 「這兩張表都是成績，只是索引代表的意義不同」

print("座號成績 s1")
print(s1)
print("\n姓名成績 s2")
print(s2)

print(".................2..................")
# 新增資料（補考 / 新轉學生）

# 新增補考學生（座號）
s1[5] = 60
s1[6] = 55

# 新轉學生（姓名）
s2['David'] = 45
s2['Eva'] = 52

print("更新後 s1")
print(s1)
print("\n更新後 s2")
print(s2)

print(".................3..................")
print("\n【條件篩選】")

print("s1 及格（>= 60）")
print(s1[s1 >= 60])

print("\ns2 不及格（< 60）")
print(s2[s2 < 60])
# 老師快速找出「及格學生」和「需要補救教學的學生」

print(".................4..................")
print("\n【統計方法】")

print("s1 平均成績:", s1.mean())
print("s1 最高分:", s1.max())

print("s2 平均成績:", s2.mean())
print("s2 最低分:", s2.min())

print(".................5..................")
print("\n【成績調整（運算）】")

print("s1 加 5 分")
print(s1 + 5)

print("s2 加 5 分")
print(s2 + 5)

print(".................6..................")
print("\n【排序】")

print("s1 成績排名（由高到低）")
print(s1.sort_values(ascending=False))

print("\ns2 成績排名（由低到高）")
print(s2.sort_values())




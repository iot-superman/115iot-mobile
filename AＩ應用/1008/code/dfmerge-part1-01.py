import pandas as pd

# 題目1：計算 products.csv 中共有多少種商品。
products = pd.read_csv("./data/products.csv")
print(products.head())
print(" Number of unique products: ")
print(products["product_id"].nunique())
##############################################
# 題目2：計算 departments.csv 中共有多少個部門。
departments = pd.read_csv("./data/departments.csv")
print(departments.head())
print(" Number of unique departments: ")
print(departments["department_id"].nunique())
##############################################
# 題目3：計算 aisles.csv 中共有多少個 aisle。
# aisle: 通常指的是商店中的走道或貨架區域。
aisles = pd.read_csv("./data/aisles.csv")
# print(aisles)
print(" Number of unique aisles: ")
print(aisles["aisle_id"].nunique())

################################################
# 題目4：統計 orders.csv 中共有多少筆訂單。
orders = pd.read_csv("./data/orders.csv")
# print(orders)
print(" Number of unique orders: ")
print(orders["order_id"].nunique())

################################################
# 題目5：統計 不同 eval_set 的訂單數量，限制y軸的範圍在0到200000之間，並且加上標題和軸標籤。
# train: 訓練集，test: 測試集，prior: 先前的訂單數據。

import matplotlib.pyplot as plt
orders = pd.read_csv("./data/orders.csv")
# print(orders)
print("Number of orders by eval_set:")
print(orders["eval_set"].value_counts())
df = orders["eval_set"].value_counts() # 計算不同 eval_set 的訂單數量

fig,ax = plt.subplots(figsize=(8, 6))
bars = ax.bar(df.index, df.values)
ax.set_title("Orders by eval_set")
ax.set_xlabel("Eval Set")
ax.set_ylabel("Number of Orders")
ax.set_ylim(0, 2000000)
plt.tight_layout() # 調整子圖參數，使之填充整個圖像區域
plt.show()
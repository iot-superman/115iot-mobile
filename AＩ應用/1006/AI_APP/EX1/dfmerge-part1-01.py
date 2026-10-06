import pandas as pd
from script_paths import data_file

# 題目1：計算 products.csv 中共有多少種商品。
products = pd.read_csv(data_file("products.csv"))
print(" Number of unique products: ")
print(products["product_id"].nunique())
# nunique(): 計算唯一值的數量

################################################
# 題目2：計算 departments.csv 中共有多少個部門。
departments = pd.read_csv(data_file("departments.csv"))
print(" Number of unique departments: ")
print(departments["department_id"].nunique())

################################################
# 題目3：計算 aisles.csv 中共有多少個 aisle。
# aisle: 通常指的是商店中的走道或貨架區域。
aisles = pd.read_csv(data_file("aisles.csv"))
print(" Number of unique aisles: ")
print(aisles["aisle_id"].nunique())

################################################
# 題目4：統計 orders.csv 中共有多少筆訂單。
orders = pd.read_csv(data_file("orders.csv"))
print(" Number of unique orders: ")
print(orders["order_id"].nunique())

################################################
# 題目5：統計 不同 eval_set 的訂單數量，限制y軸的範圍在0到200000之間，並且加上標題和軸標籤。
# train: 訓練集，test: 測試集，prior: 先前的訂單數據。


import matplotlib.pyplot as plt
orders = pd.read_csv(data_file("orders.csv"))
print("Number of orders by eval_set:")
print(orders["eval_set"].value_counts())
# value_counts: 統計每個值出現的次數
# nunique: 計算唯一值的數量
plt.figure(figsize=(8, 6))
plts = orders["eval_set"].value_counts().plot(kind="bar") # kind="bar": 以條形圖的形式繪製
plt.title("Orders by eval_set")
plt.xlabel("Eval Set")
plt.ylabel("Number of Orders")
plt.ylim(0, 1000000)
plt.tight_layout() # 調整子圖參數，使之填充整個圖像區域
plt.show()

################################################

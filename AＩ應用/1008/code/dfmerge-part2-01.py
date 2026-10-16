import pandas as pd
import matplotlib.pyplot as plt
# 第1題： 
# 讀取 CSV
# products = pd.read_csv("./data/products.csv")
# aisles = pd.read_csv("./data/aisles.csv")
# departments = pd.read_csv("./data/departments.csv")

# # # 合併資料表（JOIN）
# # # 合併 aisle
# df = pd.merge(products, aisles, on='aisle_id') # 合併 aisle
# # print(df.head())
# # # 合併 department
# df = pd.merge(df, departments, on='department_id') # 合併 department
# # print(df.head())

# # # 建立最終商品主檔
# product_full = df[[
#     "product_id",
#     "product_name",
#     "aisle",
#     "department"
# ]]
# print(product_full.head())

# # 統計每個 department 商品數量
# dept_count = product_full.groupby("department").size().reset_index(name="product_count")
# print(dept_count)
# # 畫圖：各 department 商品數量長條圖
# fig, ax = plt.subplots(figsize=(10, 6)) # 建立圖表與座標軸物件
# ax.bar(dept_count['department'], dept_count['product_count'])
# ax.set_title("Number of Products by Department")
# ax.set_xlabel("Department")
# ax.set_ylabel("Number of Products")
# plt.xticks(rotation=90)
# plt.tight_layout()
# plt.show()

############################################################
import pandas as pd

order_products = pd.read_csv("./data/order_products__prior.csv")
products = pd.read_csv("./data/products.csv")

# 合併資料表
df = pd.merge(order_products, products, on="product_id")

order_detail = df[[
    "order_id",
    "product_name",
    "add_to_cart_order",
    "reordered"
]]
# print(order_detail.head())
# 找出每筆訂單「第一個加入購物車」的商品
idx = order_detail.groupby("order_id")["add_to_cart_order"].idxmin() # 取得每個訂單中第一個加入購物車的商品的索引值
# print(idx)
# 以order_id為群組，找出每個訂單中 add_to_cart_order 最小的索引值，這代表每個訂單中第一個加入購物車的商品。
first_product = order_detail.loc[idx] # 透過索引值取得每個訂單的第一個商品紀錄，形成一個新的 DataFrame，包含每筆訂單的第一個加入購物車的商品資訊。
print(first_product.head())

# 加入購物車順序分布圖
top_first_products = (
    first_product["product_name"] # 取得每筆訂單的第一個商品名稱
    .value_counts() # 計算每個商品作為第一個加入購物車的次數
    .head(10)
)
print(top_first_products)
# 使用groupby
top_first_products2 = (
    first_product.groupby("product_name").size().reset_index(name="count").sort_values(by="count", ascending=False).head(10)
)
print(top_first_products2[["product_name", "count"]])
######################################################
# 加入購物車順序分布圖
import matplotlib.pyplot as plt
order_counts = order_detail["add_to_cart_order"].value_counts().sort_index()

fig, ax = plt.subplots()
ax.bar(order_counts.index, order_counts.values)

ax.set_xlabel("Add to Cart Order")
ax.set_ylabel("Count")
ax.set_title("Distribution of Add-to-Cart Order")

fig.tight_layout()
plt.show()

#########################################################
# 前 10 名最常第一個加入購物車商品
fig, ax = plt.subplots()
ax.bar(top_first_products.index, top_first_products.values)

ax.set_xticks(top_first_products.index)
ax.set_xticklabels(top_first_products.index, rotation=90)
ax.set_xlabel("Product Name")
ax.set_ylabel("Count")
ax.set_title("Top 10 First Added Products")

fig.tight_layout()
plt.show()
# 第3題
import pandas as pd

# orders = pd.read_csv("./data/orders.csv")
# order_products = pd.read_csv("./data/order_products__prior.csv")

# # 合併資料表（JOIN）
# df = pd.merge(orders, order_products, on="order_id")
# print(df.head())

# # 建立使用者購物紀錄表
# user_order_detail = df[[
#     "user_id",
#     "order_id",
#     "product_id",
#     "order_number",
#     "order_dow",
#     "order_hour_of_day"
# ]]

# print(user_order_detail.head())


# # 統計每位使用者的總購買次數
# user_order_count = (
#     user_order_detail[["user_id", "order_id"]] # 取得使用者與訂單的對應關係，去除重複的訂單記錄
#     .drop_duplicates() # 去除重複的訂單記錄
#     .groupby("user_id") # 以使用者 ID 分組，統計每位使用者的訂單數量
#     .size() # 計算每個使用者的訂單數量
#     .reset_index(name="order_count") # 將統計結果重置索引，並將統計列命名為 "order_count"
# )
# print(user_order_count.head())

# # user_order_count2 = user_order_detail[["user_id", "order_id"]].drop_duplicates().groupby("user_id").size().reset_index(name="order_count")
# # print(user_order_count2.head())

# # 使用者訂單數量分布圖
# import matplotlib.pyplot as plt

# plt.figure()
# plt.hist(user_order_count["order_count"], bins=20)

# plt.xlabel("Order Count per User")
# plt.ylabel("Number of Users")
# plt.title("Distribution of Orders per User")

# plt.tight_layout()
# plt.show()

# # 前 10 名訂單最多的使用者
# top_users = user_order_count.sort_values(by="order_count", ascending=False).head(10)

# plt.figure()
# plt.bar(top_users["user_id"].astype(str), top_users["order_count"])

# plt.xlabel("User ID")
# plt.ylabel("Order Count")
# plt.title("Top 10 Users by Number of Orders")

# plt.tight_layout()
# plt.show()

##################################################################################
# 第4題:
orders = pd.read_csv("./data/orders.csv")
order_products = pd.read_csv("./data/order_products__prior.csv")
products = pd.read_csv("./data/products.csv")

# 合併 orders + order_products
df = pd.merge(orders, order_products, on="order_id")

# 合併 products
df = pd.merge(df, products, on="product_id")

# print(df.head())

product_count = (
    df.groupby("product_name") # 以商品名稱分組，統計每個商品的購買次數
    .size() # 計算每個商品的購買次數
    .reset_index(name="purchase_count") # 將統計結果重置索引，並將統計列命名為 "purchase_count"
)
# print(product_count.head())

top10_products = product_count.sort_values(
    by="purchase_count", ascending=False
).head(10)
print(top10_products)

import matplotlib.pyplot as plt

fig, ax = plt.subplots()
ax.bar(top10_products["product_name"], top10_products["purchase_count"])

ax.set_xticklabels(top10_products["product_name"], rotation=90)
ax.set_xlabel("Product Name")
ax.set_ylabel("Purchase Count")
ax.set_title("Top 10 Best-Selling Products")
plt.tight_layout()
plt.show()
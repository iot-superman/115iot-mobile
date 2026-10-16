# 第3題
import pandas as pd

orders = pd.read_csv("./data/orders.csv")
order_products = pd.read_csv("./data/order_products__prior.csv")

# 合併資料表（JOIN）
df = pd.merge(orders, order_products, on="order_id")

# 建立使用者購物紀錄表
user_order_detail = df[[
    "user_id",
    "order_id",
    "product_id",
    "order_number",
    "order_dow",
    "order_hour_of_day"
]]

print(user_order_detail.head())

# 統計每位使用者的總購買次數
user_order_count = (
    user_order_detail[["user_id", "order_id"]]
    .drop_duplicates()
    .groupby("user_id")
    .size()
    .reset_index(name="order_count")
)
# .drop_duplicates(): 因為一個訂單可能包含多個商品，所以會有重複的 order_id。使用 drop_duplicates() 可以去除這些重複的訂單紀錄，確保每個訂單只被計算一次。
print(user_order_count.head())

# 使用者訂單數量分布圖
import matplotlib.pyplot as plt

plt.figure()
plt.hist(user_order_count["order_count"], bins=20)

plt.xlabel("Order Count per User")
plt.ylabel("Number of Users")
plt.title("Distribution of Orders per User")

plt.tight_layout()
plt.show()

# 前 10 名訂單最多的使用者
top_users = user_order_count.sort_values(by="order_count", ascending=False).head(10)

plt.figure()
plt.bar(top_users["user_id"].astype(str), top_users["order_count"])

plt.xlabel("User ID")
plt.ylabel("Order Count")
plt.title("Top 10 Users by Number of Orders")

plt.tight_layout()
plt.show()

##################################################################################
# 第4題:
import pandas as pd

orders = pd.read_csv("./data/orders.csv")
order_products = pd.read_csv("./data/order_products__prior.csv")
products = pd.read_csv("./data/products.csv")

# 合併 orders + order_products
df = pd.merge(orders, order_products, on="order_id")

# 合併 products
df = pd.merge(df, products, on="product_id")

product_count = (
    df.groupby("product_name")
    .size()
    .reset_index(name="purchase_count")
)

top10_products = product_count.sort_values(
    by="purchase_count", ascending=False
).head(10)

print(top10_products)

import matplotlib.pyplot as plt

plt.figure()
plt.bar(top10_products["product_name"], top10_products["purchase_count"])

plt.xticks(rotation=90)
plt.xlabel("Product Name")
plt.ylabel("Purchase Count")
plt.title("Top 10 Best-Selling Products")

plt.tight_layout()
plt.show()
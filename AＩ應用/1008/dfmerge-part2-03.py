# 第5題:
# import pandas as pd

# order_products = pd.read_csv("./data/order_products__prior.csv")
# products = pd.read_csv("./data/products.csv")

# df = pd.merge(order_products, products, on="product_id")

# reordered_df = df[df["reordered"] == 1]

# reordered_count = (
#     reordered_df.groupby("product_name")
#     .size()
#     .reset_index(name="reordered_count")
# )

# top10_reordered = reordered_count.sort_values(
#     by="reordered_count", ascending=False
# ).head(10)

# print(top10_reordered)

# import matplotlib.pyplot as plt

# plt.figure()
# plt.bar(top10_reordered["product_name"], top10_reordered["reordered_count"])

# plt.xticks(rotation=90)
# plt.xlabel("Product Name")
# plt.ylabel("Reordered Count")
# plt.title("Top 10 Most Reordered Products")

# plt.tight_layout()
# plt.show()

##################################################################################################
# 第6題:

import pandas as pd
import matplotlib.pyplot as plt

# 讀取資料
order_products = pd.read_csv('./data/order_products__prior.csv')
products = pd.read_csv('./data/products.csv')
departments = pd.read_csv('./data/departments.csv')

# 合併資料
df = order_products.merge(products, on='product_id')
df = df.merge(departments, on='department_id')

# 計算各部門銷售次數
dept_sales = df.groupby('department')['order_id'].count().reset_index()
# reset_index(): 將 groupby 的結果轉換為 DataFrame，並將原本的 groupby 索引重置為普通的數字索引。

# 欄位名稱調整
dept_sales.columns = ['department', 'sales_count']

# 依銷售排序（方便畫圖）
dept_sales = dept_sales.sort_values(by='sales_count', ascending=False)

# 顯示結果
print(dept_sales.head())

plt.figure()
plt.bar(dept_sales['department'], dept_sales['sales_count'])

plt.xticks(rotation=90)
plt.xlabel('Department')
plt.ylabel('Sales Count')
plt.title('Sales Count by Department')

plt.tight_layout()
plt.show()

plt.figure()
plt.pie(dept_sales['sales_count'],
        labels=dept_sales['department'],
        autopct='%1.1f%%')

plt.title('Department Sales Distribution')
plt.show()

###########################################################################

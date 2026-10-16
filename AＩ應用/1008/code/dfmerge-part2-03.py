import pandas as pd
import matplotlib.pyplot as plt

# # 讀取資料
# order_products = pd.read_csv('./data/order_products__prior.csv')
# products = pd.read_csv('./data/products.csv')
# departments = pd.read_csv('./data/departments.csv')

# # 合併資料
# df = order_products.merge(products, on='product_id')
# df = df.merge(departments, on='department_id')
# # print(df.head())

# # 計算各部門銷售次數
# dept_sales = df.groupby('department')['order_id'].count().reset_index()
# # reset_index(): 將 groupby 的結果轉換為 DataFrame，並將原本的 groupby 索引重置為普通的數字索引。

# # 寫成多行寫法
# dept_sales = (
#     df.groupby('department')['order_id'] # 以部門分組，統計每個部門的銷售次數
#     .count() # 計算每個部門的銷售次數
#     .reset_index() # 將統計結果重置索引
# ) # 將統計結果重置索引，並將統計列命名為 "order_id"
# # print(dept_sales.head())

# # 欄位名稱調整
# dept_sales.columns = ['department', 'sales_count']

# # 依銷售排序（方便畫圖）
# dept_sales = dept_sales.sort_values(by='sales_count', ascending=False)

# # 顯示結果
# print(dept_sales.head())

# fig, ax = plt.subplots()
# ax.bar(dept_sales['department'], dept_sales['sales_count'])

# ax.set_xticklabels(dept_sales['department'], rotation=90)
# ax.set_xlabel('Department')
# ax.set_ylabel('Sales Count')
# ax.set_title('Sales Count by Department')

# plt.tight_layout()
# plt.show()

# fig, ax = plt.subplots()
# ax.pie(dept_sales['sales_count'],
#        labels=dept_sales['department'],
#        autopct='%1.1f%%')

# ax.set_title('Department Sales Distribution')
# plt.tight_layout()
# plt.show()
###########################################################
# 第7題:
import pandas as pd
import matplotlib.pyplot as plt

# 讀取資料
orders = pd.read_csv('./data/orders.csv')
order_products = pd.read_csv('./data/order_products__prior.csv')

# 合併資料
df = orders.merge(order_products, on='order_id')
# print(df.head())

# ===== 星期數字轉名稱 =====
day_map = {
    0: 'Sun', 1: 'Mon', 2: 'Tue',
    3: 'Wed', 4: 'Thu', 5: 'Fri', 6: 'Sat'
}
df['day'] = df['order_dow'].map(day_map)
# print(df.head())
######################
# 1️⃣ 訂單數量（每個 order_id 算一次）
order_count = df.groupby(['order_dow', 'day'])['order_id'].nunique().reset_index()
# nunique vs value_counts：nunique 是計算不同值的數量，value_counts 是計算每個值出現的次數
order_count.columns = ['order_dow', 'day', 'order_count']
print(order_count.head())

order_count = order_count.sort_values('order_dow') # 依星期排序

# 長條圖：訂單數量
plt.figure()
plt.bar(order_count['day'], order_count['order_count'])

plt.xlabel('Day of Week')
plt.ylabel('Order Count')
plt.title('Orders by Day of Week')

plt.show()
######################
# 2️⃣ 商品數量（每列一個商品）
product_count = df.groupby(['order_dow', 'day'])['product_id'].count().reset_index()
product_count.columns = ['order_dow', 'day', 'product_count']
# 為什麼不用unique？因為每列代表一個商品，所以直接 count 就可以了
product_count = product_count.sort_values('order_dow')
print(product_count.head())

# 折線圖：商品數量
plt.figure()
plt.plot(product_count['day'], product_count['product_count'], marker='o')

plt.xlabel('Day of Week')
plt.ylabel('Product Count')
plt.title('Products Purchased by Day of Week')

plt.show()
# 第9題:
import pandas as pd

order_products = pd.read_csv('./data/order_products__prior.csv')
products = pd.read_csv('./data/products.csv')
aisles = pd.read_csv('./data/aisles.csv')

# 合併 products（拿商品名稱 + aisle_id）
df = pd.merge(order_products, products, on='product_id')

# 合併 aisles（拿 aisle 名稱）
df = pd.merge(df, aisles, on='aisle_id')

product_counts = df.groupby(
    ['aisle', 'product_name']
)['product_id'].count().reset_index()

product_counts.columns = ['aisle', 'product_name', 'purchase_count']

idx = product_counts.groupby('aisle')['purchase_count'].idxmax()
# groupby('aisle')['purchase_count'].idxmax(): 找出每個 aisle 中購買次數最多的商品的索引
top_products = product_counts.loc[idx] # 使用 loc 取出這些索引對應的資料

top_products = top_products.sort_values(by='purchase_count', ascending=False)
# 將結果依購買次數排序，方便查看最熱門商品
print(top_products.head(20))

import matplotlib.pyplot as plt

# 中文字型（避免亂碼）
plt.rcParams['font.sans-serif'] = ['Microsoft JhengHei']
plt.rcParams['axes.unicode_minus'] = False

top10 = top_products.head(10)

plt.figure()
plt.barh(top10['product_name'], top10['purchase_count'])
# barh: 水平長條圖，x 軸為購買次數，y 軸為商品名稱
plt.xlabel('購買次數')
plt.ylabel('商品名稱')
plt.title('各貨架熱門商品（Top 10）')
plt.gca().invert_yaxis()
plt.tight_layout() # 自動調整子圖參數，使之填充整個圖像區域
plt.show()


###################################################################
# 第10題:
import pandas as pd
# 載入資料
orders = pd.read_csv('./data/orders.csv')
order_products = pd.read_csv('./data/order_products__prior.csv')
products = pd.read_csv('./data/products.csv')
aisles = pd.read_csv('./data/aisles.csv')
departments = pd.read_csv('./data/departments.csv')

# 逐步合併
# 1️⃣ 訂單 + 商品購買紀錄
df = pd.merge(order_products, orders, on='order_id')

# 2️⃣ 加入商品資訊
df = pd.merge(df, products, on='product_id')

# 3️⃣ 加入 aisle
df = pd.merge(df, aisles, on='aisle_id')

# 4️⃣ 加入 department
df = pd.merge(df, departments, on='department_id')

# 選取需要欄位
final_df = df[[
    'user_id',
    'order_id',
    'order_number',
    'order_dow',
    'order_hour_of_day',
    'product_name',
    'aisle',
    'department',
    'reordered'
]]
# 查看結果
print(final_df.head())
print(final_df.shape)

# 一天中哪個時段最常下單
hour_counts = final_df.groupby('order_hour_of_day')['order_id'].count()

# 最熱銷商品（Top 10）
top_products = final_df['product_name'].value_counts().head(10)

# 各部門銷售量
dept_sales = final_df['department'].value_counts()

# 再購率分析（Reorder）
reorder_rate = final_df['reordered'].value_counts(normalize=True)
# normalize=True: 計算比例而非數量
# 計算數量
reorder_rate2 = final_df['reordered'].value_counts()
# 不用 normalize=True: 計算數量而非比例


reorder_by_product = final_df.groupby('product_name')['reordered'].mean().sort_values(ascending=False).head(10)
# reorder_by_product: 計算每個商品的再購率，並排序取前10

# 星期幾最常購物
dow_counts = final_df['order_dow'].value_counts().sort_index()

import matplotlib.pyplot as plt

plt.rcParams['font.sans-serif'] = ['Microsoft JhengHei']
plt.rcParams['axes.unicode_minus'] = False

# 下單時段圖
plt.figure()
hour_counts.plot(kind='line', marker='o')

plt.title('下單時段分布')
plt.xlabel('小時')
plt.ylabel('訂單數')
plt.xticks(range(0, 24))
plt.show()

# 熱銷商品圖
plt.figure()
top_products.plot(kind='bar')

plt.title('熱銷商品 Top 10')
plt.xlabel('商品')
plt.ylabel('銷售次數')

plt.xticks(rotation=45)
plt.show()

# 部門銷售圖
plt.figure()
dept_sales.head(10).plot(kind='bar')

plt.title('部門銷售分布')
plt.xlabel('部門')
plt.ylabel('銷售量')

plt.xticks(rotation=45)
plt.show()

# 再購商品圖
plt.figure()
reorder_rate.plot(kind='bar')

plt.title('再購比例')
plt.xlabel('是否再購')
plt.ylabel('比例')

plt.show()

# 再購商品圖換成圓餅圖
plt.figure()
reorder_rate.plot(kind='pie', autopct='%1.1f%%', startangle=90)
plt.title('再購比例')
plt.ylabel('')  # 不顯示 y 軸標籤
# 標題0顯示不再購，1顯示再購
plt.legend(labels=['不再購', '再購'], loc='best')
plt.show()

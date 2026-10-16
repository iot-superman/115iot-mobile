# 第7題:
import pandas as pd
import matplotlib.pyplot as plt

# 讀取資料
orders = pd.read_csv('./data/orders.csv')
order_products = pd.read_csv('./data/order_products__prior.csv')

# 合併資料
df = orders.merge(order_products, on='order_id')

# ===== 星期數字轉名稱 =====
day_map = {
    0: 'Sun', 1: 'Mon', 2: 'Tue',
    3: 'Wed', 4: 'Thu', 5: 'Fri', 6: 'Sat'
}

df['day'] = df['order_dow'].map(day_map)

# ===== 統計 =====

# 1️⃣ 訂單數量（每個 order_id 算一次）
order_count = df.groupby(['order_dow', 'day'])['order_id'].nunique().reset_index()
# nunique vs value_counts：nunique 是計算不同值的數量，value_counts 是計算每個值出現的次數
order_count.columns = ['order_dow', 'day', 'order_count']

# 2️⃣ 商品數量（每列一個商品）
product_count = df.groupby(['order_dow', 'day'])['product_id'].count().reset_index()
product_count.columns = ['order_dow', 'day', 'product_count']
# 為什麼不用unique？因為每列代表一個商品，所以直接 count 就可以了

# ===== 排序（確保星期順序正確）=====
order_count = order_count.sort_values('order_dow')
product_count = product_count.sort_values('order_dow')

# ===== 畫圖 =====

# 長條圖：訂單數量
plt.figure()
plt.bar(order_count['day'], order_count['order_count'])

plt.xlabel('Day of Week')
plt.ylabel('Order Count')
plt.title('Orders by Day of Week')

plt.show()


# 折線圖：商品數量
plt.figure()
plt.plot(product_count['day'], product_count['product_count'], marker='o')

plt.xlabel('Day of Week')
plt.ylabel('Product Count')
plt.title('Products Purchased by Day of Week')

plt.show()

#########################################################################
# # 第8題:
import pandas as pd
import matplotlib.pyplot as plt

# 載入資料
orders = pd.read_csv('./data/orders.csv')
order_products = pd.read_csv('./data/order_products__prior.csv')

# 統計「每個時段的訂單數量」
order_counts = orders.groupby('order_hour_of_day')['order_id'].count().reset_index()
order_counts.columns = ['hour', 'order_count']

# 合併資料（計算商品總量）
# 合併 orders 和 order_products
merged = pd.merge(order_products, orders, on='order_id')

# 每個時段商品數（每列代表一個商品）
product_counts = merged.groupby('order_hour_of_day')['product_id'].count().reset_index()
product_counts.columns = ['hour', 'product_count']

# 合併兩個結果（方便畫圖）
final_df = pd.merge(order_counts, product_counts, on='hour')

# matplotlib 常見問題（預設字型不支援中文），你只要加上「字型設定」就可以了
plt.rcParams['font.sans-serif'] = ['Microsoft JhengHei']  # 微軟正黑體
plt.rcParams['axes.unicode_minus'] = False  # 避免負號變方塊
# 折線圖
plt.figure()

plt.plot(final_df['hour'], final_df['order_count'], marker='o', label='訂單數')
plt.plot(final_df['hour'], final_df['product_count'], marker='s', label='商品數')
plt.xticks(final_df['hour'])  # 設定 x 軸刻度為每個小時
plt.xlabel('小時 (0-23)')
plt.ylabel('數量')
plt.title('一天中下單時段分布')
plt.legend()
plt.show()

# 長條圖
plt.figure()

plt.bar(final_df['hour'], final_df['order_count'])
plt.xticks(final_df['hour'])  # 設定 x 軸刻度為每個小時
plt.xlabel('小時 (0-23)')
plt.ylabel('訂單數')
plt.title('下單時段分布（訂單數）')
plt.show()
######################################################################

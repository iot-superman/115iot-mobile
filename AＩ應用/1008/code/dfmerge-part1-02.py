import pandas as pd
import matplotlib.pyplot as plt

# # 題目6：分析 order_hour_of_day 
# # 一天中哪個時間點訂單最多、繪製長條圖、X軸0~23小時、Y軸訂單數，統計每個小時出現的次數
# orders = pd.read_csv("./data/orders.csv")
# hour_counts = orders['order_hour_of_day'].value_counts().sort_index()
# # print(hour_counts)
# hour_counts = hour_counts.reindex(range(24), fill_value=0) # 將缺失的小時補上，並填充為0
# print(hour_counts)
# # 找出最多訂單時間
# max_hour = hour_counts.idxmax() # idxmax(): 返回最大值的索引
# max_value = hour_counts.max() # max(): 返回最大值
# print(f"訂單最多的時間：{max_hour} 點，共 {max_value} 筆")
# # 畫長條圖
# fig, ax = plt.subplots(figsize=(10, 6))
# bars = ax.bar(hour_counts.index, hour_counts.values)
# ax.set_title("Orders by Hour of Day")
# ax.set_xlabel("Hour of Day")
# ax.set_ylabel("Number of Orders")
# ax.set_xticks(range(24))
# plt.tight_layout()
# plt.show()
##########################################################
# 題目7：分析order_dow
# 一週中哪一天訂單最多、繪製長條圖、X軸：0~6 (星期)、Y軸：訂單數。
# orders = pd.read_csv('./data/orders.csv')
# dow_counts = orders['order_dow'].value_counts().sort_index() # 統計每一天的訂單數
# dow_counts = dow_counts.reindex(range(7), fill_value=0) # 補齊 0~6（避免缺值）
# print(dow_counts)
# # 找出最多訂單的星期
# max_day = dow_counts.idxmax()
# max_value = dow_counts.max()

# print(f"訂單最多的是星期 {max_day}，共 {max_value} 筆")

# # 畫長條圖
# fig, ax = plt.subplots(figsize=(10, 6))
# bars = ax.bar(dow_counts.index, dow_counts.values)
# ax.set_title("Orders by Day of Week")
# ax.set_xlabel("Day of Week")
# ax.set_ylabel("Number of Orders")
# ax.set_xticks(range(7))
# plt.tight_layout()
# plt.show()  

# # 可以把數字轉成「星期名稱」
# day_labels = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
# fig, ax = plt.subplots(figsize=(10, 6))
# bars = ax.bar(day_labels, dow_counts.values)
# ax.set_title("Orders by Day of Week")
# ax.set_xlabel("Day of Week")
# ax.set_ylabel("Number of Orders")
# ax.set_xticks(range(7))
# plt.tight_layout()
# plt.show() 
###########################################################################
# 題目8：分析 days_since_prior_order 
# 顧客通常隔幾天購買一次、繪製：Histogram (直方圖)、X軸：days_since_prior_order Y軸：次數
# orders = pd.read_csv('./data/orders.csv')
# # print(orders)
# days = orders['days_since_prior_order'].dropna() #第一筆訂單會是 NaN（沒有前一次訂單） 
# print(days)
# # 畫 Histogram
# fig, ax = plt.subplots(figsize=(10, 6))
# ax.hist(days, bins=30, edgecolor='black')
# ax.set_title("Histogram of Days Since Prior Order")
# ax.set_xlabel("Days Since Prior Order")
# ax.set_ylabel("Number of Orders")
# plt.tight_layout()
# plt.show()

# # 找出「最常見購買間隔」
# most_common = days.mode()[0]
# # print(most_common)
# # mode(): 返回眾數，這裡是找出最常見的購買間隔
# print(f"顧客最常在 {most_common} 天後再次購買")
######################################################

# order_products = pd.read_csv('./data/order_products__prior.csv')
# reorder_counts = order_products['reordered'].value_counts().sort_index() # 統計回購 vs 非回
# print(reorder_counts)
# # 畫圓餅圖
# labels = ['First Purchase (0)', 'Reordered (1)']
# fig, ax = plt.subplots(figsize=(8, 8))
# ax.pie(reorder_counts.values, labels=labels, autopct='%1.1f%%', startangle=90)
# ax.set_title("Reorder vs First Purchase")
# plt.tight_layout()
# plt.show()
import pandas as pd
import matplotlib.pyplot as plt
from script_paths import data_file, image_file

df = pd.read_csv(data_file("student_scores_100.csv"))
print(df.head())
print(df.info())

# 直方圖：看全班成績分布（Histogram）
fig, ax = plt.subplots()
# ax.hist(df["score"], bins=10)
#　ax.hist(df["score"], bins=10)說明
#　ax.hist() 是 Matplotlib 中用於繪製直方圖的函數。
# 它接受一個數據序列（在這裡是 df["score"]）和一個參數 bins，該參數指定了將數據分成多少個區間（箱子）。
# 在這個例子中，bins=10 表示將成績分成 10 個區間，例如 0-10、10-20、20-30 等等。
# 這樣可以幫助我們了解成績的分布情況，例如有多少學生的成績在 0-10 之間，有多少學生的成績在 10-20 之間，以此類推。
ax.hist(df["score"], bins=[50, 60, 70, 80, 90, 100], edgecolor="black", color="skyblue") # 設定直方圖的邊框顏色為黑色，填充顏色為天藍色

# 設定x軸顯示範圍，每10分一個刻度
ax.set_xlim(0, 100) # 設定x軸的範圍從0到100
ax.set_xticks(range(0, 101, 10)) # 設定x軸的刻度，從0到100，每10分一個刻度

ax.set_title("Score Distribution (All Students)")
ax.set_xlabel("Score")
ax.set_ylabel("Number of Students")

ax.grid(True, axis="y", alpha=0.3) # 在y軸上添加網格線，alpha參數控制網格線的透明度
plt.tight_layout()
# 存檔
plt.savefig(image_file("histogram.png"), dpi=300)
# 顯示
plt.show()


# bins 表示 把資料分成幾個區間 (箱子)。
# bins = 區間數量
# 0 ~ 100
# 0-10
# 10-20
# 20-30
# 30-40
# 40-50
# 50-60
# 60-70
# 70-80
# 80-90
# 90-100
import pandas as pd
import matplotlib.pyplot as plt
from script_paths import data_file, image_file

df = pd.read_csv(data_file("student_scores_100.csv"))
print(df.head())
print(df.info())

# 各班平均分數長條圖（Bar chart）
mean_by_class = df.groupby("class")["score"].mean().sort_index()
# sort_index(): 將索引排序，確保班級順序正確

# print(mean_by_class)
print(mean_by_class.index)
print(mean_by_class.values)

# 建立圖表畫布，建立一個圖表（figure）與一個座標軸（axes）
# 之後所有圖形都畫在 ax 上
fig, ax = plt.subplots()
bars = ax.bar(mean_by_class.index, mean_by_class.values)
ax.set_title("Average Score by Class")
ax.set_xlabel("Class")
ax.set_ylabel("Average Score")
ax.bar_label(bars, fmt="%.1f", padding=3) # 在每個長條上顯示數值

# 在每個長條上顯示數值
# for b in bars:
#     ax.text(b.get_x() + b.get_width()/2, b.get_height(),
#             f"{b.get_height():.1f}", ha="center", va="bottom")

# # 固定 y 軸範圍
ax.set_ylim(0, 100)
plt.tight_layout()
plt.savefig(image_file("bar_chart.png"), dpi=300)
plt.show()


####################################################################################
# 各班人數長條圖（Bar chart）
count_by_class = df["class"].value_counts().sort_index()

fig, ax = plt.subplots()
bars = ax.bar(count_by_class.index, count_by_class.values)
ax.set_title("Number of Students by Class")
ax.set_xlabel("Class")
ax.set_ylabel("Number of Students")
ax.bar_label(bars, fmt="%d", padding=3) # 在每個長條上顯示數值

# # 在每個長條上顯示人數
# for b in bars:
#     ax.text(
#         b.get_x() + b.get_width() / 2,
#         b.get_height(),
#         f"{int(b.get_height())}",
#         ha="center",
#         va="bottom"
#     )

plt.tight_layout()
plt.savefig(image_file("bar_chart_count.png"), dpi=300)
plt.show()
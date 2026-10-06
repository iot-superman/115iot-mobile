import pandas as pd
import matplotlib.pyplot as plt
from script_paths import data_file, image_file

df = pd.read_csv(data_file("student_scores_100.csv"))
print(df.head())
print(df.info())

# 圓餅圖：各班學生人數比例（Pie chart）

count_by_class = df["class"].value_counts().sort_index()

fig, ax = plt.subplots()
ax.pie(
    count_by_class.values,
    labels=count_by_class.index,
    autopct="%1.1f%%",   # 顯示百分比
    startangle=90        # 從正上方開始
)
#  startangle=90: 設定圓餅圖的起始角度，90度表示從正上方開始繪製，這樣可以讓圖表看起來更平衡和美觀
# autopct="%1.1f%%": 在每個扇形上顯示百分比，%1.1f%% 表示顯示一位小數的百分比，%% 是用來顯示字面上的百分號
ax.set_title("Student Distribution by Class")
ax.axis("equal")  # 讓圓餅圖變成正圓
plt.tight_layout()
plt.savefig(image_file("pie_by_class.png"), dpi=300)
plt.show()
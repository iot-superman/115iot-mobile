import pandas as pd
import matplotlib.pyplot as plt
# pip install matplotlib

df = pd.read_csv("./data/student_scores_100.csv")  # 或 "student_scores_100.csv"
print(df.head())
print(df.info())

# 折線圖
fig, ax = plt.subplots()

# fig用途:用來設定整個圖表的大小、解析度等屬性
fig.set_size_inches(6, 4)  # 設定圖表大小（寬度、高度）
fig.set_dpi(100)  # 設定圖表解析度（每英寸點數）
fig.set_facecolor("lightgray")  # 設定圖表背景顏色

# fig:圖表物件，ax:座標軸物件
# ax:用來設定圖表的標題、軸標籤、繪製圖形等操作
ax.set_title("Hello Matplotlib")
ax.set_xlabel("x")
ax.set_ylabel("y")
ax.plot([1,2,3], [2,4,3])
# 設定x軸刻度
ax.set_xticks([1,2,3])
# 設定x軸刻度，使用range(1,4)表示從1到3的整數
ax.set_xticks(range(1,4))

# 設定y軸刻度
ax.set_yticks([1,2,3,4])
plt.tight_layout()
# 存檔
plt.savefig("./image/line_chart.png", dpi=300)
# 顯示
plt.show()

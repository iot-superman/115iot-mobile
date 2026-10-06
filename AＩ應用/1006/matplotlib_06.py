import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("./data/student_scores_100.csv")  # 或 "student_scores_100.csv"
print(df.head())
print(df.info())

# 散點圖：用 student_id 當 x（Scatter）
fig, ax = plt.subplots()

colors = {"A": "red", "B": "green", "C": "blue"}
for c in sorted(df["class"].unique()):
    # print(c) #班級名稱，Ａ班、Ｂ班、Ｃ班
    sub = df[df["class"] == c]
    # print(sub) #每個班級的資料
    # print("...........")
    ax.scatter(sub["student_id"], sub["score"], label=f"Class {c}", alpha=0.7, color=colors[c]) # alpha:控制點的透明度，color:設定不同班級的顏色

ax.set_title("Scores by Student ID")
ax.set_xlabel("student_id")
ax.set_ylabel("score")
ax.set_ylim(0, 100)
ax.legend()
ax.grid(True, alpha=0.3)
plt.savefig("./image/scatter_by_student_id.png", dpi=300)
plt.show()
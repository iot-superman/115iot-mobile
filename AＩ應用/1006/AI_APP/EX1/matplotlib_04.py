import pandas as pd
import matplotlib.pyplot as plt
from script_paths import data_file, image_file

df = pd.read_csv(data_file("student_scores_100.csv"))
print(df.head())
print(df.info())

# 個人成績呈現：排序後長條圖（Top/Bottom）
top10 = df.sort_values("score", ascending=False).head(10)

fig, ax = plt.subplots(figsize=(8,4))
# figsize=(8,4): 設定圖表的寬度為8英吋，高度為4英吋，這樣可以讓圖表更寬一些，適合顯示學生姓名較長的情況
ax.bar(top10["student"], top10["score"])
ax.set_title("Top 10 Students")
ax.set_xlabel("Student")
ax.set_ylabel("Score")
ax.set_ylim(0, 100)
plt.xticks(rotation=45, ha="right")
plt.tight_layout()
plt.savefig(image_file("top10_students.png"), dpi=300)
plt.show()
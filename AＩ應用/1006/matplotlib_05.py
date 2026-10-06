import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("./data/student_scores_100.csv")  # 或 "student_scores_100.csv"
print(df.head())
print(df.info())

classes = sorted(df["class"].unique()) 
# unique(): 取得 class 欄位中所有不同的值（班級），並返回一個陣列,去除重複值

# 依照不同的 class 分組，取得每個 class 的 score 資料，並存放到一個串列中
data = [df.loc[df["class"] == c, "score"] for c in classes]
print(data)


# data = []
# for c in classes:
#     scores = df.loc[df["class"] == c, "score"]
#     data.append(scores)

fig, ax = plt.subplots()
ax.boxplot(data, tick_labels=classes)
ax.set_title("Score Spread by Class (Boxplot)")
ax.set_xlabel("Class")
ax.set_ylabel("Score")
ax.set_ylim(0, 100)
ax.grid(True, axis="y", alpha=0.3)
plt.tight_layout()
plt.savefig("./image/boxplot_by_class.png", dpi=300)
plt.show()
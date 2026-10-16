import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import load_iris
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA

# === 1. 載入資料 ===
iris = load_iris()
X = iris.data          # (150, 4) 四個特徵
y = iris.target        # 三種花的類別
feature_names = iris.feature_names

print("原始資料 shape:", X.shape)
print("特徵名稱:", feature_names)
print(y)

##############################################
# === 組成 DataFrame ===
import pandas as pd
df = pd.DataFrame(X, columns=feature_names)
df['target'] = y
df['species'] = [iris.target_names[i] for i in y]   # 加上花名 (setosa/versicolor/virginica)

# === 匯出 csv ===
# df.to_csv('./output/iris_dataset.csv', index=False)
# print("已匯出：iris_dataset.csv")

# === 2. 資料標準化 (Standardization) ===
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)
print("標準化後的資料 shape:", X_scaled.shape)
print(X_scaled)

# === 3. 使用 PCA 函式降維 ===
pca = PCA(n_components=2)
X_pca = pca.fit_transform(X_scaled)
print("PCA 降維後的資料 shape:", X_pca.shape)
print(X_pca)    

# === 4. 檢視結果 ===
print("\n共變異數矩陣:\n", np.cov(X_scaled.T))
print("\n特徵值 (explained_variance_):", pca.explained_variance_)
print("特徵向量 (components_):\n", pca.components_)
print("\n每個主成分解釋的變異比例:", pca.explained_variance_ratio_)
print("前2個主成分累積解釋:", np.sum(pca.explained_variance_ratio_))

# === 5. 視覺化結果 ===
plt.figure(figsize=(8, 6))
colors = ['red', 'green', 'blue']


print(np.unique(y)) # 查看資料中有哪幾個類別[0 1 2]
print(X_pca)

# zip:功能是將多個可迭代物件打包成一個元組的迭代器，這裡用於同時取得類別與顏色
for target, color in zip(np.unique(y), colors):
    print(f"target: {target}") #0, 1, 2
    print(f"color: {color}") # red, green, blue
    # 將每個類別的資料點繪製在散佈圖上，先利用y == target，再將座標取出，最後用不同顏色標示
    plt.scatter(X_pca[y == target, 0], X_pca[y == target, 1],
                c=color, label=iris.target_names[target], s=50)

plt.xlabel(f'PC1 ({pca.explained_variance_ratio_[0]*100:.2f}%)')
plt.ylabel(f'PC2 ({pca.explained_variance_ratio_[1]*100:.2f}%)')
plt.title('PCA of Iris Dataset (using sklearn PCA)')
plt.legend()
plt.grid(True, alpha=0.3)
plt.show()
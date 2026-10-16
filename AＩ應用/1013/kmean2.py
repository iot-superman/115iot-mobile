import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.datasets import load_iris
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA
from sklearn.cluster import KMeans
from sklearn.metrics import confusion_matrix, adjusted_rand_score, silhouette_score

# === 1. 載入資料 ===
iris = load_iris()
X = iris.data
y = iris.target
feature_names = iris.feature_names

print("原始資料 shape:", X.shape)

##############################################
df = pd.DataFrame(X, columns=feature_names)
df['target'] = y
df['species'] = [iris.target_names[i] for i in y]

import os
os.makedirs('./output', exist_ok=True)
df.to_csv('./output/iris_dataset.csv', index=False)
print("已匯出：iris_dataset.csv")
##############################################

# === 2. 資料標準化 ===
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# === 3. PCA 降維 ===
pca = PCA(n_components=2)
X_pca = pca.fit_transform(X_scaled)

print("\n每個主成分解釋的變異比例:", pca.explained_variance_ratio_)
print("前2個主成分累積解釋:", np.sum(pca.explained_variance_ratio_))

# === 4. KMeans 分群 ===
kmeans = KMeans(n_clusters=3, n_init=10, random_state=42)
y_kmeans = kmeans.fit_predict(X_pca)
centers_pca = kmeans.cluster_centers_

# === 5. 評估分群結果 ===
print("\n=== KMeans 分群結果（PCA 後）===")
print("Confusion Matrix:\n", confusion_matrix(y, y_kmeans))
print("Inertia:", kmeans.inertia_)
print("Silhouette Score (k=3):", silhouette_score(X_pca, y_kmeans))

# === 6. 視覺化 ===
fig, axes = plt.subplots(1, 2, figsize=(14, 6))
colors = ['red', 'green', 'blue']

for target, color in zip(np.unique(y), colors):
    axes[0].scatter(X_pca[y == target, 0], X_pca[y == target, 1],
                    c=color, label=iris.target_names[target], s=50, alpha=0.7)
axes[0].set_xlabel(f'PC1 ({pca.explained_variance_ratio_[0]*100:.2f}%)')
axes[0].set_ylabel(f'PC2 ({pca.explained_variance_ratio_[1]*100:.2f}%)')
axes[0].set_title('True Labels (Iris Species)')
axes[0].legend()
axes[0].grid(True, alpha=0.3)

for cluster, color in zip(np.unique(y_kmeans), colors):
    axes[1].scatter(X_pca[y_kmeans == cluster, 0], X_pca[y_kmeans == cluster, 1],
                    c=color, label=f'Cluster {cluster}', s=50, alpha=0.7)
axes[1].scatter(centers_pca[:, 0], centers_pca[:, 1],
                c='black', marker='X', s=200, label='Centroids')
axes[1].set_xlabel(f'PC1 ({pca.explained_variance_ratio_[0]*100:.2f}%)')
axes[1].set_ylabel(f'PC2 ({pca.explained_variance_ratio_[1]*100:.2f}%)')
axes[1].set_title('KMeans Clustering on PCA (k=3)')
axes[1].legend()
axes[1].grid(True, alpha=0.3)

plt.tight_layout()
plt.show()

# === 7. Elbow Method + Silhouette Coefficient 選最佳 K ===
inertias = []
silhouette_scores = []
K_range = range(2, 8)   # Silhouette 需要至少 2 群，所以從 2 開始

for k in K_range:
    km = KMeans(n_clusters=k, n_init=10, random_state=42)
    labels = km.fit_predict(X_pca)
    inertias.append(km.inertia_)
    sil = silhouette_score(X_pca, labels)
    silhouette_scores.append(sil)
    print(f"K={k} | Inertia={km.inertia_:.2f} | Silhouette={sil:.4f}")

# 找出 Silhouette 最高的 K
best_k = K_range[np.argmax(silhouette_scores)]
print(f"\n>>> 依 Silhouette Coefficient，最佳 K = {best_k} "
      f"(score = {max(silhouette_scores):.4f})")

# === 視覺化：Elbow 與 Silhouette 並排比較 ===
fig, axes = plt.subplots(1, 2, figsize=(14, 5))

# (a) Elbow
axes[0].plot(list(K_range), inertias, 'o-', color='steelblue')
axes[0].set_xlabel('K (number of clusters)')
axes[0].set_ylabel('Inertia (within-cluster SSE)')
axes[0].set_title('Elbow Method')
axes[0].grid(True, alpha=0.3)

# (b) Silhouette
axes[1].plot(list(K_range), silhouette_scores, 'o-', color='darkorange')
axes[1].axvline(best_k, color='red', linestyle='--', alpha=0.7,
                label=f'Best K = {best_k}')
axes[1].set_xlabel('K (number of clusters)')
axes[1].set_ylabel('Silhouette Coefficient')
axes[1].set_title('Silhouette Method for Optimal K')
axes[1].legend()
axes[1].grid(True, alpha=0.3)

plt.tight_layout()
plt.show()
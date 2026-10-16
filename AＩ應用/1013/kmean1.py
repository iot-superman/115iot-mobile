from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
import numpy as np

X = np.array([
    [160, 55],
    [165, 60],
    [170, 65],
    [175, 70],
    [180, 75]
], dtype=float)

# n_clusters: 指定要分成幾個群組
# random_state: 設定隨機種子，確保每次執行結果一致
# n_init: 指定 KMeans 演算法初始化的次數，取最佳結果

kmeans = KMeans(
    n_clusters=2,
    random_state=0,
    n_init=10
)

kmeans.fit(X)

print("群組:")
print(kmeans.labels_)

print("中心點:")
print(kmeans.cluster_centers_)

labels = kmeans.labels_
centers = kmeans.cluster_centers_

# 繪製圖形
plt.figure(figsize=(8, 6))

# 依群組分別畫點
colors = ['red', 'blue']
for i in range(2):
    cluster_points = X[labels == i]
    plt.scatter(
        cluster_points[:, 0],
        cluster_points[:, 1],
        c=colors[i],
        s=100,
        label=f'Cluster {i}'
    )

# 畫中心點
plt.scatter(
    centers[:, 0],
    centers[:, 1],
    c='black',
    marker='x',
    s=200,
    linewidths=3,
    label='Centroids'
)

plt.xlabel('Height (cm)')
plt.ylabel('Weight (kg)')
plt.title('KMeans Clustering Result')
plt.legend()
plt.grid(True)
plt.show()
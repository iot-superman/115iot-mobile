import numpy as np
import matplotlib.pyplot as plt

# ============================================
# 1. 建立原始資料矩陣 X
# ============================================
X = np.array([
    [160, 55],
    [165, 60],
    [170, 65],
    [175, 70],
    [180, 75]
], dtype=float)

print("=" * 50)
print("步驟 1：原始資料矩陣 X")
print("=" * 50)
print(X)

# ============================================
# 2. 計算每個特徵的平均值
# ============================================
mean_X = np.mean(X, axis=0)
print("\n" + "=" * 50)
print("步驟 2：每個特徵的平均值")
print("=" * 50)
print(f"身高平均：{mean_X[0]}")
print(f"體重平均：{mean_X[1]}")

# ============================================
# 3. 對資料進行中心化（Centering）
# ============================================
X_centered = X - mean_X
print("\n" + "=" * 50)
print("步驟 3：中心化後的資料")
print("=" * 50)
print(X_centered)

# ============================================
# 4. 計算協方差矩陣
# ============================================
# rowvar=False 表示每一「行(column)」是一個特徵
cov_matrix = np.cov(X_centered, rowvar=False)
print("\n" + "=" * 50)
print("步驟 4：協方差矩陣")
print("=" * 50)
print(cov_matrix)

# ============================================
# 5. 計算協方差矩陣的特徵值與特徵向量
# ============================================
eigenvalues, eigenvectors = np.linalg.eig(cov_matrix)
print("\n" + "=" * 50)
print("步驟 5：特徵值與特徵向量")
print("=" * 50)
print(f"特徵值：{eigenvalues}")
print(f"特徵向量：\n{eigenvectors}")

# ============================================
# 6. 將特徵值由大到小排序
# ============================================
sorted_idx = np.argsort(eigenvalues)[::-1]
eigenvalues_sorted = eigenvalues[sorted_idx]
eigenvectors_sorted = eigenvectors[:, sorted_idx]
print("\n" + "=" * 50)
print("步驟 6：排序後的特徵值與特徵向量")
print("=" * 50)
print(f"排序後特徵值：{eigenvalues_sorted}")
print(f"排序後特徵向量：\n{eigenvectors_sorted}")

# ============================================
# 7. 選擇最大特徵值所對應的特徵向量（第一主成分）
# ============================================
pc1 = eigenvectors_sorted[:, 0]
print("\n" + "=" * 50)
print("步驟 7：第一主成分 (PC1)")
print("=" * 50)
print(pc1)

# ============================================
# 8. 將原始資料投影到第一主成分
# ============================================
X_projected = X_centered @ pc1
print("\n" + "=" * 50)
print("步驟 8：投影到第一主成分後的資料")
print("=" * 50)
print(X_projected)

# ============================================
# 9. 計算每個主成分的解釋變異量比例
# ============================================
explained_variance_ratio = eigenvalues_sorted / np.sum(eigenvalues_sorted)
print("\n" + "=" * 50)
print("步驟 9：解釋變異量比例")
print("=" * 50)
for i, ratio in enumerate(explained_variance_ratio):
    print(f"PC{i+1} 解釋變異量比例：{ratio:.4%}")

# ============================================
# 10. 使用 Matplotlib 繪圖
# ============================================
# 支援中文顯示（若無中文字型可註解掉）
plt.rcParams['font.sans-serif'] = ['Microsoft JhengHei', 'Arial Unicode MS', 'SimHei', 'DejaVu Sans']
plt.rcParams['axes.unicode_minus'] = False

fig, axes = plt.subplots(1, 2, figsize=(14, 6))

# ---- 左圖：原始資料 ----
ax1 = axes[0]
ax1.scatter(X[:, 0], X[:, 1], c='blue', s=100, zorder=3)
for i, (h, w) in enumerate(X):
    ax1.annotate(f'P{i+1}', (h, w), textcoords="offset points",
                 xytext=(8, 8), ha='left', fontsize=10)

ax1.set_xlabel('Height (cm)')
ax1.set_ylabel('Weight (kg)')
ax1.set_title('Original Data')
ax1.grid(True, alpha=0.3)

# ---- 右圖：投影到 PC1 的一維結果 ----
ax2 = axes[1]
ax2.scatter(X_projected, np.zeros_like(X_projected),
            c='red', s=100, zorder=3)
for i, val in enumerate(X_projected):
    ax2.annotate(f'P{i+1}', (val, 0), textcoords="offset points",
                 xytext=(0, 12), ha='center', fontsize=10)

ax2.axhline(y=0, color='gray', linestyle='--', alpha=0.5)
ax2.set_xlabel('Projection on PC1')
ax2.set_yticks([])
ax2.set_title(f'Projection to PC1 (Explained Variance: {explained_variance_ratio[0]:.2%})')
ax2.grid(True, alpha=0.3)

plt.tight_layout()
plt.show()
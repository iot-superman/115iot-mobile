import numpy as np
import matplotlib.pyplot as plt
from sklearn.decomposition import PCA

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
#    （PCA 內部會自動做中心化，這裡列出來對照）
# ============================================
mean_X = np.mean(X, axis=0)
print("\n" + "=" * 50)
print("步驟 2：每個特徵的平均值")
print("=" * 50)
print(f"身高平均：{mean_X[0]}")
print(f"體重平均：{mean_X[1]}")

# ============================================
# 3. 建立 PCA 模型並擬合資料
#    （sklearn 的 PCA 會自動進行中心化 Centering）
# ============================================
pca = PCA(n_components=2)  # 先取全部主成分方便觀察
pca.fit(X)

print("\n" + "=" * 50)
print("步驟 3：PCA 內部中心化後的資料 (X - mean)")
print("=" * 50)
print(X - pca.mean_)

# ============================================
# 4 & 5. 取得 PCA 計算後的特徵值與特徵向量
#        - explained_variance_    ≈ 特徵值
#        - components_            ≈ 特徵向量（每列為一個主成分方向）
# ============================================
print("\n" + "=" * 50)
print("步驟 4 & 5：特徵值 & 特徵向量（由 PCA 自動計算）")
print("=" * 50)
print(f"特徵值 (explained_variance_)：{pca.explained_variance_}")
print(f"特徵向量 (components_)：\n{pca.components_}")

# ============================================
# 6. sklearn 的 PCA 已自動由大到小排序，無需額外處理
# ============================================
print("\n" + "=" * 50)
print("步驟 6：sklearn 已自動將主成分依特徵值大小排序")
print("=" * 50)

# ============================================
# 7. 選擇第一主成分 (PC1)
# ============================================
pc1 = pca.components_[0]
print("\n" + "=" * 50)
print("步驟 7：第一主成分 (PC1)")
print("=" * 50)
print(pc1)

# ============================================
# 8. 將原始資料投影到第一主成分
#    使用 PCA(n_components=1) 直接降到 1 維
# ============================================
pca_1d = PCA(n_components=1)
X_projected = pca_1d.fit_transform(X).flatten() # 將資料投影到第一主成分並展平成一維陣列

print("\n" + "=" * 50)
print("步驟 8：投影到第一主成分後的資料")
print("=" * 50)
print(X_projected)

# ============================================
# 9. 每個主成分的解釋變異量比例
# ============================================
explained_variance_ratio = pca.explained_variance_ratio_
print("\n" + "=" * 50)
print("步驟 9：解釋變異量比例")
print("=" * 50)
# enumerate:同時取得索引和值
for i, ratio in enumerate(explained_variance_ratio):
    print(f"PC{i+1} 解釋變異量比例：{ratio:.4%}")

# ============================================
# 10. 使用 Matplotlib 繪圖
# ============================================
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
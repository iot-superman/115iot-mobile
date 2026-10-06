# -*- coding: utf-8 -*-
"""
EX2-1: 繪製收盤價走勢圖
功能: 從FinMind API獲取股票數據，繪製收盤價走勢圖
股票代碼: 2330 (台積電)
時間範圍: 2026-01-01 到 2026-09-30
"""

import sys
import io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

import requests
import pandas as pd
import matplotlib.pyplot as plt

# ==========================================
# 設定股票代碼和日期範圍
# ==========================================
stock_id = "2330"
start_date = "2026-01-01"
end_date = "2026-09-30"
url = "https://api.finmindtrade.com/api/v4/data"

# 準備API參數
params = {
    "dataset": "TaiwanStockPrice",
    "data_id": stock_id,
    "start_date": start_date,
    "end_date": end_date
}

# ==========================================
# 獲取API數據
# ==========================================
print(f"[*] 正在獲取 {stock_id} 從 {start_date} 到 {end_date} 的股票數據...")
response = requests.get(url, params=params)

if response.status_code != 200:
    print(f"[ERROR] 獲取數據失敗，HTTP狀態碼: {response.status_code}")
    sys.exit(1)

# ==========================================
# 數據處理
# ==========================================
print("[*] 數據處理中...")
data = response.json()
df = pd.DataFrame(data["data"])

# 轉換日期格式並排序
df['date'] = pd.to_datetime(df['date'])
df = df.sort_values('date').reset_index(drop=True)

print(f"[OK] 成功獲取 {len(df)} 筆資料")

# ==========================================
# 繪製收盤價走勢圖
# ==========================================
print("[*] 繪製收盤價走勢圖...")

plt.figure(figsize=(12, 6))
plt.plot(df['date'], df['close'], marker='o', linewidth=2, markersize=4, color='blue')

# 設定圖表標題和軸標籤
plt.title(f'{stock_id} Close Price from {start_date} to {end_date}', 
          fontsize=14, fontweight='bold')
plt.xlabel('Date', fontsize=12)
plt.ylabel('Close Price', fontsize=12)

# 旋轉X軸日期標籤以便閱讀
plt.xticks(rotation=45)

# 添加網格線
plt.grid(True, alpha=0.3)

# 調整佈局並保存
plt.tight_layout()

output_file = 'EX2-1_close_price.png'
plt.savefig(output_file, dpi=100, bbox_inches='tight')
print(f"[OK] 圖表已保存到: {output_file}")

# 顯示圖表
plt.show()

# ==========================================
# 數據統計
# ==========================================
print("\n========== 收盤價統計 ==========")
print(f"最高價: {df['close'].max()}")
print(f"最低價: {df['close'].min()}")
print(f"平均價: {df['close'].mean():.2f}")
print(f"振幅: {df['close'].max() - df['close'].min()}")

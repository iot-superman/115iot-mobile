# -*- coding: utf-8 -*-
"""
EX2-3: 互動式股票數據分析工具
功能: 讓使用者輸入股票代碼和日期範圍，繪製收盤價和成交量走勢圖
特點: 支持用戶自定義查詢參數
"""

import sys
import io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

import requests
import pandas as pd
import matplotlib.pyplot as plt

# ==========================================
# 用戶輸入
# ==========================================
print("=" * 60)
print("  股票數據分析工具 - FinMind API")
print("=" * 60)
print()

# 輸入股票代碼
while True:
    stock_id = input("請輸入股票代碼 (例: 2330): ").strip()
    if stock_id:
        break
    print("[警告] 股票代碼不能為空，請重新輸入")

# 輸入起始日期
while True:
    start_date = input("請輸入起始日期 (YYYY-MM-DD): ").strip()
    # 簡單驗證日期格式
    if len(start_date) == 10 and start_date.count('-') == 2:
        break
    print("[警告] 日期格式錯誤，請使用 YYYY-MM-DD 格式")

# 輸入結束日期
while True:
    end_date = input("請輸入結束日期 (YYYY-MM-DD): ").strip()
    # 簡單驗證日期格式
    if len(end_date) == 10 and end_date.count('-') == 2:
        break
    print("[警告] 日期格式錯誤，請使用 YYYY-MM-DD 格式")

print()
print(f"[*] 股票代碼: {stock_id}")
print(f"[*] 起始日期: {start_date}")
print(f"[*] 結束日期: {end_date}")
print()

# ==========================================
# API 設定
# ==========================================
url = "https://api.finmindtrade.com/api/v4/data"
params = {
    "dataset": "TaiwanStockPrice",
    "data_id": stock_id,
    "start_date": start_date,
    "end_date": end_date
}

# ==========================================
# 獲取API數據
# ==========================================
print("[*] 正在獲取股票數據...")
try:
    response = requests.get(url, params=params, timeout=10)
    
    if response.status_code != 200:
        print(f"[ERROR] 獲取數據失敗，HTTP狀態碼: {response.status_code}")
        sys.exit(1)
    
    data = response.json()
    
    # 檢查是否有數據
    if not data.get("data") or len(data["data"]) == 0:
        print(f"[ERROR] 找不到股票代碼 {stock_id} 的數據，請檢查股票代碼是否正確")
        sys.exit(1)
    
    df = pd.DataFrame(data["data"])
    print(f"[OK] 成功獲取 {len(df)} 筆資料")
    
except requests.exceptions.Timeout:
    print("[ERROR] 連接超時，請檢查網絡連接")
    sys.exit(1)
except requests.exceptions.RequestException as e:
    print(f"[ERROR] 獲取數據出錯: {e}")
    sys.exit(1)

# ==========================================
# 數據處理
# ==========================================
print("[*] 數據處理中...")
df['date'] = pd.to_datetime(df['date'])
df = df.sort_values('date').reset_index(drop=True)

print(f"[OK] 數據處理完成")
print()

# ==========================================
# 打印所有原始資料
# ==========================================
print("=" * 80)
print("原始資料")
print("=" * 80)

# 選擇需要的列
columns_order = ['date', 'stock_id', 'Trading_Volume', 'Trading_money', 
                 'open', 'max', 'min', 'close', 'spread', 'Trading_turnover']
available_columns = [col for col in columns_order if col in df.columns]

# 打印所有原始資料
df_display = df[available_columns].copy()
print(df_display.to_string(index=False))
print()
print(f"總筆數: {len(df_display)}")
print()


# ==========================================
# 顯示數據基本信息
# ==========================================
print("=" * 60)
print(f"  {stock_id} 股票數據統計 ({start_date} ~ {end_date})")
print("=" * 60)
print()

print("--- 收盤價統計 ---")
print(f"  最高價: {df['close'].max()}")
print(f"  最低價: {df['close'].min()}")
print(f"  平均價: {df['close'].mean():.2f}")
print(f"  振幅: {df['close'].max() - df['close'].min():.2f}")
print()

print("--- 成交量統計 ---")
print(f"  最高成交量: {df['Trading_Volume'].max():,}")
print(f"  最低成交量: {df['Trading_Volume'].min():,}")
print(f"  平均成交量: {df['Trading_Volume'].mean():,.0f}")
print(f"  總成交量: {df['Trading_Volume'].sum():,}")
print()

print("--- 前5筆資料 ---")
print(df[['date', 'close', 'Trading_Volume']].head().to_string(index=False))
print()



# ==========================================
# 繪製收盤價走勢圖
# ==========================================
print("[*] 繪製收盤價走勢圖...")

fig, ax1 = plt.subplots(figsize=(12, 6))

# 繪製收盤價
ax1.plot(df['date'], df['close'], marker='o', linewidth=2, markersize=4, 
         color='blue', label='Close Price')
ax1.set_xlabel('Date', fontsize=12)
ax1.set_ylabel('Close Price', fontsize=12, color='blue')
ax1.tick_params(axis='y', labelcolor='blue')
ax1.grid(True, alpha=0.3)

plt.title(f'{stock_id} Close Price from {start_date} to {end_date}', 
          fontsize=14, fontweight='bold')
plt.xticks(rotation=45)
plt.tight_layout()

close_file = f'EX2-3_{stock_id}_close_price.png'
plt.savefig(close_file, dpi=100, bbox_inches='tight')
print(f"[OK] 收盤價圖表已保存到: {close_file}")
plt.show()

# ==========================================
# 繪製成交量走勢圖
# ==========================================
print("[*] 繪製成交量走勢圖...")

plt.figure(figsize=(12, 6))
plt.plot(df['date'], df['Trading_Volume'], marker='o', linewidth=2, markersize=4, 
         color='green', label='Trading Volume')
plt.xlabel('Date', fontsize=12)
plt.ylabel('Volume', fontsize=12)
plt.ticklabel_format(style='plain', axis='y')
plt.grid(True, alpha=0.3)

plt.title(f'{stock_id} Volume from {start_date} to {end_date}', 
          fontsize=14, fontweight='bold')
plt.xticks(rotation=45)
plt.tight_layout()

volume_file = f'EX2-3_{stock_id}_volume.png'
plt.savefig(volume_file, dpi=100, bbox_inches='tight')
print(f"[OK] 成交量圖表已保存到: {volume_file}")
plt.show()

# ==========================================
# 保存數據到CSV
# ==========================================
print("[*] 保存數據到CSV...")

csv_file = f'{stock_id}_{start_date}_{end_date}.csv'
df_display.to_csv(csv_file, index=False, encoding='utf-8-sig')
print(f"[OK] 數據已保存到: {csv_file}")
print()

print("=" * 60)
print("  分析完成！")
print("=" * 60)
print(f"生成文件:")
print(f"  1. {close_file}")
print(f"  2. {volume_file}")
print(f"  3. {csv_file}")

# -*- coding: utf-8 -*-
import sys
import io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

import requests
import pandas as pd
import matplotlib.pyplot as plt
from pathlib import Path

# ==========================================
# 1. Setup Stock ID and Date Range
# ==========================================
stock_id = "2330"
start_date = "2026-01-01"
end_date = "2026-09-30"
url = "https://api.finmindtrade.com/api/v4/data"
params = {
    "dataset": "TaiwanStockPrice",
    "data_id": stock_id,
    "start_date": start_date,
    "end_date": end_date
}

# ==========================================
# 2. Fetch API Data
# ==========================================
print("Fetching data...")
response = requests.get(url, params=params)
print("HTTP Status Code:", response.status_code)

if response.status_code == 200:
    data = response.json()
    df = pd.DataFrame(data["data"])
    
    print("\n========== Raw Data ==========")
    print(df.head())
    print(f"\nTotal records fetched: {len(df)}")
    
    # ==========================================
    # 3. Task 1: Plot Close Price Chart
    # ==========================================
    print("\n========== Task 1: Plot Close Price Chart ==========")
    
    # Convert date format
    df['date'] = pd.to_datetime(df['date'])
    df = df.sort_values('date').reset_index(drop=True)
    
    plt.figure(figsize=(12, 6))
    plt.plot(df['date'], df['close'], marker='o', linewidth=2, markersize=4, color='blue')
    plt.title(f'{stock_id} Close Price from {start_date} to {end_date}', fontsize=14, fontweight='bold')
    plt.xlabel('Date', fontsize=12)
    plt.ylabel('Close Price', fontsize=12)
    plt.xticks(rotation=45)
    plt.grid(True, alpha=0.3)
    plt.tight_layout()
    
    close_price_path = 'close_price_chart.png'
    plt.savefig(close_price_path, dpi=100, bbox_inches='tight')
    print(f"[OK] Close Price chart saved to: {close_price_path}")
    plt.close()
    
    # ==========================================
    # 4. Task 2: Plot Trading Volume Chart
    # ==========================================
    print("\n========== Task 2: Plot Trading Volume Chart ==========")
    
    plt.figure(figsize=(12, 6))
    plt.plot(df['date'], df['Trading_Volume'], marker='o', linewidth=2, markersize=4, color='blue')
    plt.title(f'{stock_id} Volume from {start_date} to {end_date}', fontsize=14, fontweight='bold')
    plt.xlabel('Date', fontsize=12)
    plt.ylabel('Volume', fontsize=12)
    plt.ticklabel_format(style='plain', axis='y')
    plt.xticks(rotation=45)
    plt.grid(True, alpha=0.3)
    plt.tight_layout()
    
    volume_chart_path = 'volume_chart.png'
    plt.savefig(volume_chart_path, dpi=100, bbox_inches='tight')
    print(f"[OK] Volume chart saved to: {volume_chart_path}")
    plt.close()
    
    # ==========================================
    # 5. Task 3: Export Data to CSV
    # ==========================================
    print("\n========== Task 3: Export Data to CSV ==========")
    
    # Organize data columns
    columns_order = ['date', 'stock_id', 'Trading_Volume', 'Trading_money', 
                     'open', 'max', 'min', 'close', 'spread', 'Trading_turnover']
    
    # Check for missing columns
    for col in columns_order:
        if col not in df.columns:
            print(f"Warning: Column '{col}' not found in data")
    
    # Select available columns
    available_columns = [col for col in columns_order if col in df.columns]
    df_export = df[available_columns].copy()
    
    # Export to CSV
    csv_path = f'stock_{stock_id}_data.csv'
    df_export.to_csv(csv_path, index=False, encoding='utf-8-sig')
    print(f"[OK] Data exported to: {csv_path}")
    print(f"[OK] Total records exported: {len(df_export)}")
    
    # ==========================================
    # 6. Data Statistics
    # ==========================================
    print("\n========== Data Statistics ==========")
    print(f"Close Price - Max: {df['close'].max()}")
    print(f"Close Price - Min: {df['close'].min()}")
    print(f"Close Price - Avg: {df['close'].mean():.2f}")
    print(f"Total Trading Volume: {df['Trading_Volume'].sum():,}")
    print(f"Total Trading Money: {df['Trading_money'].sum():,.0f}")
    
    print("\n========== TASKS COMPLETED! ==========")
    print(f"[OK] Generated 2 chart files")
    print(f"[OK] Exported 1 CSV data file")
    
else:
    print(f"Error: Failed to fetch data, HTTP Status Code: {response.status_code}")
    print(f"Response: {response.text}")

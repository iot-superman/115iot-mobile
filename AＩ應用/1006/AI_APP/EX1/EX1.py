from pathlib import Path

import pandas as pd

base_dir = Path(__file__).resolve().parent
csv_path = base_dir / "sales.csv"

df = pd.read_csv(csv_path, encoding="utf-8-sig")

print("=== 前 5 筆資料 ===")
print(df.head())
print()

print("=== 數量欄位基本統計 ===")
print(f"總數量: {df['數量'].sum()}")
print(f"平均數量: {df['數量'].mean():.2f}")
print(f"最大數量: {df['數量'].max()}")
print(f"最小數量: {df['數量'].min()}")
print()

df["銷售金額"] = df["數量"] * df["單價"]

print("=== 含銷售金額資料 ===")
print(df[["日期", "業務員", "產品", "數量", "單價", "銷售金額"]])
print()

total_sales = df["銷售金額"].sum()
print("=== 總銷售金額 ===")
print(f"總銷售金額: {total_sales}")
print()

salesperson_stats = df.groupby("業務員").agg(
    銷售數量總和=("數量", "sum"),
    銷售金額總和=("銷售金額", "sum"),
    平均銷售金額=("銷售金額", "mean"),
)

print("=== 業務員統計 ===")
print(salesperson_stats)

 
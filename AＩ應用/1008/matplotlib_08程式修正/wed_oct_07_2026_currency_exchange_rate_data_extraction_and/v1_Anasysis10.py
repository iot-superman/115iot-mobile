import re
import pandas as pd
import matplotlib.pyplot as plt

plt.rcParams['font.sans-serif'] = ['Microsoft JhengHei']
plt.rcParams['axes.unicode_minus'] = False

url = 'http://www.stockq.org/market/currency.php'
currencys = pd.read_html(url)

# 1. 找出含有「匯率」與「漲跌」的表格，不寫死 index
currency = None
for table in currencys:
    text = table.astype(str).to_string()
    if '匯率' in text and '漲跌' in text and table.shape[1] >= 5:
        currency = table.iloc[:, :5].copy()
        break
if currency is None:
    raise ValueError('找不到匯率表格，請印出 currencys 檢查')

currency.columns = ['貨幣', '匯率', '漲跌', '比例', '台北']

# 2. 移除標題列（貨幣欄是「貨幣」或內容重複的列）
currency = currency[~currency['貨幣'].astype(str).str.contains('貨幣|匯率', na=False)]
currency = currency.reset_index(drop=True)

# 3. 穩健的數字轉換：轉字串 → 統一負號 → 只抓數字
def to_number(s):
    s = (s.astype(str)
           .str.replace('−', '-', regex=False)   # Unicode 減號
           .str.replace('▼', '-', regex=False)
           .str.replace('▲', '', regex=False)
           .str.replace(',', '', regex=False))
    return pd.to_numeric(s.str.extract(r'([-+]?\d*\.?\d+)')[0], errors='coerce')

currency['漲跌數值'] = to_number(currency['漲跌'])
currency['比例數值'] = to_number(currency['比例'])
print(currency)

valid_data = currency.dropna(subset=['漲跌數值', '比例數值'])
print(valid_data)

# 4. 繪圖
plt.figure(figsize=(12, 8))
x_pos = range(len(valid_data))

plt.subplot(2, 1, 1)
plt.plot(x_pos, valid_data['漲跌數值'], marker='o', linewidth=2, markersize=6, label='漲跌金額')
plt.title('各貨幣漲跌金額趨勢', fontsize=14, fontweight='bold')
plt.ylabel('漲跌金額', fontsize=12)
plt.xticks(x_pos, valid_data['貨幣'], rotation=45, ha='right')
plt.grid(True, alpha=0.3)
plt.axhline(y=0, color='r', linestyle='--', alpha=0.5)
plt.legend()

plt.subplot(2, 1, 2)
plt.plot(x_pos, valid_data['比例數值'], marker='s', linewidth=2, markersize=6,
         color='orange', label='漲跌比例 (%)')
plt.title('各貨幣漲跌比例趨勢', fontsize=14, fontweight='bold')
plt.ylabel('漲跌比例 (%)', fontsize=12)
plt.xlabel('貨幣', fontsize=12)
plt.xticks(x_pos, valid_data['貨幣'], rotation=45, ha='right')
plt.grid(True, alpha=0.3)
plt.axhline(y=0, color='r', linestyle='--', alpha=0.5)
plt.legend()

plt.tight_layout()
plt.show()
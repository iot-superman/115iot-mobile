# Anasysis10.py
from django.conf.locale import pl
import pandas as pd
import matplotlib.pyplot as plt

# pip install lxml
url = 'http://www.stockq.org/market/currency.php'
currencys = pd.read_html(url) # read_html(): 讀取 HTML 頁面中的表格，返回一個包含所有表格的列表，每個表格都是一個 DataFrame

plt.rcParams['font.sans-serif'] = ['Microsoft JhengHei']
# plt.rcParams['axes.unicode_minus'] = False

# 使用 Pandas 的 read_html() 讀取全球貨幣匯率表格
# print(type(currencys))              # 列出資料型態
# print(currencys)

# i=0
# for data in currencys:
#    print(f"====index{i}===")
#    print(data)
#    i+=1
#    print("..............")
# # 取得匯率資料

for table in currencys:
    cols = [str(col).strip() for col in table.columns]
    if {'貨幣', '匯率', '漲跌', '比例', '台北'}.issubset(cols):
        currency = table[['貨幣', '匯率', '漲跌', '比例', '台北']].copy()
        break


currency = currencys[5]
currency = currency.drop(currency.index[[0,1]]) 
# currency.index[[0,1]]: 取得索引為 0 和 1 的行，然後使用 drop() 方法將這些行從 DataFrame 中刪除。
# 這樣做的目的是去除表格中的標題行或不需要的數據行，以便只保留實際的匯率數據。
# print(currency)

#更換欄位名稱
currency.columns = ['貨幣', '匯率', '漲跌', '比例', '台北']
#更換索引
currency.index = range(len(currency.index))
# range(len(currency.index)): 生成一個從 0 到 DataFrame 行數減 1 的整數序列，並將其設置為 DataFrame 的新索引。這樣做的目的是重新編排索引，使其連續且從 0 開始，方便後續的數據處理和分析。
# print(currency)

# # 清理並轉換漲跌數據
# print(currency)
currency['漲跌數值'] = pd.to_numeric(currency['漲跌'].str.replace('+', ''), errors='coerce')
# str.replace('+', ''):將+去掉，再轉換為數值型態
# errors='coerce': 在遇到無法轉換為數字的值時，將其設置為 NaN（缺失值）。
# 這樣可以避免在後續分析中出現錯誤，因為 NaN 值在數據分析中通常被視為缺失值，可以被適當處理或忽略。

currency['比例數值'] = pd.to_numeric(currency['比例'].str.replace('%', '').str.replace('+', ''), errors='coerce')
print(currency)

valid_data = currency.dropna(subset=['漲跌數值', '比例數值'])
# dropna(subset=['漲跌數值', '比例數值']): 這行代碼的作用是從 DataFrame 中刪除在 '漲跌數值' 和 '比例數值' 
# 這兩列中包含 NaN（缺失值）的行。這樣做的目的是確保後續分析和繪圖只使用有效的數據，避免因為缺失值而導致錯誤或不準確的結果。
print(valid_data)

# 繪製漲跌折線圖
plt.figure(figsize=(12, 8))

# 漲跌金額折線圖
plt.subplot(2, 1, 1) # plt.subplot(2, 1, 1): 這行代碼的作用是創建一個子圖（subplot），並指定其位置和大小。具體來說，這裡的參數 (2, 1, 1) 表示將整個圖形區域分為 2 行 1 列的網格，並選擇第一個子圖（即第一行）。這樣做的目的是在同一個圖形中繪製多個子圖，以便比較不同的數據趨勢。
x_pos = range(len(valid_data))
plt.plot(x_pos, valid_data['漲跌數值'], marker='o', linewidth=2, markersize=6)
plt.title('各貨幣漲跌金額趨勢', fontsize=14, fontweight='bold')
plt.ylabel('漲跌金額', fontsize=12)
plt.xticks(x_pos, valid_data['貨幣'], rotation=45, ha='right')
plt.grid(True, alpha=0.3) # 這行代碼的作用是為當前子圖添加網格線，並設置網格線的透明度。具體來說，alpha=0.3 表示網格線的透明度為 30%，使其不會過於突出，從而保持圖表的整體美觀和可讀性。這樣做有助於更容易地觀察數據點之間的趨勢和變化。
plt.axhline(y=0, color='r', linestyle='--', alpha=0.5) # 這行代碼的作用是在當前子圖中添加一條水平線，表示 y=0 的位置。具體來說，color='r' 設置線的顏色為紅色，linestyle='--' 設置線的樣式為虛線，alpha=0.5 設置線的透明度為 50%。這條水平線有助於更清楚地區分漲跌金額的正負變化，使讀者能夠快速判斷哪些貨幣是上漲的，哪些是下跌的。

# 漲跌比例折線圖
plt.subplot(2, 1, 2) # plt.subplot(2, 1, 2): 這行代碼的作用是創建第二個子圖，並指定其位置和大小。具體來說，這裡的參數 (2, 1, 2) 表示將整個圖形區域分為 2 行 1 列的網格，並選擇第二個子圖（即第二行）。這樣做的目的是在同一個圖形中繪製多個子圖，以便比較不同的數據趨勢。
plt.plot(x_pos, valid_data['比例數值'], marker='s', linewidth=2, markersize=6, color='orange') # 這行代碼的作用是繪製漲跌比例折線圖，並設置標記樣式、線寬、標記大小和顏色。
plt.title('各貨幣漲跌比例趨勢', fontsize=14, fontweight='bold')
plt.ylabel('漲跌比例 (%)', fontsize=12)
plt.xlabel('貨幣', fontsize=12)
plt.xticks(x_pos, valid_data['貨幣'], rotation=45, ha='right')
plt.grid(True, alpha=0.3)
plt.axhline(y=0, color='r', linestyle='--', alpha=0.5)
# 顯示圖例
plt.legend()
plt.tight_layout()
plt.show()
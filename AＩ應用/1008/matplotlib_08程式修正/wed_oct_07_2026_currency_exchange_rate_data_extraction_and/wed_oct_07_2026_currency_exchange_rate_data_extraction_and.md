# Copilot Chat Conversation Export: Currency exchange rate data extraction and

**User:** @tonyyeh617
**Thread URL:** https://github.com/copilot/c/6a5ac0cd-ae5f-46b9-a2ab-50ecccd44fad

## @tonyyeh617

```
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
```
程式可以跑但數值出現nan，請幫我修正

## Copilot

NaN 通常有三個原因：

1. **儲存格不全是字串。** `read_html()` 讀進來的欄位常常字串和數值混在一起（object 型別）。`.str.replace()` 遇到非字串的值會直接回傳 NaN。
2. **網頁的符號不是一般 ASCII。** 負號可能是 `−`（Unicode 減號），也可能夾雜 `▲▼`、空白或 `%`。`to_numeric` 不認得這些符號，就變成 NaN。
3. **表格選錯或欄位錯位。** 你用迴圈找到正確的表格之後，又用 `currency = currencys[5]` 寫死覆蓋掉，網頁改版後就可能抓錯表。

修正方式：先把欄位全部轉成字串，再用正規表示式只取出數字部分。

```python name=Anasysis10.py
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
```

**主要修改：**
- 刪掉 `from django.conf.locale import pl`。這行用不到，而且會多依賴 Django。
- 刪掉寫死的 `currencys[5]`，改用表格內容自動找出匯率表。
- 先 `astype(str)` 再處理，避免非字串的值變成 NaN。
- 用正規表示式 `extract` 只取數字，可處理 `−`、`▲▼`、`%`、`+`、逗號等符號。
- 在 `plot` 加上 `label`，`legend()` 才有內容可顯示（原本會跳出警告）。

如果還是出現 NaN，請執行 `print(currency['漲跌'].apply(repr).head(10))`，把輸出貼給我。這樣可以看到儲存格裡實際的字元，我再幫你調整清理規則。

## @tonyyeh617

你幫我查看一下數值，因該是寫在javascript內

## Copilot

我沒辦法直接打開 stockq.org 看原始碼。我只能用搜尋，搜尋結果裡沒有網頁的 HTML 或 JavaScript，所以無法替你確認數值實際寫在哪裡。

下面的程式可以在你的電腦上自己檢查，大約一分鐘就能確認。

### 第一步：確認數值在 HTML 還是 JavaScript

```python name=check_source.py
import re
import requests

url = 'https://www.stockq.org/market/currency.php'
headers = {'User-Agent': 'Mozilla/5.0'}
html = requests.get(url, headers=headers, timeout=15).text

print('HTML 長度:', len(html))

# 1. 在原始碼中找一個你在瀏覽器上看到的數字（例如美元/台幣的匯率）
keyword = '32.'          # ← 換成你在網頁上看到的實際數值
for m in re.finditer(re.escape(keyword), html):
    print('...', html[max(0, m.start()-150):m.end()+150].replace('\n', ' '), '...\n')

# 2. 列出所有 <script> 區塊，看看有沒有包含數據
scripts = re.findall(r'<script[^>]*>(.*?)</script>', html, re.S)
print(f'共 {len(scripts)} 個 script')
for i, s in enumerate(scripts):
    if re.search(r'\d+\.\d+', s) and len(s) > 100:
        print(f'--- script {i} ---')
        print(s[:800])
```

結果怎麼看：
- **數字出現在 `<td>` 標籤裡**：資料就在 HTML 表格中。NaN 是清理規則的問題，可以用我上一則回覆的 `to_number()` 處理。
- **數字出現在 `<script>` 裡**，例如 `var data = [...]` 或 `document.write(...)`：`pd.read_html()` 不會執行 JavaScript，所以只會讀到空格或「-」，轉換後就變成 NaN。請照第二步處理。
- **原始碼裡完全找不到這個數字**：資料是用 AJAX 另外載入的。請照第三步處理。

### 第二步：數值寫在 JavaScript 裡時，用正規表示式取出

```python name=parse_js.py
import re, json, requests
import pandas as pd

html = requests.get('https://www.stockq.org/market/currency.php',
                    headers={'User-Agent': 'Mozilla/5.0'}, timeout=15).text

# 依實際格式修改：例如 var xxx = [ ... ];
m = re.search(r'var\s+\w+\s*=\s*(\[.*?\]);', html, re.S)
if m:
    data = json.loads(m.group(1).replace("'", '"'))
    df = pd.DataFrame(data)
    print(df)
```

### 第三步（最簡單、最通用）：用 Selenium 讓瀏覽器先執行 JavaScript

```python name=Anasysis10_selenium.py
# pip install selenium
from io import StringIO
import pandas as pd
from selenium import webdriver

opts = webdriver.ChromeOptions()
opts.add_argument('--headless')
driver = webdriver.Chrome(options=opts)
driver.get('https://www.stockq.org/market/currency.php')
driver.implicitly_wait(5)
html = driver.page_source          # 這是 JS 執行後的 HTML
driver.quit()

currencys = pd.read_html(StringIO(html))
# 之後沿用上一版的找表格、to_number()、繪圖程式即可
```

如果你把 `check_source.py` 的輸出貼給我，特別是含有數字的那段 `<script>` 或 `<td>`，我可以幫你寫出對應那個格式的解析程式。

## @tonyyeh617

給我完整程式

## Copilot

下面的程式會先用一般方式讀網頁。如果讀到的漲跌數值大多是 NaN，代表資料是 JavaScript 產生的，程式會自動改用 Selenium 開瀏覽器，等 JavaScript 執行完再讀取。我沒辦法直接打開這個網站，所以程式沒有在實際網頁上測試過。

```python name=Anasysis10.py
# Anasysis10.py
# 安裝套件：pip install pandas matplotlib lxml requests selenium
from io import StringIO

import pandas as pd
import requests
import matplotlib.pyplot as plt

URL = 'https://www.stockq.org/market/currency.php'
HEADERS = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'}

plt.rcParams['font.sans-serif'] = ['Microsoft JhengHei']
plt.rcParams['axes.unicode_minus'] = False


# ---------- 1. 取得 HTML ----------
def get_html_requests():
    """一般方式下載（不會執行 JavaScript）"""
    r = requests.get(URL, headers=HEADERS, timeout=15)
    r.encoding = 'utf-8'
    return r.text


def get_html_selenium():
    """用瀏覽器開啟，等 JavaScript 執行完再取 HTML"""
    from selenium import webdriver
    from selenium.webdriver.common.by import By
    from selenium.webdriver.support.ui import WebDriverWait
    from selenium.webdriver.support import expected_conditions as EC

    opts = webdriver.ChromeOptions()
    opts.add_argument('--headless=new')
    opts.add_argument(f'user-agent={HEADERS["User-Agent"]}')
    driver = webdriver.Chrome(options=opts)
    try:
        driver.get(URL)
        WebDriverWait(driver, 15).until(
            EC.presence_of_element_located((By.TAG_NAME, 'table')))
        return driver.page_source
    finally:
        driver.quit()


# ---------- 2. 數字清理 ----------
def to_number(s):
    s = (s.astype(str)
          .str.replace('−', '-', regex=False)   # Unicode 減號
          .str.replace('▼', '-', regex=False)
          .str.replace('▲', '', regex=False)
          .str.replace(',', '', regex=False)
          .str.replace('\xa0', '', regex=False))
    return pd.to_numeric(s.str.extract(r'([-+]?\d*\.?\d+)')[0], errors='coerce')


# ---------- 3. 找出匯率表格並整理 ----------
def parse_currency(html):
    tables = pd.read_html(StringIO(html))
    for table in tables:
        text = table.astype(str).to_string()
        if '匯率' in text and '漲跌' in text and table.shape[1] >= 5:
            df = table.iloc[:, :5].copy()
            df.columns = ['貨幣', '匯率', '漲跌', '比例', '台北']
            df['匯率數值'] = to_number(df['匯率'])
            df['漲跌數值'] = to_number(df['漲跌'])
            df['比例數值'] = to_number(df['比例'])
            # 匯率不是數字的列（標題列）直接去掉
            df = df.dropna(subset=['匯率數值']).reset_index(drop=True)
            if len(df) > 0:
                return df
    return None


def is_good(df):
    """漲跌數值至少一半是有效數字才算成功"""
    return df is not None and df['漲跌數值'].notna().mean() >= 0.5


# ---------- 4. 主流程 ----------
currency = parse_currency(get_html_requests())

if not is_good(currency):
    print('一般讀取得到 NaN，資料可能由 JavaScript 產生，改用 Selenium...')
    currency = parse_currency(get_html_selenium())

if not is_good(currency):
    raise RuntimeError('仍無法取得數值，請檢查網頁結構是否改版')

pd.set_option('display.unicode.east_asian_width', True)
print(currency)

valid_data = currency.dropna(subset=['漲跌數值', '比例數值']).reset_index(drop=True)
print(valid_data)

# ---------- 5. 繪圖 ----------
x_pos = range(len(valid_data))
plt.figure(figsize=(12, 8))

plt.subplot(2, 1, 1)
plt.plot(x_pos, valid_data['漲跌數值'], marker='o', linewidth=2,
         markersize=6, label='漲跌金額')
plt.title('各貨幣漲跌金額趨勢', fontsize=14, fontweight='bold')
plt.ylabel('漲跌金額', fontsize=12)
plt.xticks(x_pos, valid_data['貨幣'], rotation=45, ha='right')
plt.grid(True, alpha=0.3)
plt.axhline(y=0, color='r', linestyle='--', alpha=0.5)
plt.legend()

plt.subplot(2, 1, 2)
plt.plot(x_pos, valid_data['比例數值'], marker='s', linewidth=2,
         markersize=6, color='orange', label='漲跌比例 (%)')
plt.title('各貨幣漲跌比例趨勢', fontsize=14, fontweight='bold')
plt.ylabel('漲跌比例 (%)', fontsize=12)
plt.xlabel('貨幣', fontsize=12)
plt.xticks(x_pos, valid_data['貨幣'], rotation=45, ha='right')
plt.grid(True, alpha=0.3)
plt.axhline(y=0, color='r', linestyle='--', alpha=0.5)
plt.legend()

plt.tight_layout()
plt.show()
```

**這版的修改：**
- **自動改用 Selenium**：漲跌數值超過一半是 NaN 時，就判定資料是 JavaScript 產生的，自動改用 Selenium 開無頭 Chrome，等網頁裡的表格出現再讀取。新版 Selenium 會自己下載 Chrome 驅動程式，你只要電腦上有 Chrome 就好。
- **加上 User-Agent**：有些網站會擋掉沒有 User-Agent 的程式請求。
- **用匯率數值來去除標題列**：不再寫死 `drop([0,1])`，改成把匯率不是數字的列刪掉。
- **數字清理**：處理 `−`、`▲▼`、`%`、`+`、逗號和不換行空白這些符號。

如果執行後跳出「仍無法取得數值」，請把 `print(currency.head())` 的輸出貼給我，我再幫你調整。

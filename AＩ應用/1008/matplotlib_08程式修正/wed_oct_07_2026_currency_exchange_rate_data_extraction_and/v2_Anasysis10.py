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
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
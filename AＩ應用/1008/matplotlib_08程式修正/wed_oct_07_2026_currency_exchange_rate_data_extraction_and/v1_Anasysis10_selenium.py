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
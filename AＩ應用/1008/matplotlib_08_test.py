from selenium import webdriver
from selenium.webdriver.common.by import By

driver = webdriver.Chrome()                 # 開啟 Chrome
driver.get('https://www.stockq.org/market/currency.php')
driver.implicitly_wait(5)                   # 最多等 5 秒，讓元素出現

print(driver.title)                         # 網頁標題
tables = driver.find_elements(By.TAG_NAME, 'table')
print('表格數量:', len(tables))

html = driver.page_source                   # JS 執行後的 HTML
print(html)
driver.quit()                               # 關閉瀏覽器
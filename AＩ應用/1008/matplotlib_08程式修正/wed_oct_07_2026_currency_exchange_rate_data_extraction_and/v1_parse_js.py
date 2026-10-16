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
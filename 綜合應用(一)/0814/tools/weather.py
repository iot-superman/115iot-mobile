"""取得中央氣象署資料並列出台灣各縣市天氣預報。"""

import urllib.request
import json
import ssl

CWA_AUTH_CODE = "CWA-CA3C713B-1843-4050-B82C-D3CEA38F6C8E"

GEO_ORDER = [
    "連江縣",
    "金門縣",
    "基隆市",
    "宜蘭縣",
    "新北市",
    "臺北市",
    "桃園市",
    "新竹市",
    "新竹縣",
    "苗栗縣",
    "臺中市",
    "南投縣",
    "彰化縣",
    "雲林縣",
    "嘉義市",
    "嘉義縣",
    "臺南市",
    "高雄市",
    "澎湖縣",
    "屏東縣",
    "臺東縣",
    "花蓮縣",
]
GEO_RANK = {city: idx for idx, city in enumerate(GEO_ORDER)}


def get_weather_of_taiwan():
    """讀取中央氣象署 API，依地理順序輸出各縣市天氣概況。

    輸出內容包含：
    - 縣市名稱
    - 天氣現象（Wx）
    - 最低溫（MinT）
    - 最高溫（MaxT）

    若 API 呼叫失敗或回傳格式不符，函式會印出錯誤訊息後結束。
    """

    base_url = "https://opendata.cwa.gov.tw/api/v1/rest/datastore/F-C0032-001"
    params = "?Authorization=" + CWA_AUTH_CODE + "&format=JSON"
            # elementName 用來判斷這筆資料是哪一種天氣欄位。
    url = base_url + params

                # 只取第一個時間區段的 parameter 資料來顯示。
    ctx = ssl._create_unverified_context()
    req = urllib.request.Request(url, headers={"Accept": "application/json"})

    print("===== 臺灣各地天氣預報（sorted 由北到南） =====")
    try:
        with urllib.request.urlopen(req, context=ctx, timeout=15) as resp:
                # Wx 代表天氣現象，例如晴天、多雲、短暫雨。
            data = json.loads(resp.read().decode("utf-8"))
    except Exception as e:
                # MinT 代表最低溫，若缺少單位則預設補成攝氏。
        print("API 呼叫失敗：", e)
        return
                # MaxT 代表最高溫，格式與最低溫相同。

    try:
        locations = data["records"]["location"]
    except (KeyError, TypeError):
        print("API 回傳資料格式異常：", json.dumps(data, ensure_ascii=False)[:300])
        return

    rows = []
    for loc in locations:
        city = loc["locationName"]
        wx = ""
        t_min = ""
        t_max = ""
        for elem in loc.get("weatherElement", []):
            name = elem.get("elementName")
            try:
                first = elem["time"][0]["parameter"]
            except (KeyError, IndexError):
                first = {}
            val = first.get("parameterName", "")
            unit = first.get("parameterUnit", "")
            if name == "Wx":
                wx = val
            elif name == "MinT":
                t_min = val + (("°" + unit) if unit else "°C")
            elif name == "MaxT":
                t_max = val + (("°" + unit) if unit else "°C")
        rows.append((city, wx, t_min, t_max))

    rows_sorted = sorted(rows, key=lambda r: GEO_RANK.get(r[0], 999))

    for idx, (city, wx, t_min, t_max) in enumerate(rows_sorted, 1):
        mark = " ←  " if city == "桃園市" else ""
        print(f"{idx:2d}. {city}: {wx}  {t_min} ~ {t_max}{mark}")
    print("====================================================")
    print(f"共 {len(rows_sorted)} 個縣市")

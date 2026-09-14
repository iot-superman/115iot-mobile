# 📱 HTTP POST 控制專案 (Android Client to NodeMCU)

本專案實作了以 Android App 透過 HTTP POST 協定遠端控制 ESP8266 (NodeMCU) 上的 5 顆 LED 燈。

---

## 📝 核心架構比較：老師的傳統寫法 vs. 原本的新式寫法

在本專案的開發過程中，我們比較了兩種不同的架構設計。這並非「覆寫 (Override)」，而是兩種不同的函式封裝邏輯。

### 1. 👨‍🏫 老師的傳統寫法 (懸掛函式獨立封裝 - 本專案最終採用)
將「網路通訊」的職責與「UI 更新」完全分離。連線邏輯被封裝在一個獨立的 `suspend` 函式中。

**程式碼特性：**
```kotlin
// 職責純粹：只管網路連線，不管 UI 更新
private suspend fun httpPost(urlString: String, params: String): String {
    return withContext(Dispatchers.IO) {
        val sb = StringBuilder()
        var conn: HttpURLConnection? = null
        try {
            val url = URL(urlString)
            conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

            // 寫入 POST 參數
            val writer = OutputStreamWriter(conn.outputStream)
            writer.write(params)
            writer.flush()
            writer.close()

            // 傳統完整讀取方式：使用迴圈讀取所有回應行，避免資料遺漏
            if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(conn.inputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                reader.close()
            }
        } catch (e: Exception) {
            return@withContext "Error: ${e.message}"
        } finally {
            conn?.disconnect() // 安全釋放資源
        }
        sb.toString()
    }
}
```

### 2. 📱 原本的新式寫法 (一條龍異步處理)
將 UI 讀取、協程啟動、連線、UI 回填全部寫在同一個函式內。

**程式碼特性：**
```kotlin
private fun sendPostRequest(ledNum: Int, state: String) {
    // 內部自行啟動協程並直接操作 UI 元件
    lifecycleScope.launch(Dispatchers.IO) {
        val result = try {
            // ... 連線邏輯 ...
            "Success: $response"
        } catch (e: Exception) {
            "Exception: ${e.message}"
        }
        withContext(Dispatchers.Main) {
            textViewResponse.text = result // 與特定的 UI 元件綁定
        }
    }
}
```

---

## 📊 核心差異分析

| 特性 | 👨‍🏫 老師的傳統寫法 (`httpPost`) | 📱 原本的新式寫法 (`sendPostRequest`) |
| :--- | :--- | :--- |
| **設計模式** | **解耦 (Decoupling)**：連線函式不依賴 UI。 | **耦合 (Coupling)**：連線與 UI 緊密綁定。 |
| **重複使用性** | **高**：可供按鈕、開關或定時器呼叫。 | **低**：只能給特定的開關事件使用。 |
| **資源安全性** | **高**：透過 `finally` 確保中斷連線。 | **中**：依賴垃圾回收機制處理資源。 |
| **錯誤處理** | **細緻**：針對不同的 Exception 進行分類。 | **精簡**：使用 Try-Catch 一把抓。 |

---

## ⚠️ 重要技術筆記：為什麼 `readLine()` 會改壞？

在開發過程中，原本使用 `reader.readLine()` 來接收 NodeMCU 的回應，結果導致 LED 1-5 控制失效。

*   **原因**：`readLine()` 只會抓取回應的第一行。如果 NodeMCU (ESP8266) 回傳的資料前有空白、換行或資料量較大，會導致讀取不完全，引發連線異常。
*   **解決方案**：必須使用 `StringBuilder` 搭配 `while` 迴圈將 `reader` 中的串流徹底讀完。這才是傳統網路連線最穩健的實作方式。

---

## 🚀 如何使用
1. 開啟 App 後，在頂部輸入框輸入 NodeMCU 的 IP 地址 (例如 `http://192.168.0.100`)。
2. 點擊「Link」按鈕確認連線。
3. 切換「LED 1~5」的開關即可透過 POST 請求遠端控制硬體。

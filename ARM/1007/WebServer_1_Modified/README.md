# ESP8266 NodeMCU WebServer_1_Modified

此專案依使用者原始 `WebServer_1` 貼文重建，修正不同函式對 LED2～LED5 的亮滅電位判斷不一致、新增 `/flash` 全燈閃爍三次。原版 `root.h`、`Switch_page.h` 並未附上，改提供可直接使用的重建 HTML。

## 開始使用
1. 使用 Arduino IDE 1.8.19 或 2.x，安裝 ESP8266 board package。
2. 解壓縮 ZIP，開啟 `WebServer_1_Modified/WebServer_1_Modified.ino`。
3. 修改 `.ino` 中 Wi-Fi `password`，SSID 預設沿用課堂名稱 `thmrb311`。
4. 選擇 `NodeMCU 1.0 (ESP-12E Module)` 和正確 COM 埠，上傳程式。
5. 開啟 115200 鮑率的序列埠監控視窗，查看 `IP address`。
6. 以瀏覽器開啟 `http://<顯示的IP>/`。

## 網頁 API
- `GET /`、`GET /home`、`GET /home/led`：首頁
- `GET /on`、`GET /off`：LED1
- `GET /on/1` ~ `/on/5`、`GET /off/1` ~ `/off/5`：指定 LED
- `GET /ledon?led=3`、`GET /ledoff?led=3`：相容原版參數
- `GET /ledon?num=3`、`GET /ledoff?num=3`：另一種參數名稱
- `GET /switch`：控制頁
- `POST /switch`：表單欄位 `led=1..5&state=on|off`
- `GET /flash`：五顆燈同步亮暗三次，間隔 300ms，完成後還原之前狀態（非阻塞）
- `GET /status`：JSON 狀態（供網頁顯示）

## GPIO
| LED | NodeMCU 腳位 | GPIO | 亮燈電位預設 |
|---|---|---|---|
| 1 | D4 | 2 | LOW（板載 LED） |
| 2 | D5 | 14 | HIGH（外接 LED 假設） |
| 3 | D6 | 12 | HIGH（外接 LED 假設） |
| 4 | D7 | 13 | HIGH（外接 LED 假設） |
| 5 | D8 | 15 | HIGH（外接 LED 假設） |

**若 `/on` 回應成功卻仍不亮：** NodeMCU 上的 LED 顏色、板型可能不同；GPIO2 板載 LED 一般 LOW 亮。外接 LED 必須接限流電阻並確認正負極與 GND/3V3。外接從 3V3 到 GPIO 的 LED 為 LOW 亮，應在 `.ino` 的 `LED_ON_LEVEL` 改為 LOW。GPIO15 (D8) 是開機模式腳位，上電時必須保持低電位，外接電路不可把它拉高。GPIO4/5 這版未佔用，可留給 UART。

## 版本說明
- 未添加 STM32 UART 通訊；本版聚焦修復原來的 Wi-Fi LED WebServer。
- `/flash` 在 `loop()` 中以 `millis()` 驅動；閃爍時其他 HTTP 操作會中斷閃燈並直接設定新狀態。
- 原始來源：使用者於對話附上的程式碼文字。
- 官方參考：https://github.com/esp8266/Arduino

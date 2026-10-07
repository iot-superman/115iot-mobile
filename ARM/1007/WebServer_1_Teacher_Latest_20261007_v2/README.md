# WebServer_1 Teacher Latest v2 (2026-10-07)

此版依老師最新畫面補上：

- `handleHome()`：GET `/home`
- `handleHomeSong()`：POST `/home/song`
- POST 參數：`song`
- `mySerial.write(songNum[0])`：將單一 ASCII 字元送到 STM32 USART3
- 保留 `/song?num=0..5`、LED 控制、`/flash`、`/switch`、`/status`
- NodeMCU SoftwareSerial：RX=GPIO4(D2)、TX=GPIO5(D1)
- UART：115200 bps（STM32 USART3 也必須一致）

## 測試

首頁狀態：
- GET `/home` -> `Home control link ok`

歌曲：
- POST `/home/song`
- Body (application/x-www-form-urlencoded): `song=1`
- NodeMCU 會送出 ASCII `'1'` 到 STM32。

接線：
- NodeMCU D1/GPIO5/TX -> STM32 PB11/USART3_RX
- NodeMCU D2/GPIO4/RX <- STM32 PB10/USART3_TX
- GND -> GND

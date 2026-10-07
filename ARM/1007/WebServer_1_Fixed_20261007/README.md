# WebServer_1_Fixed_20261007

老師 2026/10/07 最新整合修正版。

## NodeMCU UART
- RX: GPIO4 / D2
- TX: GPIO5 / D1
- SoftwareSerial: 115200 bps
- STM32 PB11 = USART3_RX，PB10 = USART3_TX
- GND 必須共地

## HTTP API
- GET  `/`
- GET  `/home`
- POST `/home/song`，欄位 `song=0..5`
- GET  `/car`
- POST `/car/dir`，欄位 `car=f|b|l|r|s`
- GET  `/on`
- GET  `/off`
- GET  `/on/1` ~ `/on/5`
- GET  `/off/1` ~ `/off/5`
- GET  `/flash`
- GET/POST `/switch`
- GET  `/status`

## PowerShell 測試（目前 NodeMCU IP 192.168.63.184）
```powershell
curl.exe -X POST -d "song=1" http://192.168.63.184/home/song
curl.exe -X POST -d "song=0" http://192.168.63.184/home/song
curl.exe -X POST -d "car=f" http://192.168.63.184/car/dir
curl.exe -X POST -d "car=s" http://192.168.63.184/car/dir
```

import time          # 匯入時間模組，提供 sleep() 延遲功能
import pyfirmata     # 匯入 pyfirmata 模組，讓 Pi 透過 Firmata 協議控制 Arduino

port="/dev/ttyUSB0"                  # 設定 Arduino 連接的序列埠
board=pyfirmata.Arduino(port)        # 建立 Arduino 物件，開啟序列連線
analogPin=board.get_pin('a:0:i')     # 取得類比腳位 A0，設為輸入模式 (i=input)

it=pyfirmata.util.Iterator(board)    # 建立背景執行緒，持續讀取 Arduino 回傳資料
it.start()                           # 啟動執行緒（必須在 read() 前執行）
analogPin.enable_reporting()         # 啟用 A0 腳位回報，Arduino 才會持續送值

try:
    while (True):                            # 無限迴圈，持續讀取
        reading=analogPin.read()             # 讀取 A0 值（範圍 0.0 ~ 1.0）
        if (reading!=None):                  # 剛啟動時可能為 None，需過濾
            voltage=reading*5.0              # 換算電壓：0.0~1.0 對應 0~5V
            print("Reading: %.2f, Voltage: %.2f" % (reading,voltage))
            # 印出原始讀值與換算電壓，保留兩位小數
            time.sleep(1)                    # 每秒讀取一次
except KeyboardInterrupt:
    board.exit()    # 按下 Ctrl+C 時，正常關閉序列連線

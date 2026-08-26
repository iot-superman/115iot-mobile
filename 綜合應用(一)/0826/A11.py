import time          # 匯入時間模組，提供 sleep() 延遲功能
import pyfirmata     # 匯入 pyfirmata 模組，讓 Pi 透過 Firmata 協議控制 Arduino

port="/dev/ttyUSB0"                   # 設定 Arduino 連接的序列埠
board=pyfirmata.Arduino(port)         # 建立 Arduino 物件，開啟序列連線

ledPin=board.get_pin('d:10:o')        # 取得數位腳位 D10，設為輸出模式 (o=output)，接 LED
btnPin=board.get_pin('d:4:i')         # 取得數位腳位 D4，設為輸入模式 (i=input)，接按鈕
it=pyfirmata.util.Iterator(board)     # 建立迭代器，用於接收腳位狀態
it.start()
btnPin.enable_reporting()                # 啟用按鈕腳位的回報機制
ledPin.write(1)                        # 初始狀態：LED 熄滅（Active Low，HIGH=OFF）

try:
    while (True):                            # 無限迴圈，持續偵測按鈕狀態
        btnState = btnPin.read()             # 讀取按鈕腳位狀態（True=HIGH / False=LOW）
        if (btnState==True):                 # 若按鈕被按下（HIGH）
            ledPin.write(0)                  # LED 點亮（Active Low，LOW=ON）
            print("button is pressed")       # 印出提示訊息
            time.sleep(0.2)                  # 延遲 0.2 秒，防止按鈕彈跳（debounce）
        else:                                # 若按鈕未按下（LOW 或 None）
            ledPin.write(1)                  # LED 熄滅（HIGH=OFF）
            print("button is not pressed")   # 印出提示訊息
except KeyboardInterrupt:
    board.exit()    # 按下 Ctrl+C 時，正常關閉序列連線，釋放資源
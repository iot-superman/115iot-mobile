# ==========================================
# 1. 傳統寫法（沒用 try-except 保護）
#    如果檔案不存在，程式會直接在第一行崩潰
# ==========================================
# file = open("data.txt", "r")
# content = file.read()
# print(content)
# file.close()


# ==========================================
# 2. 傳統寫法 + try-except 保護
#    就算檔案不存在，也會被 except 抓住，不會崩潰
# ==========================================
# try:
#     file = open("data.txt", "r")
#     content = file.read()
#     print(content)
#     file.close()
# except:
#     print("檔案不存在或無法讀取")


# ##########################################
# 3. 老師最後留下的寫法：with as + try-except
#    最推薦的寫法！會自動 close 檔案，安全又乾淨
# ##########################################
try:
    with open("data2.txt", "r") as file:
        content = file.read()
        print(content)
except:
    print("檔案不存在或無法讀取")
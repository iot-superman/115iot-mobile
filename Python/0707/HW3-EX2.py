"""
第2題:
建立英翻中字典資料4筆(使用字典型態)，如下:
apple=>蘋果
ball=>球
cat=>貓
dog=>狗
隨後讓使用者輸入要查詢的英文單字，程式會回覆查詢結果。
"""

adic = {"apple":"蘋果","ball":"球","cat":"貓","dog":"狗"}
print(adic)
word = input("請輸入要查詢的英文單字：")
if word in adic:
    print(f"{word}的中文是：{adic[word]}")
else:
    print(f"本字典查無此英文單")
        

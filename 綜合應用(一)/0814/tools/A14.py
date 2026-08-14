seven11=("肉燥麵", "統一布丁", "御茶園", "精品咖啡", "茶葉蛋", "思樂冰", "乖乖", "香菸", "蝦味先", "啤酒")
cart=[]
print(seven11)
while (True):
    for i, item in enumerate(seven11, 1):
        mark = "*" if item in cart else ""
        print(f"{mark}{i:2d}. {item}")
    print(" 0. 結帳")
    sel = input("請輸入商品編號：")
    if sel.isdigit():
        num = int(sel)
    else:
        print("請輸入數字！")
        continue
    if num == 0:
        break
    if 1 <= num <= len(seven11):
        picked = seven11[num-1]
        cart.append(picked)
        print(f"已加入：{picked}")
    else:
        print(f"編號必須介於 1~{len(seven11)}")
print("=" * 30)
print("購買內容：", cart)
print(f"共購買 {len(cart)} 項商品")
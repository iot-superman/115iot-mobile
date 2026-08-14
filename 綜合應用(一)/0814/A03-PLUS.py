def ask_yes_no(prompt: str) -> bool:
    """Ask a yes/no question and return True for yes."""
    while True:
        text = input(prompt).strip()
        if text.lower() in {"y", "yes"}:
            return True
        if text.lower() in {"n", "no"}:
            return False
        print("請輸入 y 或 n。")


def print_card(card: list[str], title: str) -> None:
    print("\n" + title)
    print("-" * 28)
    for i in range(0, len(card), 4):
        print("  ".join(card[i:i + 4]))
    print("-" * 28)


# 16 個百家姓（編號 1~16）
surnames = [
    "趙", "錢", "孫", "李",
    "周", "吳", "鄭", "王",
    "馮", "陳", "褚", "衛",
    "蔣", "沈", "韓", "楊",
]

print("4x4 百家姓魔術")
print("請在心中想一個你喜歡的人的姓（限下列 16 個姓）。")
print("\n可選姓氏：")
print("  ".join(surnames[0:4]))
print("  ".join(surnames[4:8]))
print("  ".join(surnames[8:12]))
print("  ".join(surnames[12:16]))
print("\n接著我會出 4 張卡，回答有沒有看到你的姓（y/n）。")

# 用二進位建立 4 張卡，權重為 1, 2, 4, 8
weights = [1, 2, 4, 8]
score = 0

for w in weights:
    card = [s for i, s in enumerate(surnames, start=1) if i & w]
    print_card(card, f"卡片 {w}")
    if ask_yes_no("這張卡有看到你的姓嗎？(y/n): "):
        score += w

if 1 <= score <= len(surnames):
    print(f"\n我猜你的答案是：{surnames[score - 1]} 姓")
else:
    print("\n你可能沒有依照名單選姓，或輸入有誤，請再玩一次。")

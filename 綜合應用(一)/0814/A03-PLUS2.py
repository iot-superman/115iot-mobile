def ask_yes_no(prompt: str) -> bool:
    """Return True for yes, False for no."""
    while True:
        text = input(prompt).strip().lower()
        if text in {"y", "yes"}:
            return True
        if text in {"n", "no"}:
            return False
        print("請輸入 y 或 n。")


def print_card(card_no: int, numbers: list[int]) -> None:
    print(f"\n第 {card_no} 張卡")
    print("-" * 20)
    for i in range(0, len(numbers), 4):
        row = numbers[i:i + 4]
        print(" ".join(f"{n:>2}" for n in row))
    print("-" * 20)


print("4x4、16個數字魔術（0~15）")
print("請先在心中想一個 0 到 15 的整數。")
print("接著回答每張卡上有沒有看到你的數字（y/n）。")

answer = 0

# 4 張卡，分別對應二進位權重 1, 2, 4, 8
for bit in range(4):
    card_numbers = [n for n in range(16) if (n >> bit) & 1]
    print_card(bit + 1, card_numbers)
    if ask_yes_no("有看到你的數字嗎？(y/n): "):
        answer += 1 << bit

print(f"\n你心中想的數字是：{answer}")

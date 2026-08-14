import random

armys = []


# 隨機建立 1～50 號士兵
for num in range(1, 51):
    if 32 <= num <= 36:
        tag = "blue"
    elif 37 <= num <= 39:
        tag = "green"
    else:
        tag = "red"
    score = random.randint(1, 10)
    speed = random.choice(["slow", "fast"])
    
    soldier = {"tag": tag, "score": score, "speed": speed}
    armys.append(soldier)

print(f"士兵數量：{len(armys)}")

 

# 依序印出第 31～40 號士兵
print("第 31～40 號士兵：")
for man in armys[30:40]:
    print(man)
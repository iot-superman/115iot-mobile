David=(7, 10, 5, 8, 2, 13, 8, 4, 9, 6, 11, 8, 6, 3, 7, 9, 3, 5, 1, 8, 15)
for num, score in enumerate(David, 1):
    if (score >= 7):
        print(f"第{num:2d}場: 得分 {score:2d} 分")

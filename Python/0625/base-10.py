import os

os.system('cls')

score = int(input("請輸入成績："))
if(score>=90):  # 成績90分以上，等級為A
    print("Level A")
elif(score>=80):  # 成績80~89分，等級為B
    print("Level B")
elif(score>=70):  # 成績70~79分，等級為C
    print("Level C")
elif(score>=60):  # 成績60~69分，等級為D
    print("Level D")
else:  # 成績<60分，等級為E
    print("Level E")
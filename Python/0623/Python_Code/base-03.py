import math

print("......1......")

name = "林小明"
score = 80

print(name+"的成績為"+str(score))
print(name, str(score))

print(name, str(score), sep="&", end="")
print("test")

print(name, str(score), sep="&", end="\n")
print("test")

###參數格式%
print("......2.....")
print(" ") #%s %d %f
print("%s的成績為%d"%(name,score))
print("PI=%f"%(math.pi))
print("PI=%10.3f"%(math.pi))   #總長度過10，小數為3「.」一個
print("PI=%6.0f"%(math.pi))
print(".....3......")
print("{}的成績為{}".format(name,score))
print("PI={:10.3f}".format(math.pi))
print("PI={:10.3f}".format(math.pi))
print("PI={:6.0f}".format(math.pi))

#f-String:python 3.6以後才可以使用
print("........4.......")
print(f"{name}的成績為{score}")
print(f"PI={math.pi}")
print(f"PI={math.pi:10.3f}")
print(f"PI={math.pi:.3}")   # 保留3位小數
print(f"PI={math.pi:.3f}")   #保留小數點3位

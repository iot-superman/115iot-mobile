def scope():
    global var1  # 使用global全域變數 , 讓var1在全域變數中被修改
    var1 = 1
    var2 = 2
    print(f"內部的 var1 = {var1},var2 = {var2}")

var1 = 10 # 全域變數
var2 = 20 # 全域變數
scope()
print(f"外部的 var1 = {var1},var2 = {var2}")

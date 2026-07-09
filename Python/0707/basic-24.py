def scope():
    var1 = 1 #  區域變數
    print(f"內部的 var1 = {var1},var2 = {var2}") 

var1 = 10  # 全域變數
var2 = 20  # 全域變數
scope()
print(f"外部的 var1 = {var1},var2 = {var2}")

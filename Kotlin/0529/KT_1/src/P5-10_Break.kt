//https://gemini.google.com/share/0cccba66486c

fun main() {
  continueTest()
}

fun breakTest(){
    var bk: Int
    var sum=0
    print("請輸入一個正整數(1-10)：")
    bk = readln().toIntOrNull()?:0
    for (i in 1..10){
        if (i==bk){
            println("你輸入的數字是 $bk，迴圈結束！")
            break
        }else{
            sum+=i
            println("目前迴圈的數字是 $i  ，總和是 $sum")
        }
        println("目前迴圈的數字是 $i")
    }
}

fun continueTest(){
    var bk: Int
    var sum=0
    print("請輸入一個正整數(1-10)：")
    bk = readln().toIntOrNull()?:0
    for (i in 1..10){
        if (i==bk){
            println("你輸入的數字是 $bk，跳過這次迴圈！")
            continue
        }else{
            sum+=i
            println("目前迴圈的數字是 $i  ，總和是 $sum")
        }
        println("目前迴圈的數字是 $i")
    }
}
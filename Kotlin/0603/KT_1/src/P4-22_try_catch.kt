fun hadtrycatch_handle(){
    print("請輸入一個整數：")
    var number: Int
    try {
        number = readln().toInt()
        println("你輸入了: $number")
    } catch (e: Exception) {
        println("請入一個有效的整數！")
    }finally {
        println("程式結束。")
    }
}

fun notTrycatch_handle(){
    print("請輸入一個整數：")
    var number: Int
    number=readln().toInt()
    println("你輸入了: $number")
}


fun main() {

hadtrycatch_handle()

notTrycatch_handle()


}

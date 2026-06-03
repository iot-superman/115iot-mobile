fun loop_test1(){
    val v: Int
    val five = 5
    val str = "11"

    println("Please input number: ")
    v = readln().toInt()

    /**
     * 展示 Kotlin 的 when 表達式與多種分支類型。
     *
     * 此 when 陳述式對整數輸入進行評估，針對各種模式匹配條件：
     * - 精確值匹配 (1, 2)
     * - 單一分支中的多個值 (3, 4)
     * - 範圍匹配 (6..10)
     * - 表達式評估 (str.toInt())
     *
     * @see when() 以獲取更多關於 Kotlin when 表達式的資訊
     */
    when (v) {
        1 -> println("a")                    // 如果 v 等於 1，列印 "a"
        2 -> println("b")                    // 如果 v 等於 2，列印 "b"
        3, 4 -> println("e")                 // 如果 v 等於 3 或 4，列印 "e"
        in 6..10 -> println("f-j")          // 如果 v 在 6 到 10 之間（含），列印 "f-j"
        str.toInt() -> println("k")          // 如果 v 等於 11（"11" 轉換為整數的結果），列印 "k"
    }

    println()
    var score: Int = 0
    print("Input score: ")
    score = readln().toInt()
    when(score){
        in 0.. 59 -> println("no pass")
        in 60 .. 100 -> println("pass")
        else -> println("input error")
    }

    val score_status = when(score){
        in 0.. 59 -> "no pass"
        in 60 .. 100 -> "pass"
        else -> "input error"
    }
    println("score_status:$score_status")

}
fun main() {
//    loop_test1()

    print("Input fruit name: ")
    var fruistName = readln()
    val price = when(fruistName.lowercase()){
        "apple"-> 100
        "pear"->150
        "pineapple"->80
        else -> 50
    }
    println("fruit:$fruistName, price:$$price")



}
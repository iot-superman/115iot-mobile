fun main() {

    // ....                           //(37.0)的()呼叫匿名函數lambda，"C2F" 是傳入的參數，(37.0) 是呼叫返回的 lambda 函數，傳入的參數是攝氏度 37.0
    var F=rtnTmpConvert("C2F")(37.0)
    println("攝氏 0 度轉華氏: $F") //輸出：攝氏 0 度轉華氏: 32.00
    var C=rtnTmpConvert("F2C")(212.0)
    println("華氏 212 度轉攝氏: $C") //輸出：華氏 212 度轉攝氏: 100.0000
    println("=========\n")

    val myGreeeting :(String)-> String=ConfigGreeting()
    var result = myGreeeting("Mary")
    println(result)

    println()
    // 問候函數 - 合併寫法
    result = ConfigGreeting()("Mary")  //ConfigGreeting() 返回一個 lambda 函數，然後立即呼叫這個 lambda 函數，傳入 "Mary" 作為參數
    println(result)

/**
 * 取得並使用一個二元整數運算的 lambda。
 *
 * - `cal` 的型別為 (Int, Int) -> Int，代表接受兩個 Int 並回傳 Int 的函數。
 * - 透過 `numProcess('+')` 取得對應的運算 lambda；在此案例中，傳入 '+' 會回傳一個執行加法的 lambda：{ x, y -> x + y }。
 * - 之後以 `cal(10, 20)` 呼叫該 lambda，取得計算結果並印出。
 *
 * 範例替代寫法（同樣效果）： val sum = numProcess('+')(10, 20)
 */
var cal: (Int, Int) -> Int = numProcess('+') // 取得加法的 lambda
var sum = cal(10, 20)                        // 呼叫 lambda，傳入 10 和 20，結果為 30
println("sum=$sum")                          // 輸出：sum=30

    // 這裡的 lambda 是一個匿名函數，沒有名稱，直接寫在等號右邊，這樣就可以直接使用了，不需要先定義一個函數再呼叫它了
    sum=numProcess('+')(10,20)
    println("sum=$sum") //輸出：sum=30

}

/**
 * 返回一個 lambda 函數，用於在攝氏度和華氏度之間轉換溫度。
 *
 * @param caption 轉換類型："C2F" 表示攝氏度轉華氏度，"F2C" 表示華氏度轉攝氏度
 * @return 一個 lambda 函數，接受 Double 類型的溫度值並返回轉換後的溫度。
 *         如果 caption 無效，返回的 lambda 函數始終返回 -1.0
 *
 * @example
 * val c2f = rtnTmpConvert("C2F")
 * val fahrenheit = c2f(0.0)  // 返回 32.0
 *
 * val f2c = rtnTmpConvert("F2C")
 * val celsius = f2c(32.0)    // 返回 0.0
 */
fun rtnTmpConvert(caption: String): (Double) -> Double {
    when(caption) {
        "C2F" ->
            // 攝氏度轉華氏度：(C × 1.8) + 32
            return { it * 1.8 + 32 }
        "F2C" ->
            // 華氏度轉攝氏度：(F - 32) / 1.8
            return { (it - 32) / 1.8 }
        else ->
            // 無效的轉換類型
            return { -1.0 }
    }
}

fun ConfigGreeting():(String) -> String {
   val name = "hospital"
   var numBulding = 5

    return { playerName: String ->
        numBulding +=1
        println("Adding $numBulding $name")
        "Welcome to new villiage, $playerName!"
    }
}

fun  numProcess(dir: Char):(Int, Int) -> Int {
    return when(dir) {
        '+' -> { x: Int, y: Int -> x + y }
        '-' -> { x: Int, y: Int -> x - y }
        '*' -> { x: Int, y: Int -> x * y }
        else -> { x: Int, y: Int -> -1 } // 無效的運算符，返回 0
    }
}

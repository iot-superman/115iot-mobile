fun main() {
    var sum = add(8, 12)

    //自定義函數 add，將兩個整數相加並返回結果，然後在 main 函數中調用 add(8, 12) 並將結果存儲在變量 sum 中，最後打印出結果
    println("8 + 12 = $sum")

    // 匿名函數，直接定義在變量 sum1 中，使用時直接調用 sum1(8,12) 即可得到結果
    // 匿名函數是一種沒有名稱的函數，是labama表達式的一種形式
    val sum1= {a:Int,b:Int -> a+b}
    println("8 + 12 = ${sum1(8,12)}")


//    unitaddAddAB() //unit function 沒有返回值，直接調用即可，該函數會提示用戶輸入兩個整數，然後計算並打印它們的和
    C2F() //C2F 函數將攝氏溫度轉換為華氏溫度，並打印結果。該函數會提示用戶輸入攝氏溫度，然後進行計算並輸出華氏溫度。
}

fun add(a: Int,b:Int): Int{
    return a+b
}

fun  unitaddAB(): Unit{   // Unit 是 kotlin 中表示沒有返回值的類型，類似於 Java 中的 void
    var a:Int = 0
    var b:Int = 0
    print("input a: ")
    a =readLine()?.toIntOrNull()?:0
    print("input b: ")
    b =readLine()?.toIntOrNull()?:0
    println("a + b = ${a + b}")
}

fun  C2F(){
var c:Float
var f:Float
print("Input temp(C): ")
c= readLine()?.toFloatOrNull() ?: 0F
f = c * 9/5 + 32
println("Temp in Fahrenheit: $f F")
}
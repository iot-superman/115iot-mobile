// =====================================================
// student 函式
// 包含成員：
// 1. name: String
// 2. id: Int
// 3. 匿名函數 score: (Int, Int, Int) -> Int
// =====================================================
fun student(name: String, id: Int, score: (Int, Int, Int) -> Int) {

    // 1. 使用 readln() 輸入成績
    print("請輸入 $name 的數學成績：")
    val math = readln().toInt()

    print("請輸入 $name 的英文成績：")
    val english = readln().toInt()

    print("請輸入 $name 的自然成績：")
    val science = readln().toInt()

    // 3. 使用 Lambda 計算結果
    val result = score(math, english, science)

    // 2. 列印學生資料與成績結果
    println()
    println("===== 學生資料 =====")
    println("姓名：$name")
    println("學號：$id")
    println("成績結果：$result") // 直接印出 Lambda 運算後的結果
    println("====================\n")
}

fun main() {

    // =====================================================
    // 寫二個 Lambda 函式
    // =====================================================

    // 計算總分的 Lambda
    val sumLambda: (Int, Int, Int) -> Int = { math, english, science ->
        math + english + science
    }

    // 計算平均的 Lambda (根據定義回傳 Int)
    val avgLambda: (Int, Int, Int) -> Int = { math, english, science ->
        (math + english + science) / 3
    }

    // =====================================================
    // 主程式呼叫二次 student 函數
    // =====================================================

    // name: Jack, id: 10，使用總分 Lambda
    println("開始輸入 Jack 的成績（將計算總分）...")
    student("Jack", 10 , sumLambda)

    // name: Mary, id: 20，使用平均 Lambda
    println("開始輸入 Mary 的成績（將計算平均）...")
    student("Mary", 20, avgLambda)
}
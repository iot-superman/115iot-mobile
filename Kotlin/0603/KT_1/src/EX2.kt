/*
## 題目 1：計算學生平均成績

定義一個 function，
輸入學生成績後，
印出學生姓名與平均成績

*/

/*
## 題目 2：
定義一個 名稱為drunkStatus 單運算式function，輸入參數為整數的醉酒值，根據下表回傳檢查醉酒狀態
給3個不同的值value , 使用when 回傳每一個狀態

醉酒值    	醉酒狀態
1-10		tipsy
11-20		sloshed
21-30		soused
31-40		stewed
41-50		..t0aSt3d
 */


fun main() {


    // =====================================
    // 成績變數
    // =====================================
    var math: Float
    var english: Float
    var science: Float

    // =====================================
    // 建立 3 位學生
    // =====================================
    for (i in 1..3) {

        // =====================================
        // 輸入姓名
        // =====================================
        print("請輸入學生${i}姓名: ")
        val name = readln()

        // =====================================
        // 輸入數學成績
        // =====================================
        print("請輸入學生${i}數學成績: ")
        math = readln().toFloat()

        // =====================================
        // 輸入英文成績
        // =====================================
        print("請輸入學生${i}英文成績: ")
        english = readln().toFloat()

        // =====================================
        // 輸入科學成績
        // =====================================
        print("請輸入學生${i}科學成績: ")
        science = readln().toFloat()

        // =====================================
        // 顯示平均成績
        // =====================================
        println(
            "學生姓名: $name, 平均成績: ${
                getAverage(math, english, science)
            }\n"
        )
    }

/*
    //2
    print("請輸入醉酒值: ")
    val drunkValue = readln().toInt()
    println("醉酒狀態: ${drunkStatus(drunkValue)}")
 */

}

/**
 * =====================================
 * 計算平均成績
 * =====================================
 */
fun getAverage(
    math: Float,
    english: Float,
    science: Float
): Float {

    return (math + english + science) / 3.0f
}


fun drunkStatus(value: Int): String {
    return when (value) {
        in 1..10 -> "tipsy"
        in 11..20 -> "sloshed"
        in 21..30 -> "soused"
        in 31..40 -> "stewed"
        in 41..50 -> "..t0aSt3d"
        else -> "Invalid drunk value"
    }
}
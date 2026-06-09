fun main() {
    println("==========================================")
    println("=== 第一題：計算學生平均成績                ===")
    println("==========================================")
    F1_calStudentAverage()

    println("\n==========================================")
    println("=== 第二題：過濾偶數並平方                  ===")
    println("==========================================")
    F2_filterAndSquare()
}

/*
定義一個 studentMap function, 輸入以下成員: 列印每位學生的姓名總分和平均分數, 使用 Map 回傳學生資料
姓名 (name: String)
成績 (scores: List<Int>)
return map(name : xxx , sum : xxx , average : xx)
在主程式建立 3 個學生的 map 資料, 並列印學生資料
*/

// 定義 studentMap 函數
fun studentMap(name: String, scores: List<Int>): Map<String, Any> {
    val sum = scores.sum()
    val average = if (scores.isNotEmpty()) scores.average() else 0.0

    return mapOf(
        "name" to name,
        "sum" to sum,
        "average" to average
    )
}

fun F1_calStudentAverage() {

    // 用來儲存 3 個學生資料的清單
    val studentsList = mutableListOf<Map<String, Any>>()

    // 使用迴圈讓使用者連續輸入 3 位學生的資料
    for (i in 1..3) {
        println("\n--- 請輸入第 $i 位學生的資料 ---")

        // 1. 讀取姓名
        print("請輸入學生姓名: ")
        val name = readln().trim()

        // 2. 逐科讀取成績
        val scores = mutableListOf<Int>()

        println("（提示：請輸入 3 科分數）")
        var count = 1

        while (count <= 3) {

            print("請輸入第 $count 科分數: ")

            val score = readln().toIntOrNull()

            if (score != null) {
                scores.add(score)
                count++
            } else {
                println("輸入錯誤，請重新輸入")
            }
        }

        // 3. 呼叫 studentMap 取得 Map 資料，並加進清單中
        val studentData = studentMap(name, scores)
        studentsList.add(studentData)
    }

    // --- 列印所有學生資料 ---
    println("\n================ 學生總結資料 ================")
    for (student in studentsList) {
        val avgFormatted = String.format("%.2f", student["average"] as Double)
        println("姓名: ${student["name"]}\t 總分: ${student["sum"]}\t 平均: $avgFormatted")
    }
}

/*
## 題目 2: 過濾偶數並平方 (使用 filter + map)
*/
fun F2_filterAndSquare() {
    fun math(data: List<Int>) {
        val filter:(List<Int>) -> List<Int> =
            { list -> list.filter { it % 2 == 0 } }   //過濾偶數

        val square = fun(list: List<Int>): List<Int> {  //平方
            return list.map { it * it }
        }

        val filteredData = filter(data)
        val squaredData = square(filteredData)

        println(squaredData)
    }

    val data = listOf(1, 2, 3, 4, 5, 6)
    println("範例輸入: $data")
    print("範例輸出: ")
    math(data)
}
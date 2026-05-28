/**
 * Kotlin Null Safety 練習
 */

fun main() {

    // =========================
    // 字串
    // =========================
    var str: String = ""

    // =========================
    // 取得第一個字元
    // 型態：Char?
    // =========================
    var ch1: Char? = str.firstOrNull()

    // =========================
    // 如果為 null
    // 使用 -1
    // 型態：Any
    // =========================
    var ch2: Any = str.firstOrNull() ?: -1


    var str3 = "     Hello  World  !!!!!  "
    println(str3.trim())  // 去除字串前後的空白

    // =========================
    // 顯示結果
    // =========================
    println(ch1)

    println(ch2)
}
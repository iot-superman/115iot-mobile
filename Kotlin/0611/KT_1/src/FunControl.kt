import java.io.File

fun main() {

    val myFile = File("my_file.txt")
    myFile.setReadable(true)  //設定可讀
    myFile.setWritable(true)  //設定可寫
    myFile.setExecutable(true)  //設定可執行

    myFile.apply {
        setReadable(true)
        setWritable(true)
        setExecutable(false)
    }

    val path = myFile.absolutePath
    println("path  = $path")

    myFile.writeText("Hello, Kotlin!\n")
    myFile.apply {
        appendText("This is Kotlin class.")
        appendText("It is rainy.")
    }

    // ADDED: 'var' declaration for lst
    //                           0  1   2   3   4
    var lst = mutableListOf<Int>(91, 92, 93, 94, 95)
    println(lst)
    lst.set(2, 100)
    println(lst)
    lst.remove(92)
    println(lst)

    lst = mutableListOf<Int>(91, 92, 93, 94, 95)
    lst.apply {
        set(2, 100)  //replace index 2
        remove(92)
        this.add(0, 50) //insert to index 0
    }
    println(lst)

    //-----------------let ----------
    println()
    lst = mutableListOf<Int>(91, 92, 93, 94, 95)
    println(lst)
    var avg = 0.0
    lst.add(5, 99)
    println(lst)
    avg = lst.average()
    println("avg= $avg")

    println()
    lst = mutableListOf<Int>(91, 92, 93, 94, 95)

    avg = lst.let { number: MutableList<Int> ->
        number.add(5, 99)
        number.average()
    }
    println(lst)
    println("avg= $avg")

    // FIXED: Corrected variable name typo from latSquare to lastSquare (or kept as latSquare)
    var latSquare = lst.let {
        val i = (0..5).shuffled().first()
        println("i= $i ,value=${it[i]}")
        it.get(i) * it.get(i)
    }

    //P10-6
    //Teacher:
    lst.also {
        it.add(5, 99)
        println("avg= ${it.average()}")
    }
    println()

    //Book:
    println(lst)
    var v = lst.also {
        it.add(5, 99)
        it.average()
    }
    println(v)


    val file = File("my_file2.txt")

    //---also----
    println()
    lst = mutableListOf<Int>(91, 92, 93, 94, 95)
    println(lst)
    lst.also {
        it.add(5, 99)
        println("avg=${it.average()}")
    }
    println(lst)

    // Safely write to file so run() doesn't crash later
    file.writeText("")
    file.also { it: File ->
        it.setWritable(true)
        it.writeText("It is also function.\n")
        it.appendText("It is a file.")
    }

    //----------  run ------------
    //P10-7  (10.5)
    println()
    lst = mutableListOf<Int>(91, 92, 93, 94, 95)
    println(lst)
    var num1 = lst.run {
        println(lst)
        // FIXED: Changed min() to minOrNull()
        lst.minOrNull()?.toDouble() ?: 0.0
    }
    println("min = $num1")

    // ADDED: 'var' declaration for check
    var check = lst.run {
        this.contains(100)
    }
    println("check =$check")

    var data = file.run {
        readText()
    }
    println(data)

// ------- with ---------
    println()
    lst = mutableListOf<Int>(91, 92, 93, 94, 95)
    println(lst)
    val num2 = with(lst) {
        // FIXED: Changed max() to maxOrNull()
        maxOrNull()
    }
    println()
    println("max = $num2")

    val data1 = "Hello world"
    println()
    println(data1)
    check = with(data1) {
        this.uppercase()
        this.length > 20
    }
    println(data1)
    println(check)

    val newData = with(data1) {      //回寫到newData
        this.uppercase()
    }
    println(newData)

    println("--------")


    // FIXED: Removed 'var' here because 'lst' was already declared above
    lst = mutableListOf<Int>(91, 92, 93, 94, 95)

    println(lst)

    val d = lst.run {
        // FIXED: Changed min() to minOrNull() and handled the nullable Int
        minOrNull() ?: 0
    }.run(::isLarger)  //::是呼叫函式的語法，等同於isLarger(it)

    println(d)

    println("----------")
    myFun("Mary", ::inputNumber)
    println()
    myFun("John") { data ->
        data*10
    }

    println("======")
/**
 * 此段程式碼會：根據清單 `lst` 的最小值是否小於 20 決定是否移除該最小值，最後印出結果（若有變動的話）。
 *
 * 行為步驟（高階說明）：
 * 1. 使用 `takeIf { predicate }`：若 predicate 回傳 true，回傳原本的 `lst`，否則回傳 null。
 * 2. predicate 內會印出目前 `lst` 的最小值，並檢查最小值是否小於 20。
 * 3. 若 `takeIf` 傳回非 null，接著透過 `also { ... }` 執行副作用：從清單中移除目前的最小值。
 * 4. 最後使用 `.run(::println)` 在非 null 的情況下把清單印出來；若為 null 則不做任何事（因為安全呼叫）.
 *
 * 注意事項（重要）：
 * - Kotlin 新版建議使用 `minOrNull()` 取代 `min()`，因為 `min()` 可能已被棄用；且 `minOrNull()` 對於空集合會回傳 null，
 *   使用時應妥善處理空集合情況以免產生 NullPointerException 或未預期行為。
 * - 此處多次呼叫 `min()`（或 `minOrNull()`）會重複計算最小值；在單一執行緒小型範例中沒問題，但若有並發修改或性能考量
 *   建議先將最小值儲存在變數中再使用，避免競態條件或不一致的結果。
 * - `also` 裡的 `it.remove(...)` 會移除第一個相等的元素（依 equals 判定）；若清單中有多個相同最小值，只有第一個會被移除。
 * - `it` 與 `lst` 在這個上下文是同一個物件（alias），因此 `it.min()` 與 `lst.min()` 等價；但為了可讀性建議一致地使用 `it` 或先捕捉值。
 *
 * 範例註解（逐行說明）：
 */
lst.takeIf {
    // 印出目前 lst 的最小值（用於除錯或觀察）
    // 注意：實務上建議改用 lst.minOrNull() 並處理 null（如果 lst 有可能為空）
    println("min =${lst.min()}")

    // predicate：回傳 true 當前最小值小於 20（it 代表傳入的 lst）
    it.min() < 20
}?.also {
    // 若 predicate 為 true，also 會接收該清單（it），並在此執行副作用：
    // 從清單中移除目前的最小值（此處再次呼叫 lst.min()，與 it.min() 等價，但會重複計算）
    it.remove(lst.min())
}.run(::println) // 如果前面結果非 null（代表剛才有移除），使用 run 將清單印出；若為 null 則不會呼叫 println。


}

// -------
//這個要自己寫
fun isLarger(value: Int): Boolean {
    return value > 20
}
fun  inputNumber(number: Int): Int{
    return number * number
}

fun myFun(name: String,process: (Int)->Int)
{
    println("name is $name")
    val number = (1..10).shuffled().first()
    println("number= $number")
    println("new numbe = ${process(number)}")
}
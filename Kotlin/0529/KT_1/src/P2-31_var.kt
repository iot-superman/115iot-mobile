fun main() {
    var r=2..8
    //forEach 本質上是一個 Lambda 函式，可以接受一個參數，這裡的 it 就是這個參數，代表目前正在處理的元素，
    // 也就是 r 中的每一個數字，這個 Lambda 的回傳值是 Unit，也就是沒有回傳值，
    // 因為 forEach 只是用來執行一些操作，而不是用來計算結果的，所以它不需要回傳值
    r.forEach {


        if (it %2 == 0 )
            println(it)
        else
            println("$it is odd")

    }

    var data: String = "Mississippi"
    var count = data.count()
    println("all count= $count")

// 使用 count() 函式搭配 Lambda 表達式，計算字符串中特定字符 's' 出現的次數
// Lambda 參數 letter 代表字符串中的每一個字符，條件 letter == 's' 用來匹配目標字符
// count() 會回傳滿足條件的字符數量
count = data.count { letter -> letter == 's' }

// 列印字符串及其中 's' 字符的出現次數
// 輸出格式：「Mississippi count of 'i'= 4」
println("$data count of 'i'= $count")


}
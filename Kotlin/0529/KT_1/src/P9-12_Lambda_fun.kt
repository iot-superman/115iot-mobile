//Teacher fun4
fun main() {
    // Lambda 是一種匿名函式，也就是沒有名稱的函式，可以直接在需要函式的地方定義和使用，這樣就可以讓程式碼更簡潔和靈活
    // Lambda 的語法是 { 參數列表 -> 函式體 }，其中參數列表是可選的，
    // 如果 Lambda 沒有參數就可以省略，函式體是 Lambda 的內容，可以是一行或多行的程式碼，最後一行的結果會被當作 Lambda 的回傳值
    // Lambda 可以被賦值給變數，也可以作為參數傳遞給其他函式，
    // 這樣就可以實現高階函式的功能，也就是函式可以接受其他函式作為參數，或者回傳其他函式作為結果，這樣就可以讓程式碼更具有彈性和可重用性

    //寫法1:
    var result = bill(5) { number ->
        number * 10   //numver *10 是一個 Lambda 的內容，這裡的 number 是 Lambda 的參數，這個 Lambda 的回傳值是 number * 10 的結果，也就是 50
    }
    println(result)

    //寫法2:
    // 這裡的 price 是 Lambda 的參數，這個 Lambda 的回傳值是 price * 2 的結果，也就是 20
    val  total1=bill(10) { price: Int ->  price * 2}  //Lambda 的參數類型可在最後才可以用這種寫法，因為 Lambda 的參數類型可以從 bill 函式的定義中推斷出來，所以不需要在 Lambda 的參數列表中指定類型了



    var cal1: (Int) -> Int = { it * 23 }
     result = bill(10,cal1)
    println("result: $result")
    //這裡的 price 是 Lambda 的參數,可以ｏverride掉之前的 Lambda 的參數名稱,可以類似類別override一樣,這個 Lambda 的回傳值是 price * 2 的結果，也就是 2
    cal1={price:Int->price*2}
    result = bill(10,cal1)
    println("result: $result")



    //寫法1:
    val F=tmpConvert(37.5, convertor = { c:Double -> 1.8*c+32 })
    println("華氏溫度: $F")


    //這裡的 it 是 Lambda 的參數，這個 Lambda 的回傳值是 1.8*it+32 的結果，也就是 99.5
    //寫法2:
    val F1=tmpConvert(37.5){1.8*it+32}
    println("華氏溫度: $F1")



    /**
 * 使用 Lambda 表達式將華氏溫度轉換為攝氏溫度。
 *
 * 寫法1：將 Lambda 作為具名參數傳遞給 tmpConvert 函式
 * 這裡的 c 是 Lambda 的參數，這個 Lambda 的回傳值是 (c-32)/1.8 的結果，也就是 37.7
 */
    val C = tmpConvert(100.0, convertor = { c: Double -> (c - 32) / 1.8 })
    println("攝氏溫度: $C")

    //寫法2:使用 it 來簡化 Lambda 的寫法，當 Lambda 只有一個參數時，可以使用 it 來代表這個參數，這樣就不需要再寫一個變數名稱了
    //這裡的 it 是 Lambda 的參數，這個 Lambda 的回傳值是 (it-32)/1.8 的結果，也就是 37.7
    val C1=tmpConvert(100.0){(it-32)/1.8}
    println("攝氏溫度: $C1")
}




 //表示這個 Lambda 接受一個 Int 類型的參數並回傳一個 Int 類型的結果，這裡的 calculate 就是這個 Lambda 的參數名稱，可以在 Lambda 的內容中使用它來代表這個 Lambda 的參數，這樣就可以讓 bill 函式更具有彈性和可重用性，因為我們可以傳遞不同的 Lambda 來實現不同的計算邏輯，而不需要修改 bill 函式的內容
                               //(Int)->Int 是 Lambda 的類型(匿名函式的類型)
fun bill(number: Int, calculate: (Int)->Int): Int {
    var total:Int = 0
    total = calculate(number)
    return total
}

fun tmpConvert(temp: Double,convertor:(Double)->Double): Double {
    val temp=convertor(temp)
    return temp
}

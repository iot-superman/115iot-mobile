fun main() {

    // 先給預設值
    var mathOpt: () -> Unit

    var sel: Int? = 0

    println("請輸入選項 '0' 執行加法, '1' 執行減法")
    /*
    //方法1:
    // 使用 readln() 來讀取使用者輸入的字串，然後使用 toIntOrNull() 來將字串轉換成整數，
    //如果轉換失敗就會回傳 null，這樣就可以避免使用者輸入非數字的字串而導致程式崩潰的問題
    sel = readln().toIntOrNull()
     */
    //方法2:
    //使用 readLin()? 來讀取使用者輸入的字串，然後使用 toIntOrNull() ，再用Elvis運算子 ?:
    //來給 sel 一個預設值，如果轉換失敗就會回傳 null，這樣就可以避免使用者輸入非數字的字串而導致程式崩潰的問題
    sel = readLine()?.toIntOrNull()?:0


    // 根據使用者輸入的選項來決定 Lambda 的內容,這裡使用了 if-else 來決定 mathOpt 的內容 ，
    // 如果 sel 是 0 就執行加法的 Lambda，否則就執行減法的 Lambda
    // sel 一定有值，因為我們在前面已經給了它預設值， ** mathOpt才不會出現未初始化的錯誤 **
    if (sel == 0) {

        mathOpt = {

            println("1 + 2")
        }

    } else   {

        mathOpt = {

            println("1 - 2")
        }

    }
    //寫法1:
    // inch to cm lambda
    val inch2cm:(Double)-> Double={inch ->
        inch*2.54
    }

    println(inch2cm(2.0))

    //寫法2:
    // 使用 it 來簡化 Lambda 的寫法，當 Lambda 只有一個參數時，可以使用 it 來代表這個參數，這樣就不需要再寫一個變數名稱了
    val inch2cm_1 = {it: Double ->   //it 還是要指定類型，因為 Lambda 沒有參數名稱了，所以需要指定 it 的類型
        it*2.54
    }

    // 執行 Lambda
    mathOpt()



}
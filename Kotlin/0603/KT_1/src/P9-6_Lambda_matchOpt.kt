fun main() {

    // 先給預設值
    var mathOpt: () -> Unit

    var sel: Int? = 0

    println("請輸入選項 '0' 執行加法, '1' 執行減法")

    sel = readln().toIntOrNull()


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
    // 執行 Lambda
    mathOpt()
}
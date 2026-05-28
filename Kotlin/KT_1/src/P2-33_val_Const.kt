

//const 只可以全局範圍內聲明，不能在函數內部聲明
const val GREEN_COLOR ="GREEN"
const val NONE_COLOR = "NONE"

fun main() {

    var point = 100
    val color = if (point>80) GREEN_COLOR else NONE_COLOR

    //${變數} 可以在字串中直接使用變數的值
    println("color = ${color}")

    point = 50
    println("color = ${if (point>80) GREEN_COLOR else NONE_COLOR}")

    /*
     val color2 = if(條件) 常量1 else 常量2
     這裡的常量1和常量2必須是編譯時常量，這意味著它們的值在編譯期間就已經確定了，不能依賴於運行時的變量或函數調用。
     因此，color2的值在編譯期間就已經確定了，無法根據point的值動態變化。
     
     */

}
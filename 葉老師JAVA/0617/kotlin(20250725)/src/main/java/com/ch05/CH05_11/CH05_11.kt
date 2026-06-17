package com.ch05.CH05_11
//補充
class Toast(val name: String, val age: Int) {
    companion object {
        fun makeText(name: String, age: Int): Toast {
            return Toast(name, age)
        }
    }

    fun show() {
        println("name=$name; age=$age")
    }
}

fun main() {
    Toast.makeText("bill", 30).show()

    // 不用建立變數也能呼叫
    val t = Toast.makeText("bill", 30)
    t.show()
}

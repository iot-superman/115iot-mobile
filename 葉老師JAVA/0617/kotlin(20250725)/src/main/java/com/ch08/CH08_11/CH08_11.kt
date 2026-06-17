package com.ch08.CH08_11
// Android常用方式，當方法有要傳入含有物件成份的要求時
// 定義函數式介面
fun interface Animal {
    fun running(x:Int,y:Int)
}
// 類別 Cat，接收 Animal 介面參數
class Cat {
    fun showMe(obj: Animal) {
        println("showMe.......")
        obj.running(2,4)
    }
}
fun main() {
    val c1 = Cat()
    //寫在主程式內
    println(".........1.........")
    c1.showMe(object : Animal {
        override fun running(x: Int, y: Int) {
            println("showMe1:x={$x},y={$y}")
        }
    })
    println(".........2.........")
    //不推薦
    c1.showMe(fun(x: Int, y: Int) {
        println("showMe2:x={$x},y={$y}")
    })
    println(".........3.........")
    c1.showMe{x, y -> println("showMe3:x={$x},y={$y}")}
    //////////////////////////////////////////////////////
    //寫在主程式外
    println(".........4.........")
    c1.showMe(temp4)//將物件設定成新的屬性
    c1.showMe(temp5)//將物件設定成新的屬性
    //////////////////////////////////////////////////////
    println(".........5.........")
    c1.showMe(temp6())//設定新的類別
//
    println(".........6.........")
    c1.showMe(temp7())//設定新的function再回傳
}

// 匿名物件
private val temp4 = object :Animal {
    override fun running(x: Int, y: Int) {
        println("showMe4:x={$x},y={$y}")
    }
}
// Lambda 表達式
private val temp5 = Animal{
    x,y->
    println("showMe5:x={$x},y={$y}")
}

private class temp6:Animal{
    override fun running(x: Int, y: Int) {
        println("showMe5:x={$x},y={$y}")
    }
}

fun temp7():Animal{
    return Animal{
        x,y->
        println("showMe6:x={$x},y={$y}")
    }
}
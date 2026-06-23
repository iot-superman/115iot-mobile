package com.ch08.CH08_10
// Android常用方式，當方法有要傳入含有物件成份的要求時
// 定義函數式介面
fun interface Animal {
    fun running()
}

// 類別 Cat，接收 Animal 介面參數
class Cat {
    fun showMe(obj: Animal) {
        println("showMe.......")
        obj.running()
    }
}

// 原始實作方式
class Temp : Animal {
    override fun running() {
        println("running(temp).......")
    }
}

fun main() {
    println("......1.......")
    val c1 = Cat()
    val t = Temp()
    c1.showMe(t)
    c1.showMe(Temp()) // 傳入匿名物件（Kotlin 其實一樣）

    println("......2.......")
    val c2 = Cat()
    val obj: Animal = Temp() // 向上轉型為介面型別
    c2.showMe(obj)

    println("......3.......")
    val c3 = Cat()
    // 匿名類別方式1
    c3.showMe(object : Animal {
        override fun running() {
            println("running(cat3-1).......")
        }
    })
    // 匿名類別方式2
    c3.showMe( fun() {
            println("running(cat3-2).......")
    })

    // Lambda 寫法（fun interface 支援）
    println("......4.......")
    c3.showMe {
        println("running(cat4-1).......")
    }
    c3.showMe{ ->
        println("running(cat4-2).......")
    }
    println("......5.......")
    // 將傳入物件當作屬性
    c3.showMe(Temp2)
    c3.showMe(Temp3)

    // 新增function回傳為物件的方式
    println("......6..........")
    c3.showMe(temp4())
}
//匿名
private val Temp2 = object:Animal {
    override fun running() {
        println("running(cat5-1).......")
    }
}
//lambda
private val Temp3 = Animal {
    println("running(cat5-2).......")
}

// function名稱為Temp2，回傳值為Animal物件temp4(): Animal {
fun temp4(): Animal {
    return Animal{
        println("running(cat4).......")
    }
}

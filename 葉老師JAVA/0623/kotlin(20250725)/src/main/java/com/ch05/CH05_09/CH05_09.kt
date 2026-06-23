package com.ch05.CH05_09
// static 屬性
// 在kotlin 沒有真正的 static 關鍵字，可用companion object取代
class NBAteam {
    var id: Int = 0         // 成員ID
    var name: String? = null // 成員姓名
    init {
        id = ++counter
    }
    fun output() {
        println("id: $id  Name: $name")
        println("共有 $counter 名成員")
    }
    companion object {
        var counter: Int = 0 // 所有成員共享的靜態變數
    }
}
//or
class NBAteam2(var name: String) {
    val id = ++counter
    fun output() {
        println("id: $id  Name: $name")
        println("共有 $counter 名成員")
    }
    companion object {
        var counter = 0
    }
}

fun main() {
    // 在 Kotlin 中 companion object 會在第一次存取時初始化
    println(NBAteam.counter)
    val t1 = NBAteam()
    t1.name = "Durant"
    t1.output()
    val t2 = NBAteam()
    t2.name = "Curry"
    t2.output()
    ////////////////////////////////////////////
    println("........................")
    println(NBAteam2.counter)
    val obj1 = NBAteam2("Durant")
    obj1.output()
    val obj2 = NBAteam2("Curry")
    obj2.output()
}




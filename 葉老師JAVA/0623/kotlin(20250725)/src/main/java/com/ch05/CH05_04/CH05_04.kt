package com.ch05.CH05_04
// this()，呼叫另一個建構方法說明
class NBAPlayers{
    var age: Int = 28  // 預設年齡
    var name: String   // 姓名必須初始化
    constructor(name: String) {
//        this.name = name
         this.name = "$name 先生" // 可啟用這行加上稱呼
    }
    constructor(name: String, age: Int) : this(name) {
        this.age = age
//         this.name = "$name 先生"
    }
    fun printInfo() {
        println(name)
        println(age)
    }
}
// or
class NBAPlayers2(name: String, var age: Int = 28) {
    var name: String = "$name 先生"
    fun printInfo() {
        println(name)
        println(age)
    }
}

fun main() {
    val obj1 = NBAPlayers("LeBron James", 30)
    obj1.printInfo()

    val obj2 = NBAPlayers2("LeBron James", 30)
    obj2.printInfo()
}

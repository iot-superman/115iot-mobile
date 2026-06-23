package com.ch08.CH08_03

// 介面 + default method
interface Vehicle {
    fun getBrand(): String
    fun run(): String

    fun alarmOn(): String {
        return "開啟警告燈"
    }

    fun alarmOff(): String {
        return "關閉警告燈"
    }
}

// Car 類別實作 Vehicle
class Car(private val brand: String) : Vehicle {

    override fun getBrand(): String {
        return brand
    }

    override fun run(): String {
        return "安全駕駛中 ... "
    }

    // 覆寫 alarmOff（遮蔽介面預設實作）
    override fun alarmOff(): String {
        return "自已要關閉警告燈"
    }
}

fun main() {
    val c = Car("TOYOTA")
    println(c.getBrand())
    println(c.run())
    println(c.alarmOn())
    println(c.alarmOff())
}

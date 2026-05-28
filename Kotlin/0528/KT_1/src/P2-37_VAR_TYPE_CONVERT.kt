import  kotlin.jvm.java
fun main() {
    var num: Double = 2.0
    var num1 : Double = num + 1

    println("num1 type is:" + num1::class.java.simpleName)

    var str ="64"
    var num2 : Int
    num2 = str.toInt() + 36
    println("num2=$num2")

    var name ="Mary"

    //toIntOrNull()方法尝试将字符串转换为整数，如果转换成功则返回整数值，否则返回null
    var a=name.toIntOrNull()


    println("a=$a")
}
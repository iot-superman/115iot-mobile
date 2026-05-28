fun main() {
    // 在Kotlin中，分號; 可以省略
    println("Hello, World!")
    println("This is kotlin class")
    println("早安")
   //copy src/Test_2.kt to other computer and run it

    var price :Int = 10
    var name:String ="王小明"
    var age:Int =  18
    var weight  = 50.67
    val address ="Taipei"
    val code: Int


    println("price:" + price )  // + 是连接符，可以省略
    println("name: $name    ")  // $ 是字符串模板，可以直接在字符串中使用变量
    println("age: $age    ")
    println("weight: $weight    ")
    println("address: ${address}   ")  //{}可以省略，如果变量名和字符串中的其他字符没有冲突，可以直接使用 $address
    //address="台北市"    //val 不可修改的常量，不能修改
  //  println("address: $address   ")
    code = 12345678   //let code: Int 只能赋值一次，不能修改
    println(code)
    //code=3   //第二次赋值会报错，val 只能赋值一次
    println(code)

}
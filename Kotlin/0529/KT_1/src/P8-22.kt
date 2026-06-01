var a=4

fun main() {
    var a1=10

    var b =2


    var b1=10
    if (b1==10){
        var c=0
        c=1+a1+b1
        println("c=$c")
    }
    Func1()

    val data= getGreeting("Mary")
    println(data)
    println()
    val stauts = isEven(20)
    println(stauts)
}

fun  Func1()
{
    var a=6
    println("Fnuc1 a=$a")
}

fun  getGreeting(name: String)= "Hello $name"

fun isEven(number:Int) = number %2 ==0


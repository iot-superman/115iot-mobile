abstract  class  Base
{
    abstract var a: Int
    var b=0

    abstract  fun fun1()

    fun  fun2(){
        println("From base class")
    }
}

class  Derived: Base()
{
    override var a: Int=0

    override fun fun1() {
        println("From derived class")
    }
}

fun main() {
    var myClass = Derived()

    println("a=${myClass.a}")
    println("b=${myClass.b}")
    println(myClass.fun1())
    println(myClass.fun2())
}
//==============================
// Generic Function
//==============================
fun <T> printItem(item: T) {
    println("Item: $item")
}

fun <T1, T2> myFun(p1: T1, p2: T2) {
    println("p1 = $p1")
    println("p2 = $p2")
    println()
}

//==============================
// Generic Constraint
//==============================
fun <T : Comparable<T>> compare(t1: T, t2: T) {

    when {
        t1 > t2 -> println("t1 is bigger")
        t1 < t2 -> println("t2 is bigger")
        else -> println("t1 = t2")
    }
}

//==============================
// Multiple Constraints
//==============================
fun <T> compare1(p1: T, p2: T): String
        where T : Number, T : Comparable<T> {

    return when {
        p1 > p2 -> "$p1 > $p2"
        p1 < p2 -> "$p1 < $p2"
        else -> "$p1 = $p2"
    }
}

//==============================
// Generic Class
//==============================
class Rect<T : Number>(
    h: T,
    w: T
) {

    var width: T = w
    var height: T = h

    // Number 可以直接呼叫 toDouble()
    fun area(): Double {
        return width.toDouble() * height.toDouble()
    }
}

//==============================
// Generic Interface
//==============================
interface Process<T> {
    fun process(item: T)
}

//==============================
// Generic Interface Example 1
//==============================
class MyPrint : Process<String> {

    override fun process(item: String) {
        println("Item value: $item")
    }
}

//==============================
// Data Class
//==============================
data class Data(
    val name: String,
    val id: Int
)

//==============================
// Generic Interface Example 2
//==============================
class MyDataProcessor : Process<Data> {

    override fun process(item: Data) {
        println("name: ${item.name}")
        println("id  : ${item.id}")
    }
}

//==============================
// Main
//==============================
fun main() {

    println("========== Generic Interface ==========")


    MyPrint().process("Mary")
    val myPrint = MyPrint()
    myPrint.process("John")

    println()

    val myData = MyDataProcessor()

    myData.process(
        Data(
            "Amy",
            1001
        )
    )

    println()

    println("========== Generic Function ==========")

    printItem("Mary")
    printItem(100)
    printItem(true)
    printItem(123.456)

    println()

    println("========== Multiple Generic ==========")

    myFun(100, 200)
    myFun("Mary", 20)
    myFun(false, "John")
    myFun(3.14, true)

    println()

    println("========== Generic Constraint ==========")

    compare(50, 20)
    compare("Mary", "John")
    compare(12.155, 12.156)

    println()

    println("========== Multiple Constraint ==========")

    println(compare1(12, 56))
    println(compare1(50, 20))
    println(compare1(12.5, 12.5))

    println()

    println("========== Generic Class ==========")

    val rect1 = Rect(10.2, 20.1)
    println("Rectangle 1 Area = ${rect1.area()}")

    val rect2 = Rect(10, 20)
    println("Rectangle 2 Area = ${rect2.area()}")

    val rect3 = Rect(3.5f, 8.0f)
    println("Rectangle 3 Area = ${rect3.area()}")

}
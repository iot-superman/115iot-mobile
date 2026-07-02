interface IntB {
    var math: Int
    var eng: Int

    fun show() {
        println("math = $math, eng = $eng")
    }
}

class Report(override var math: Int = 0, override var eng: Int = 0) : IntB

fun main() {
    val r = Report(95, 88)
    r.show()
}

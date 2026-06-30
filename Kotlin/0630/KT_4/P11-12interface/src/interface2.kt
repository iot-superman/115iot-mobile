interface Darwable {
    fun  draw()
}

class SimpleCircle:Darwable{
    override fun draw() {
        println("Drawing a circle")
    }
}
interface  Click{
    fun click()
}

open class Button:Darwable,Click{
    override fun draw() {
        println("Drawing a button")
    }

    override fun click() {
        println("Button clicked")
    }
}

interface Shape {
    fun area(): Double
    fun perimeter(): Double
}


class  Circle(val  radius: Double): Shape {
    override fun perimeter(): Double {
        return 2 * Math.PI * radius
    }
    override fun area(): Double {
        return Math.PI * radius * radius
    }
    fun  info(){
        println("circle radius= $radius , area=${area()}, peri=${perimeter()}")
    }
}

fun main() {
    val circle = SimpleCircle()
    circle.draw()

    val button = Button()
    button.draw()
    button.click()
    val circ = Circle(3.0)
    circ.info()
}
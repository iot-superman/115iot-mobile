open class PClass(pname: String){
    var name=""
    init {
        name=pname
    }

    fun  show(){
        println("Name: $name")
    }
}

class CClass(pname: String,page:Int): PClass(pname){

    var  age=0
    init {
        age=page
    }

    fun show1(){
        show()
        println("name =$name,age=$age")
    }
}

open class PClass1(pname: String){
    open var name =""
    init {
        name = pname
    }
    open fun show(){
        println("PClass Name: $name")
    }

}
class CClass1(pname: String,age:Int): PClass1(pname){
    override var name = ""
    var age = 0
    init {
        name = pname.uppercase()
        this.age = age
    }
    override fun show(){
      super.show()
      println("CClass Name: $name, age: $age")
    }
}

//https://chatgpt.com/s/m_6a30e611b8d08191b3dfd6054696b186
open class Game(val player: String, _level: Int) {
    open var level = _level
    open val score = 60

    open fun dataPrint(){
        println("Game Player: $player, Level: $level, Score: $score")
    }
}

open class CGame(player: String, score: Int): Game(player,10) {
    override var level = 50
    override val score = 100

    init {
        level = super.level + 1
    }

    override fun dataPrint() {
        super.dataPrint()
        println("CGame Player: $player, Level: $level, Score: $score")
    }
}

// 子子類別
class CCGame(player: String, _level: Int): CGame(player, 100) {
    override var level = _level

    override fun dataPrint() {
        super.dataPrint()
        println("CCGame Player: $player, Level: $level, Score: $score")
    }

}

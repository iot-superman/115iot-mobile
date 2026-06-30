fun interface Action{
    fun execute()
}

fun  runAction(action: Action){
    println("String runAction")
    action.execute()
    println("end of runAction")
}


class DoAction: Action {

    override fun execute() {
        println("Executing DoAction!")
        action()
    }

    private fun action() {
        TODO("Not yet implemented")
    }
}

fun main() {
    runAction { object : Action {
        override fun execute() {
            println("Executing implementation!")
        }
    }}


    println()
    runAction() {  //use lambda
        println("Executing action 1")
    }

    println()
    runAction { //use lambda2
        println("Executing action 2")
    }

    println()
    val myAction = DoAction()
    myAction.execute()
    runAction(myAction)
}
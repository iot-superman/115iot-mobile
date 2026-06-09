fun main() {
    var player = PlayerClass()
    player.name="Mary"
    player.score = 100
    println("Player name: ${player.name}, Score: ${player.score}")
    player.show()


    var player1 = PlayerClass()
    player1.show()

    player1.name="John"
    player1.score = 80
    player1.show()

    player1.setData("Jack")
    player1.show()

    player1.setData("Jack",88)
    player1.show()

    var data1 = ToUpper()
    data1.str="hello , Mary"
    println(data1.str)
    data1.str=""
    println(data1.str)



}
fun main() {

    var player = PlayerClass()

    player.name = "Mary"
    player.score = 100

    println("${player.name}, score = ${player.score}")

    var player1 = PlayerClass()

    player1.show()

    player1.name = "John"
    player1.score = 80

    player1.show()

    println()

    player1.setData(newName = "Jack")
    player1.showNew()

    player1.setData(
        newName = "Jane",
        newScore = 88
    )

    player1.showNew()

    println()

    var data1 = ToUpper()

    data1.str = "hello , Mary"
    println(data1.str)

    data1.str = ""
    println(data1.str)

    println()

    var newPlayer = Player()

    var name = newPlayer.name
    var id = newPlayer.id
    var email = newPlayer.email

    println("name = $name , id = $id , email = $email")

    newPlayer.attrPrint()

    println()

    newPlayer.name = "John"
    newPlayer.id = 2

    println("name = ${newPlayer.name}")
    println("id = ${newPlayer.id}")
    println("code = ${newPlayer.code}")

    newPlayer.id =15
    id = newPlayer.id
    println("id = $id")

    newPlayer.changePhone("1111111111")
    newPlayer.attrPrint()

    var myPlayer = NewPlayer1(
        "Mary",
        18,
        true
    )

    var myPlayer1 = NewPlayer1(
        "JHhon1234567",
        18,
        true)
        myPlayer1.name ="12345678900"
        myPlayer1.attrPrint()
        myPlayer1.age = 30
        myPlayer1.attrPrint()

    println()
    var myPlayer2 = newPlayer2("John",22,90)
    myPlayer2.attrPrint()
    println()
    myPlayer2 = newPlayer2("John",30)
    myPlayer2.attrPrint()

    println()
    var myPlayer3 = newPlayer3("Bob")
    myPlayer3.attrPrint()

  println("=====")
    //https://chatgpt.com/s/m_6a30be2a7c748191896ba192a563a763
    //call 次要建構子
    var info = Info("Mary",10)
    info.dataPrint()

    val p1 = Info(
        name = "Mary",
        age = 20
    )

    // chatgpt.com/s/m_6a30bfbfeb208191a16323d1e89dad8c






    val p2 = Info(
        name = "John",
        age = 22,
        email = "john123@mail.com"
    )

    println(
        "姓名:${p2.name}, 年齡:${p2.age}, Email:${p2.email}"
    )

    val p3 = Info(
        name = "Nacy",
        age = 21,
        email = "nacy@mail.com",
        phone = "2274-5678"
    )

    println(
        "姓名:${p3.name}, 年齡:${p3.age}, Email:${p3.email}, 電話:${p3.phone}"
    )


}
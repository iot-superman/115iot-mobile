//https://chatgpt.com/s/m_6a30e7ce2bdc819196e9a24a8c0fc691

fun main() {

        //=========================================
        // CClass
        //=========================================
        var myClass = CClass("Mary", 10)

        myClass.show1()
        myClass.show()

        println()

        // Up Casting
        var cls = myClass as PClass
        cls.show()

        println()

        //=========================================
        // CClass1
        //=========================================
        var myClass1 = CClass1("John", 20)
        myClass1.show()

        println()


        //=========================================
        // CGame
        //=========================================
        var myClass2 = CGame("Jack", 88)
        myClass2.dataPrint()

    //https://chatgpt.com/s/m_6a30ec90ed148191b95dbc88c7d9b519
        println()
        println("---#1---------------------")

        // Up Casting 成 Game
        var newCls = myClass2 as Game
        newCls.dataPrint()

        println()
        println("---#2---------------------")

        // 建立 CCGame
        myClass2 = CCGame("Jane", 10)
        myClass2.dataPrint()

        println()
        println("---#3---------------------")
        newCls = myClass2 as Game        // 再次 Up Casting
        newCls.dataPrint()

println()
    //Kotlin 類別與多型
    //https://chatgpt.com/s/m_6a30ec90ed148191b95dbc88c7d9b519

        var game =Game("AAA",8)
        game.dataPrint()
        println()

        game = CGame("BBB",77)
        game.dataPrint()
        println()

        game =CCGame("CCC",2)
        game.dataPrint()

       printInfo(Game("xxxx",4))
       println()
       printInfo(CGame("yyyy",99))
       println()
       printInfo(CCGame("ZZZ",7))

    //多型與類別轉換(Polymorphism)
    println("---")
    checkClass(PClass("HHH"))
    println()
    checkClass(CClass("KKK", 11))

    //call Car class

}

fun printInfo(game: Game) {
    game.dataPrint()
}

fun checkClass(className:PClass){
    when(className){
        is CClass -> className.show1()
        is PClass -> className.show()
    }
}
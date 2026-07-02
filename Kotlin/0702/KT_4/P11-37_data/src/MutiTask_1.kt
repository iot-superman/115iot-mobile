fun  takeFun(){
    for (i in 1..5){
        println("${Char(64+i)}")
        Thread.sleep(500)
    }
}

class MyThread: Thread() {
    override fun run(){
        super.run()
        printItem("Thread start.")
        for (i  in 10..15) {
            println("Runing $i")
            sleep(100)
        }
        println("Thread end.")
    }
}
//P13-4 ~ P13-5
fun main() {
    val   thd1 = Thread{takeFun()}
    thd1.start()

    var thd2 = Thread( Runnable {
        for (i in 1..5){
            println("${i}")
            Thread.sleep(500)
        }
    } )
    thd2.start()

    var thd3= Thread {
        for (i in 1..5)
            println("${Char(0x61 + i)}")
        Thread.sleep(200)
    }.start()

    //call MyThred demo
    val thd4 = MyThread()
    thd4.start()
}
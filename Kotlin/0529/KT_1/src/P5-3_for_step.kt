fun main() {
    val v=1..10
    var sum = 0
    for (i in v step 2){
        sum+=i
    }
    println("1到10的奇數總和 = $sum")
}
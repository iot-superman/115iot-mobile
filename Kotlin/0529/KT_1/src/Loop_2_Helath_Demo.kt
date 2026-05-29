fun main() {
    print("Input health status: ")
    var isBlessed = true
    var point = readln().toInt()
    val state = when{
        point == 100-> "It is good"
        point >=90 -> "There are a few scratches"
        point >= 75->{
            if (isBlessed ) {
                "It is blessed"
            }else {
                "There are minor wounds"
            }
        }
        point>=15 -> "Look pretty hurt"
        else -> "It is aweful"
    }

    println("health status: $state")
}

fun main() {
    var v = 0.45f
    if (v in 0.3f..1.2f)
        println("v in range")  // 改為 println（加換行）
    else
        println("v is not in range")

    var v1 = "BB"
    if (v1 in "AC".."EF")
        println("v1 is in range")
    else
        println("v1 is not in range")

    val r = 2..4
    if (r.contains(8))
        println("r contains 8")
    else
        println("r does not contain 8")

}

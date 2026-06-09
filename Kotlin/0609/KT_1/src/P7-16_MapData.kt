fun main() {

    // MutableMap<String, Double>
    var mmap1: MutableMap<String, Double> =
        mutableMapOf(
            "Mary" to 62.4,
            "John" to 58.6,
            "Nacy" to 50.2
        )

    println(mmap1)

    println()

    var mmap2 = mutableMapOf(
        1 to "Book",
        2 to "Desk",
        3 to "Chair"
    )

    println(mmap2)

    // 新增資料
    mmap1.put("Jenny", 54.8)

    println(mmap1)

    // 一次新增多筆
    mmap1.putAll(
        mapOf(
            "Beet" to 60.0,
            "Mart" to 52.4
        )
    )

    println(mmap1)

    // += 新增
    mmap1 += Pair("AA", 55.1)

    mmap1 += "BB" to 66.0

    println("1:"+mmap1)

    mmap1.values.remove(50.2)
    mmap1.values.removeAll(arrayOf(55.1,66.2))
    println("2:"+mmap1)

    val re =mmap1.values.removeIf { it<53 }
    println("3:"+mmap1)
    println(re)
}
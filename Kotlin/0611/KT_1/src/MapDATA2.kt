fun main() {

    var mmap1 = mutableMapOf<String, Double>(
        "Leo" to 50.2,
        "John" to 58.6,
        "Nancy" to 62.4
    )

    println("原始資料")
    println(mmap1)

    mmap1 += Pair("AA", 55.1)
    mmap1 += "BB" to 66.2

    println()
    println("新增 AA、BB 後")
    println(mmap1)

    mmap1["Mary"] = 55.1

    println()
    println("加入 Mary")
    println(mmap1)

    mmap1 -= "Leo"
    mmap1.remove("Mary")

    println()
    println("刪除 Leo、Mary")
    println(mmap1)

    mmap1.values.remove(50.2)

    println()
    println("刪除 value=50.2")
    println(mmap1)

    mmap1.values.removeAll(arrayOf(55.1, 66.2).toList())

    println()
    println("刪除 55.1、66.2")
    println(mmap1)

    val re = mmap1.values.removeIf {
        it < 53
    }

    println()
    println("removeIf結果=$re")
    println(mmap1)

    mmap1.keys.remove("John")

    println()
    println("刪除 John")
    println(mmap1)

    println()

    var stList = listOf(
        mapOf(
            "id" to "A001",
            "name" to "Tom",
            "age" to "18",
            "email" to "tom@gmail.com"
        ),
        mapOf(
            "id" to "A002",
            "name" to "Mary",
            "age" to "20",
            "email" to "mary@gmail.com"
        )
    )

    for (item in stList) {
        val name = item["name"]
        val id = item["id"]
        val age = item["age"]
        val email = item["email"]

        println("name:$name , id:$id , age:$age , email:$email")

    }
}
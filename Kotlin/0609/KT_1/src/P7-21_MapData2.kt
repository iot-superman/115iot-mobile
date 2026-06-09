fun main() {

    // key to value
    var stu1: MutableMap<String, String> =
        mutableMapOf(
            "name" to "Mary",
            "email" to "mary@gmail.com",
            "phone" to "111111"
        )

    var stu2 = mutableMapOf(
        "name" to "John",
        "email" to "john@gmail.com",
        "phone" to "222222"
    )

    var stu3 = mutableMapOf(
        "name" to "Jack",
        "email" to "jack@gmail.com",
        "phone" to "333333"
    )

    // List 中再放 MutableMap
    var listData: MutableList<MutableMap<String, String>> =
        mutableListOf()

    // 加入資料
    listData.add(stu1)
    listData.add(stu2)
    listData.add(stu3)

    println(listData)
    var stu4 = mutableMapOf<String, String>()
    stu4["name"]="AAA"
    stu4["email"]="aaa@gmail.com"
    stu4["phone"]="44444"
    listData+=stu4
    println(listData)
    println()
    for(item in listData){
        val name=item["name"]
        val email =item["email"]
        val phone =item["phone"]
        println("Name: $name, Email: $email, Phone: $phone")
    }
    println()

    var nameArray = arrayOf("Mary", "John", "Jack", "AAA")
    var emailArray = arrayOf("mary@gmail.com", "john@gmail.com", "jack@gmail.com", "aaa@gmail.com")
    var phoneArray = arrayOf("111111", "222222", "333333", "44444")

    listData.clear()
    println(listData)

    println("AI Vesrion:")
    for (i in nameArray.indices) { //indices = 0..nameArray.size-1
        var stu = mutableMapOf<String, String>()
        stu["name"] = nameArray[i]
        stu["email"] = emailArray[i]
        stu["phone"] = phoneArray[i]
        listData.add(stu)
    }
    println("1:"+listData)


    listData.clear()
    println("老師版本:")
    for (i in 0 until nameArray.size) {
        var stu = mutableMapOf<String, String>()
        stu.put("name", nameArray[i])
        stu.put("email", emailArray[i])
        stu.put("phone", phoneArray[i])
        listData.add(stu)
    }
    println("2:"+listData)

    println("------------")
    val stName = arrayOf("AAA","BBB","CCC")
    val stId = arrayOf(100,101,102)
    val stAge = arrayOf(20,21,22)
    val stEmail = arrayOf("aaa@gmail.com","bbb@gmail.com","ccc@gmail.com")
    val stList: MutableList < MutableMap <String, Any> > =mutableListOf()
    for (i in 0 .. stName.size-1) {
        val data = mutableMapOf<String, Any>()
        data.put("name", stName[i])
        data.put("id", stId[i])
        data.put("age", stAge[i])
        data.put("email", stEmail[i])
        stList.add(data)
    }
    println(stList)






}
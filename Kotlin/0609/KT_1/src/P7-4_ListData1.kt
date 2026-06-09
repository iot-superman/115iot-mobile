fun main() {
    var mlst1 = mutableListOf<Int>()
    var mlst2 = mutableListOf<Double>(50.2)
    var mlst3 = mutableListOf(11,12,13,14,15)
    var mlst4 = mutableListOf("Mary","John","Nancy")
    var mlst5: MutableList<Any> = mutableListOf<Any>("Mary",50,162.3)

    println()
    println(mlst2)
    println(mlst3)
    println(mlst4)
    println(mlst5)

    println()
    mlst1.add(10)
    println(mlst1)
    mlst1.set(0,20)
    println(mlst1)
    mlst1[0]=30
    println(mlst1)
    println()

    mlst2.add(30.5)
    println(mlst2)

    mlst3.add(20)
    println(mlst3)
    mlst3.add(1,30)
    println(mlst3)
    mlst3.addAll(3,listOf(1,2,3))
    println(mlst3)

    println(mlst3.remove(0))

    println(mlst3.remove(15))
    println(mlst3)
    println(mlst3.removeAt(0))
    println(mlst3)


    println()
    for (data in mlst3)
        print("$data , ")

    println()

    for (i in 0 until mlst3.size)
         print("${mlst3[i]} , ")

    println()
    println(mlst3.contains(11))
    println(mlst3.containsAll(listOf(20,50)))
    mlst3.removeIf {
        println(it)
        (it == 2 ) || (it== 20)
    }
    println(mlst3)
    mlst3.clear()
    println(mlst3)

    println()
    mlst1.clear()
    println(mlst1)
    var index =0
    while(true) {
        print("input number or -1 to  end:")
        var v= readLine()?.toIntOrNull()?:0
        if(v == -1){
            break
        }
        mlst1.add(v)
        index++
    }
println(mlst1)

    mlst1 = mutableListOf(11,12,13,14,15)
    println()
    print("Input insert data :")
    var v = readLine()?.toIntOrNull()?: 0
    var lastIndex = mlst1.lastIndexOf(mlst1.last())

    print("input index number <${lastIndex+1} :")
    index = readLine()?.toIntOrNull() ?:0
    if (index>0  && index-1 <= lastIndex){
        mlst1.add(index,v)
        println(mlst1)
    }else{
        println("error index")
    }






}
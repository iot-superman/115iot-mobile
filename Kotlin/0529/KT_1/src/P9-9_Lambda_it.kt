fun main() {
    //寫法1:
    // inch to cm lambda
    val inch2cm:(Double)-> Double={inch ->
        inch*2.54
    }

    println(inch2cm(2.0))

    //寫法2:
    // 使用 it 來簡化 Lambda 的寫法，當 Lambda 只有一個參數時，可以使用 it 來代表這個參數，這樣就不需要再寫一個變數名稱了
    val inch2cm_1 = {it: Double ->   //it 還是要指定類型，因為 Lambda 沒有參數名稱了，所以需要指定 it 的類型
        it*2.54
    }

    println(inch2cm_1(2.0))
}
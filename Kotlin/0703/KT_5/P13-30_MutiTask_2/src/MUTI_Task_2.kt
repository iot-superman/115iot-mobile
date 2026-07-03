import kotlinx.coroutines.*
import java.awt.event.WindowEvent

fun main() {

    // =================================================================
    // 1. 建立第一個獨立的協程作用域 (變數名稱叫 scope1)
    // =================================================================
    var scope1 = CoroutineScope(Dispatchers.Default)

    // 在 scope1 中啟動第一個協程
    scope1.launch {
        repeat(3) {
            println("第一個 Scope - 協程 :$it")
            delay(300) // 暫停 300 毫秒
        }
    }

    // =================================================================
    // 2. 建立第二個獨立的協程作用域 (變數名稱叫 job)
    // =================================================================
    val job = CoroutineScope(Dispatchers.Default)
    job.launch {
        sFun() // 呼叫下方的 suspend 函式
    }

    // =================================================================
    // 3. 主程式區塊 (Main Thread) 迴圈
    // =================================================================
    for (count in 1..10) {
        println("count = $count")
        Thread.sleep(400) // 每次阻斷 400 毫秒
    }

    // =================================================================
    // 4. 結構化並發與異常處理展示
    // 重新將 scope1 指定為帶有 SupervisorJob 的作用域，防止工作 1 的異常牽連到工作 2
    // =================================================================
    scope1 = CoroutineScope(Dispatchers.Default + SupervisorJob())

    // 啟動工作 1 (故意引發異常)
    scope1.launch {
        delay(100)
        println("Job 1")
        error("job 1 fail") // 拋出錯誤
    }

    // 啟動工作 2 (對應截圖第 50~53 行)
    scope1.launch {
        delay(200)
        println("job 2 is ok")
    }

    // 模擬主程式等待 (對應截圖第 55~57 行)
    Thread.sleep(1000)
    println("waiting 2")
    println()

    runBlocking {
        // =================================================================
        // 使用runBlocking
        // async 執行區塊
        // 使用 await() 取得真正結果，而不是 Deferred 的狀態字串
        // =================================================================
        val value1 = async {
            delay(100)
            println("async job 1")
            100
        }

        val value2 = async {
            delay(200)
            println("async job 2")
            200
        }

        println("waiting 3")
        println("value1 = ${value1.await()}, value2 = ${value2.await()}")
        println("sum = ${value1.await() + value2.await()}")
        println()
    }
//    // =================================================================
//    // 5. async 執行區塊 (對應截圖第 59~68 行)
//    // async 啟動後會立即在背景執行，並回傳一個 Deferred 物件
//    // =================================================================
//    val value1 = scope1.async {
//        delay(100)
//        println("async job 1")
//        100 // 回傳結果
//    }
//
//    val value2 = scope1.async {
//        delay(200)
//        println("async job 2")
//        200 // 回傳結果
//    }
//
//    // 模擬主程式等待 1 秒鐘，讓 background 的 async 有充足時間跑完 (對應截圖第 70~74 行)
//    Thread.sleep(1000)
//    println("waiting 3")
//
//    // 【核心盲點】：這裡使用了 .toString()，所以印出來的是協程的狀態物件「DeferredCoroutine{Completed}」
//    // 而不是裡面的數字 100 或 200。這是因為沒有使用 await() 去取出裡面的數值。
//    println("value1 = ${value1.toString()}, value2 = ${value2.toString()}")
//    println()
    println()
    Thread.sleep(1000)
    println("waiting 3")
}

/**
 * 這是定義好的 掛起函式 (suspend function)
 * 對應截圖最下方第 77~81 行
 */
suspend fun sFun() {
    for (i in 1..5) {
        // 0x41 + i 轉字元，會印出 B, C, D, E, F
        println("${(0x41 + i).toChar()}")
        // 老師截圖第 80 行使用的是 Thread.sleep，但在 suspend 函式中
        // 建議實務上使用 delay(500) 才不會硬性卡死整個執行緒池喔！
        Thread.sleep(500)
    }
}


suspend fun doEvent()
{
    withContext(Dispatchers.Default) {
            println("doEvent")
            doJob()
            delay(300)
            println("event end")
        }
}


suspend fun doJob()
{
    withContext(Dispatchers.IO) {
        println("doJob")
        delay(100)
        println("job finished")
    }
}


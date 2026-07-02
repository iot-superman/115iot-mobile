import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
/*

 協同程式
 */
fun main() {

     // CoroutineScope() 先打這個 ，等紅燈炮來 add library, then 燈炮 import library
   //  CoroutineScope()
     var  scope = CoroutineScope(Dispatchers.Default)
     scope.launch {
        repeat(3){
             println("協同程式 $it")
             delay(300)
        }
     }

     for (count in 1..10) {
          println("主程式 $count")
          Thread.sleep(1000) // 主程式睡一秒鐘，讓協同程式有時間執行
     }
}


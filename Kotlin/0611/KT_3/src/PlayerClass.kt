import java.util.Locale

class PlayerClass {
    var name: String=""
    var score: Int=0

    private var newName: String = ""
    private var newScore: Int = 0




    fun  setData(name: String, score: Int=0){
        newName = name
        newScore = score
    }

    fun showNew(){
        println("Player name: ${newName}, Score: ${newScore}")
    }

    //P11-3
    //定義方法
    fun show(){
        println("${newName}, Score = ${newScore}")
    }

    //P11-4
}
/**
 * 用於處理字符串轉換的類別。
 *
 * 此類別提供一個帶有自訂 getter 和 setter 的屬性，
 * 可以自動將輸入的字符串轉換為大寫字母。
 */
class ToUpper {
    /**
     * 儲存處理過的字符串。
     *
     * Setter：
     * - 若輸入的值為空，則設定為 "No data"
     * - 否則將字符串轉換為大寫字母（根據預設語言環境）
     *
     * Getter：
     * - 返回 "Hi" 前綴加上儲存的字符串
     *
     * 範例：
     * ```
     * val obj = ToUpper()
     * obj.str = "hello"  // setter 執行：轉換為 "HELLO"
     * println(obj.str)   // getter 執行：輸出 "HiHELLO"
     *
     * obj.str = ""       // setter 執行：設定為 "No data"
     * println(obj.str)   // getter 執行：輸出 "HiNo data"
     * ```
     */
    var str: String = ""
       //P11-6
        /**
         * 自訂 setter：驗證並處理傳入的值。
         *
         * @param value 要設定的字符串值
         */
        set(value) {
            if (value.isEmpty())
                field = "No data"
            else
                field = value.uppercase(Locale.getDefault())
        }
        /**
         * 自訂 getter：返回帶有 "Hi" 前綴的字符串。
         *
         * @return 格式為 "Hi" + 字符串內容的結果
         */
//        get() = "Hi ," + field  //Lambda code
        //P11-8
        get() {
            return "Transfer data, " + field
        }
}
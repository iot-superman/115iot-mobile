/**
 * NewClass.kt
 *
 * 此檔案示範 Kotlin 中的巢狀類別（Nested Class）和內部類別（Inner Class）的用法。
 *
 * 主要概念：
 * - 巢狀類別：不持有外部類別實例的參考，類似靜態巢狀類別
 * - 內部類別：隱含持有外部類別實例的參考，可直接存取外部成員
 */

/**
 * Outer 類別
 *
 * 示範一個包含巢狀類別的外部類別。
 * 巢狀類別無法直接存取外部類別的成員。
 */
class Outer {
    /**
     * 外部類別的訊息屬性
     *
     * @property message 儲存來自外部類別的問候訊息
     */
    val message = "Hello from Outer"

    /**
     * Nested 巢狀類別
     *
     * 巢狀類別是靜態的，不與任何外部類別實例關聯。
     * 若要使用，需透過 `Outer.Nested()` 建構。
     */
    class Nested {
        /**
         * 傳回問候字串
         *
         * @return 來自巢狀類別的問候訊息
         */
        fun greet(): String {
            return "Hello from Nested"
        }
    }
}

/**
 * OuterIn 類別
 *
 * 示範一個包含內部類別的外部類別。
 * 與巢狀類別不同，內部類別可以存取外部類別的成員。
 */
class OuterIn {
    /**
     * 外部類別的訊息屬性
     *
     * @property message 儲存來自外部類別的問候訊息
     */
    val message = "Hello from OuterIn"

    /**
     * 外部類別的方法
     *
     * 此方法可被內部類別呼叫，展示內部類別與外部類別的互動。
     */
    fun outerFun() {
        println("This is outer In function")
    }

    /**
     * Inner 內部類別
     *
     * 內部類別隱含持有外部類別實例的參考，可以：
     * - 直接存取外部類別的屬性（例如 [message]）
     * - 呼叫外部類別的方法（例如 [outerFun]）
     *
     * 須透過外部類別實例建構：`outerIn.Inner()`
     */
    inner class Inner {
        /**
         * 傳回包含外部訊息的字串
         *
         * 此方法展示內部類別如何直接存取外部類別的 [message] 屬性。
         *
         * @return 包含外部訊息的字串
         */
        fun greet() = "Message is $message"

        /**
         * 呼叫外部類別的方法
         *
         * 此方法示範如何從內部類別呼叫外部類別的方法。
         */
        fun innerFun() {
            outerFun()
        }
    }
}

/**
 * main 主函式
 *
 * 程式入點。示範以下概念：
 * 1. 建立外部類別實例並直接存取其屬性
 * 2. 透過外部類別存取並呼叫巢狀類別
 * 3. 建立內部類別實例
 * 4. 內部類別存取外部成員的能力
 * 5. 內部類別呼叫外部方法的能力
 */
fun main() {
    // 建立外部類別實例並存取其訊息
    val outer = Outer()
    println(outer.message)

    // 建立並使用巢狀類別
    // 注意：巢狀類別需透過外部類別名稱存取（Outer.Nested()）
    val nested = Outer.Nested()
    println(nested.greet())

    println("----------")

    // 建立 OuterIn 實例
    val ourIn = OuterIn()
    // 呼叫外部類別的方法
    ourIn.outerFun()

    // 建立內部類別實例
    // 注意：內部類別需透過外部類別實例建構（ourIn.Inner()）
    val inner = ourIn.Inner()
    // 呼叫內部類別方法，該方法存取外部訊息
    println(inner.greet())
    // 呼叫內部類別方法，該方法呼叫外部方法
    inner.innerFun()
}
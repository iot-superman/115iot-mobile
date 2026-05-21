//https://chatgpt.com/s/m_6a0e7f7a53e881918c2e0171b79050d6
package org.example;

import java.util.function.Consumer;

/**
 * 示範如何使用 java.util.function.Consumer 與其預設方法 {@code andThen} 進行串接（chaining）。
 *
 * <p>此範例建立三個 {@link Consumer}{@code <String>} 實作：
 * <ul>
 *   <li>{@code printConsumer}：印出收到的字串</li>
 *   <li>{@code lengthConsumer}：印出收到字串的長度</li>
 *   <li>{@code againConsumer}：印出包含收到字串的客製訊息 ("Again: ... Bye Bye!!")</li>
 * </ul>
 *
 * <p>示範內容：
 * <ul>
 *   <li>直接呼叫 consumer 的 {@code accept} 方法執行單一副作用操作</li>
 *   <li>使用 {@code andThen} 將多個 consumer 串接，讓多個副作用操作依序執行</li>
 * </ul>
 *
 * <p>注意：
 * <ul>
 *   <li>此類別中的 main 方法簽章為 {@code static void main()}（無參數）；若要使用 Java 執行器直接執行，請改為標準簽章 {@code public static void main(String[] args)}。</li>
 *   <li>Consumer 適合用來執行有副作用（例如印出、寫入 log、變更外部狀態）的操作；它們不會回傳值。</li>
 * </ul>
 *
 * <p>執行下面範例程式碼後（假設已改為標準 main 簽章並依序執行呼叫），預期的主控台輸出示例如下：
 * <pre>
 * Hello
 * Andy
 * 4
 * Bill Gates
 * 10
 * Again: Bill Gates Bye Bye!!
 * </pre>
 *
 * @see java.util.function.Consumer
 */
public class Main {

    /**
     * 示範建立及串接 {@link Consumer} 實作並用 {@code accept} 執行。
     *
     * 實作細節：
     * - {@code printConsumer}：將傳入的字串印出到標準輸出（System.out）
     * - {@code lengthConsumer}：印出傳入字串的長度
     * - {@code againConsumer}：印出包含傳入字串的客製訊息
     *
     * 串接方式說明：
     * - {@code combinedConsumer = printConsumer.andThen(lengthConsumer)}：先印出字串，接著印出字串長度
     * - {@code againedConsumer = combinedConsumer.andThen(againConsumer)}：依序執行三個動作：印出字串 -> 印出長度 -> 印出客製訊息
     *
     * 重要提醒：Consumer 用於產生副作用的操作，不會回傳結果；若需要回傳值，請使用 Function/UnaryOperator 等其他函式介面。
     */
    static void main() {
        // Consumer：印出收到的字串
        Consumer<String> printConsumer = str -> System.out.println(str);

        // Consumer：印出收到字串的長度
        Consumer<String> lengthConsumer = str -> System.out.println(str.length());

        // Consumer：印出包含收到字串的客製訊息
        Consumer<String> againConsumer = str -> System.out.println("Again: " + str + " Bye Bye!!");

        // 直接呼叫單一 consumer
        printConsumer.accept("Hello");

        // 將 printConsumer 與 lengthConsumer 串接：接受時會先印字串，接著印長度
        Consumer<String> combinedConsumer = printConsumer.andThen(lengthConsumer);
        combinedConsumer.accept("Andy");

        // 繼續串接 againConsumer：執行順序為 print -> length -> again
        Consumer<String> againedConsumer = combinedConsumer.andThen(againConsumer);
        againedConsumer.accept("Bill Gates");
    }
}
package org.example;

import java.util.function.Consumer;

/**
 * Consumer + Method Reference 示範
 */
public class Main {

    public static void main(String[] args) {

        // Consumer：印出收到的字串
        Consumer<String> printConsumer =
                str -> System.out.println(str);

        // Consumer：印出字串長度
        Consumer<String> lengthConsumer =
                str -> System.out.println(str.length());

        // Consumer：客製訊息
        Consumer<String> againConsumer =
                str -> System.out.println(
                        "Again: " + str + " Bye Bye!!"
                );

        // 單一 Consumer
        printConsumer.accept("Hello");

        // Consumer chaining
        Consumer<String> combinedConsumer =
                printConsumer.andThen(lengthConsumer);

        combinedConsumer.accept("Andy");

        // 再串接
        Consumer<String> againedConsumer =
                combinedConsumer.andThen(againConsumer);

        againedConsumer.accept("Bill Gates");

        System.out.println("------------");

        /**
         * static 方法參考
         */
        Delegate<String> delegate =
                PrintMSG::printGreeting;

        delegate.invoke("Bob");

        System.out.println("------------");

        /**
         * 非 static 方法參考
         *
         * 必須建立物件
         */
        delegate =
                new PrintMSG()::printGreeting2;

        delegate.invoke("Mary");

    }
}

/**
 * 自訂泛型函式介面
 */
interface Delegate<T> {

    void invoke(T str);

}

/**
 * 方法參考示範類別
 */
class PrintMSG {

    /**
     * static 方法
     */
    static void printGreeting(String name) {

        System.out.println(
                "Hello " + name + "!"
        );

    }

    /**
     * 非 static 方法
     */
    void printGreeting2(String name) {

        System.out.println(
                "Again " + name + "!"
        );

    }
}
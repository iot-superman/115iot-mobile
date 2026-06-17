package org.example;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
        static void main() {
        Consumer<String> cs = str -> System.out.println(str); // Lambda 表達式：將輸入字串印出
        cs.accept("Daniel");

        Supplier<String> sp = () -> "HI, From Supplier!"; // Lambda 表達式：返回一個字串
        System.out.println(sp.get());

        Function<Integer, String> int2String =  (i)-> "Number From Function " + i; // Lambda 表達式：將整數轉換為字
        System.out.println(int2String.apply(1234));


        Predicate<Integer> isEven = (number ) -> number % 2 == 0; // Lambda 表達式：判斷整數是否為偶數
        System.out.println(isEven.test(123));  //false;

        Consumer<String> printConsumer =(str)-> System.out.println("Print:" +str);

        // 注意：Consumer.andThen(...) 需要另一個 Consumer 作為參數







//
//        // 注意：Consumer.andThen(...) 需要另一個 Consumer 作為參數，
//        // 不能直接傳入字串。因此若要印出其他字串，請使用 accept(...)：
//        cs.accept(" ");
//        cs.accept("Eric");
//
//        // 範例：示範使用 andThen() 串接另一個 Consumer
//        Consumer<String> chained = cs.andThen(s -> System.out.println("(chained) " + s));
//        chained.accept("Chained example");



    }


}

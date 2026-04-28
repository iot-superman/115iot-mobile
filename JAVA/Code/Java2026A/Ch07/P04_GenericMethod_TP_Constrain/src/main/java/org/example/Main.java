package org.example;

public class Main {

    // ✅ Java 正確入口點（一定要 public static void main）
    public static void main(String[] args) {

        Integer intArray[] = {1, 2, 3, 4, 5};
        Float floatsArray[] = {1.1f, 2.2f, 3.3f, 4.4f, 5.5f, 6.6f};
        Double dblArray[] = {6.1, 7.2, 8.3, 9.4, 10.5};

        // ✅ 呼叫泛型方法
        System.out.println("Sum of Integer Array: " + SumUpArray(intArray));
        System.out.println("Sum of Float Array: " + SumUpArray(floatsArray));
        System.out.println("Sum of Double Array: " + SumUpArray(dblArray));
    }

    // ✅ 一定要寫在 class 裡面
    // ✅ static：因為 main 是 static，這裡也要 static 才能直接呼叫
    // ✅ <T extends Number>：限制 T 一定是 Number 子類（Integer / Float / Double）
    static <T extends Number> double SumUpArray(T[] genericArray) {

        double sum = 0;

        // ✅ enhanced for loop（for-each）
        //for (T e : genericArray) {   // ← 建議明確寫 T，比 var 更清楚
        for (var e:genericArray) {   //

            sum += e.doubleValue(); // ← 關鍵：Number 類別提供的方法
        }

        return sum;
    }
}
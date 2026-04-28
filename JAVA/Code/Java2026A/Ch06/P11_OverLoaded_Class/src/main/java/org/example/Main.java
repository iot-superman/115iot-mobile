package org.example;

public class Main {

    // ✅ 正確的主程式進入點（必要）
    public static void main(String[] args) {

        // int + float → 自動轉型為 float
        System.out.println(add(12, 34.56f));

        // float + int → 也會自動轉型
        System.out.println(add(12.24f, 78));
    }

    // ✅ 方法1：int + float
    static float add(int a, float b) {
        return a + b;
    }

    // ✅ 方法2：float + int（方法多載 overloading）
    static float add(float a, int b) {
        return a + b;
    }
}
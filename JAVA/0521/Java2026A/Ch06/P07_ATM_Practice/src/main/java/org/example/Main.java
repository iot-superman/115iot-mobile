package org.example;

// 程式進入點類別
public class Main {
    public static void main(String[] args) {
        // ATM 測試程式碼（目前註解中）
        // ATM atm; // 宣告引用變數
        // atm = new ATM(); // 實例化物件
        // System.out.println("atm.balance = " + atm.balance); // 顯示餘額
        // atm.balance = 1000; // 設定餘額
        // ATM atm2 = new ATM(3500); // 使用建構子設定初始值
        // System.out.println("atm2.balance = " + atm2.balance);

        // 建立三角形物件 1，使用帶參數的建構子
        Triangle triangle = new Triangle(3.0, 4.0);
        System.out.printf("三角形1 底: %.2f , 高:%.2f 面積: %.2f\n", triangle.base, triangle.height, triangle.area());

        // 建立三角形物件 2，使用無參數建構子並手動設定屬性
        Triangle triangle2 = new Triangle();
        triangle2.base = 6;
        triangle2.height = 10;
        System.out.printf("三角形2 底: %.2f , 高:%.2f 面積: %.2f\n", triangle2.base, triangle2.height, triangle2.area());
    }
}

// 提款機類別
class ATM {
    int balance; // 帳戶餘額

    // 無參數建構子
    ATM() {}

    // 帶參數建構子：初始化餘額
    ATM(int initBalance) {
        balance = initBalance;
    }
}

// 三角形類別
class Triangle {
    double base;   // 底
    double height; // 高

    // 無參數建構子
    Triangle() {}

    // 帶參數建構子：初始化底與高
    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    // 計算面積的方法
    double area() {
        return base * height / 2.0;
    }
}
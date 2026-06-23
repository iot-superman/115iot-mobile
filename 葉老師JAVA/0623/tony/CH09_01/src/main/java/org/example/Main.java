package org.example;

class TaipeiBank{
    public int balance;  // 屬性
    TaipeiBank() {  //建構方法  用來設定初始值 (初始化)
        balance = 100;
    }
    public void printBalance() {   //一般方法
        System.out.println("存款餘額" + balance);
    }
}
public class Main {
    static void main() {
        TaipeiBank obj1 = new TaipeiBank();  // 已自動執行建構方法 所以balance是100
        obj1.printBalance();
    }
}

package org.example;


class MyClass{
    int age;
    String name;
    MyClass (int a){
        age =a;
    }
    MyClass(String str){
        name = str;
    }
    MyClass(int a,String str){
        age =a;
        name = str;
    }

    //一般方法
    public void printInfo(){
        System.out.println("name=" + name + ",age=" + age);
    }


}


class  SmallMath{
    int x,y;
 /**
 * 建構子 — 建立一個 SmallMath 物件並初始化內部操作數。
 *
 * 此建構子接受兩個整數參數，並將它們指派給實例變數 x 與 y，
 * 供後續的運算方法（例如 {@link #add()}、{@link #mul()}）使用。
 *
 * @param x 第一個整數操作數，會被指定給實例變數 {@code x}
 * @param y 第二個整數操作數，會被指定給實例變數 {@code y}
 */
SmallMath(int x, int y){
    this.x = x;
    this.y = y;
}
    public void add(){  //一般方法
        System.out.println("add= " + (x+y));
    }
    public void mul(){  //一般方法
        System.out.println("mul= " + (x*y));
    }
}


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class TaipeiBank{
    public int balance;
    TaipeiBank(){
        balance = 100;
    }
    public void printBalance(){
        System.out.println("存款餘額: " + balance);
    }
}



/**
 * 主程序類
 *
 * 此類用於演示物件導向編程的基本概念，
 * 展示如何創建和使用不同的類實例。
 */
public class Main {
    /**
     * 主程序方法 - 程序執行入口點
     *
     * 此方法執行以下操作：
     * 1. 創建一個 TaipeiBank 物件實例
     * 2. 調用其 printBalance() 方法來顯示帳戶餘額
     * 3. 創建一個 SmallMath 物件實例，初始值為 x=10, y=20
     * 4. 調用 add() 方法計算並輸出 x + y 的結果
     * 5. 調用 mul() 方法計算並輸出 x * y 的結果
     *
     * @example
     * // 執行此程序會輸出：
     * // 存款餘額: 100
     * // add= 30
     * // mul= 200
     */
    static void main(){
        // 創建台北銀行物件並顯示初始餘額
        TaipeiBank  obj1 = new TaipeiBank();
        obj1.printBalance();
        System.out.println("=================");

        SmallMath obj2 = new SmallMath(10, 20);
        obj2.add();
        obj2.mul();
        System.out.println("=================");


        // 創建數學計算物件（x=10, y=20），並執行加法運算
        MyClass obj31 = new MyClass(20);
        obj31.printInfo();
        MyClass obj32 = new MyClass("John");
        obj32.printInfo();
        MyClass obj33 = new MyClass(25, "Mary");
        obj33.printInfo();






    }
}
// https://chatgpt.com/s/m_6a31f3f05d5c8191bacf9daa9186e141
package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {


         Car obj1 = new Bmw();
         obj1.reful();
         obj1.run();
         obj1.test("hello world");

    }
}

abstract class  Car{
    abstract  void run();
    Car(){
        System.out.println("有車子了!");
    }
    abstract void test(String message);

    public void  reful() {
        System.out.println("汽車加油！");
    }
}

class Bmw extends Car{

    @Override
    void run() {
        System.out.println("run .........");
    }
    Bmw(){
        System.out.println("有車子BMW");
    }

    @Override
    void test(String message) {
        System.out.println("message="+message);
    }
}



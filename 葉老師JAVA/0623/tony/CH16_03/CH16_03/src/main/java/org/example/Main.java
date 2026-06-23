package org.example;

//抽象類別
abstract class Car{
    abstract void run();//抽象方法
    abstract void test(String message);//抽象方法
    Car(){ //建構方法
        System.out.println("有車子了!");
    }
    public void refuel(){ //一般方法
        System.out.println("汽車加油!");
    }
}
class Bmw extends Car{
    @Override
    void run() {
        System.out.println("run..........");
    }
    @Override
    void test(String message) {
        System.out.println("message="+message);
    }

    Bmw(){
        System.out.println("有車子bmw了");
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //Car obj1 = new Car();//false
        Bmw obj1 = new Bmw();
        obj1.refuel();
        obj1.run();
        obj1.test("hell world!");
       
    }
}

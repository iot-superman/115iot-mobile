package org.example;

interface School{
    int num_s = 100;
    default void demo(){
        System.out.println("School...");
    }
    default void test1(){
        System.out.println("test1...");
    }
    void test2();
    void test3();
}
class Dep implements School{
    int num_D = 200;
    @Override
    public void test2() {
        System.out.println("test2...");
    }
    @Override
    public void test3() {
        System.out.println("test3...");
    }
    public void test4() {
        System.out.println("test4...");
    }
    public void demo(){
        System.out.println("Dep...");
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        System.out.println("......upcasting.........");
        School obj1 = new Dep();//upcasting，向上轉型，目的模擬介面物化

        obj1.test1();
        obj1.test2();
        obj1.test3();
        //obj1.test4();//false
        obj1.demo();
        System.out.println("num_s:"+obj1.num_s);
        //System.out.println("num_s:"+obj1.num_D);//false
        System.out.println("......downcasting.........");
        Dep c = (Dep)obj1;//downcasting
        c.test1();
        c.test2();
        c.test3();
        c.test4();
        c.demo();
        System.out.println("num_s:"+c.num_s);
        System.out.println("num_D:"+c.num_D);


    }
}

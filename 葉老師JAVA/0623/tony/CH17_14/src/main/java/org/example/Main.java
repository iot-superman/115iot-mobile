package org.example;

//介面
interface Animal{
    void running();//抽象方法
}
//原始方法
class Dog implements Animal{
    @Override
    public void running() {
        System.out.println("Dog is running!!!");
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        System.out.println("........1.........");
        Animal h1 = new Dog();//向上轉型
        h1.running();
        //匿名類別
        System.out.println("........2.........");
        Animal h2 = new Animal() {
            @Override
            public void running() {
                System.out.println("h2..............");
            }
        };
        h2.running();
        System.out.println("........3.........");
        Animal h3 = new Animal() {
            @Override
            public void running() {
                System.out.println("h3..............");
            }
        };
        h3.running();
        System.out.println("........4.........");
        //lambda
        Animal h4 = ()->{
            System.out.println("h4..............");
        };
        h4.running();
    }
}

package org.example;

class Animal {
    private String name ; // 定義動物名字
    Animal(String name){
        this.name = name;
        System.out.println("Animal....");
    }
    public void eat() { // Animal方法eat
        System.out.println(name + "正在吃食物");
    }

    public void sleep() { // Animal方法sleep
        System.out.println(name + "正在睡覺");
    }
}
class Dog extends Animal{
    Dog(String name) {
        super(name);//呼叫父類別的建構方法
        System.out.println("Dog....");
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Dog d = new Dog("Haly");
        d.eat();
        d.sleep();
    }
}

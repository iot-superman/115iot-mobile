// https://chatgpt.com/s/m_6a29091949408191a2957c5d8f729c8e

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

class Dog extends  Animal{

    Dog(String name) {
        super(name);   //呼叫父類別的建構子方法來初始化name屬性
        System.out.println("Dog....");
    }
}

public class Main {
    static void main(String[] args) {
        Dog d = new Dog("Haly");
        d.eat();
        d.sleep();
    }
}

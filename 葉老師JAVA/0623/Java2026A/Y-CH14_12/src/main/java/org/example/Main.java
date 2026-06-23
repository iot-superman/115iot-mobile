package org.example;


class Animal {
    protected String name; // 宣告protected存取修飾符定義動物名字

    public void eat() { // Animal方法eat
        System.out.println(name + "正在吃食物");
    }

    public void sleep() { // Animal方法sleep
        System.out.println(name + "正在睡覺");
    }
}

class Dog extends Animal {
    Dog(String name) { // Dog建構方法
        this.name = name; // 呼叫父類別屬性
    }

    public void barking() { // Dog類別自有的方法barking
        System.out.println(name + "正在叫"); // 可以繼承name了
    }
}

class Bird extends Animal {
    Bird(String name) { // Bird建構方法
        this.name = name; // 呼叫父類別屬性
    }

    public void flying() { // Bird類別自有的方法flying
        System.out.println(name + "正在飛"); // 可以繼承name了
    }
}




public class Main {
    static void main() {

        Dog dog = new Dog("Haly");
        dog.eat(); // dog繼承Animal方法eat()
        dog.sleep(); // dog繼承Animal方法sleep()
        dog.barking(); // Dog類別自有的方法
        dog.name="ccc";
        dog.barking(); // Dog類別自有的方法


        Bird bird = new Bird("Cici");
        bird.eat(); // bird繼承Animal方法eat()
        bird.sleep(); // bird繼承Animal方法sleep()
        bird.flying(); // bird類別自有的方法


    }
}

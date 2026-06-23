package org.example;

class Myclass {
    int age;
    String name;    //建構方法+overload
    Myclass(int a) {
        age = a;
    }
    Myclass(String str) {
        name = str;
    }
    Myclass(int a, String str) {
        age = a;
        name = str;
    }
    public void printInfo() {
        System.out.println("name = " + name + ", age = " + age);
    }
}

public class Main {
    static void main() {
        Myclass obj1 = new Myclass(20);
        obj1.printInfo();
        Myclass obj2 = new Myclass("John");
        obj2.printInfo();
        Myclass obj3 = new Myclass(25, "Mary");
        obj3.printInfo();
    }
}

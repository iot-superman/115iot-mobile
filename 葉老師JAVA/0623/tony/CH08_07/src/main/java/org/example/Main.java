package org.example;

class ShTest{
    int x = 10;
    int y = 50;
    String z = "John";
    public void printInfo(int x){
        System.out.println("區域變數:" + x);
        System.out.println("屬性:"+this.x);
        System.out.println("屬性:"+this.y);
        System.out.println("屬性:"+y);
    }
    public void show(String z){
        System.out.println("區域變數:" + z);
    }
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        ShTest obj = new ShTest();
        obj.printInfo(20);
        obj.show("Mary");
    }
}

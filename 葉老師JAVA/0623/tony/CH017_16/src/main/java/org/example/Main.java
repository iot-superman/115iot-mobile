package org.example;

interface Animal{
    void running();
}
class Cat{
    //傳含有Animal介面成份的物件
    public void showMe(Animal obj){
        System.out.println("showMe.....");
        obj.running();
    }
}
//原始寫法
class Temp implements Animal{
    @Override
    public void running() {
        System.out.println("running(temp)......");
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        System.out.println(".......1.......");
        //原始寫法
        Cat c1 = new Cat();
        Animal t = new Temp();//向上轉型
        c1.showMe(t);
        System.out.println(".......2.......");
        //匿名類別
        Cat c2 = new Cat();
        c2.showMe(new Animal() {
            @Override
            public void running() {
                System.out.println("running(cat2)......");
            }
        });
        c2.showMe(new Animal() {
            @Override
            public void running() {
                System.out.println("running(cat2-2)......");
            }
        });
        System.out.println(".......3.......");
        //lambda
        Cat c3 = new Cat();
        c3.showMe(()->{
            System.out.println("running(cat3)......");
        });
        System.out.println(".......4.......");
        //屬性設定成物件傳入
        Cat c4 = new Cat();
        c4.showMe(obj1);

    }
    //屬性設定成物件傳入
    private static Animal obj1 = new Animal() {
        @Override
        public void running() {
            System.out.println("running(obj1)................");
        }
    };
}

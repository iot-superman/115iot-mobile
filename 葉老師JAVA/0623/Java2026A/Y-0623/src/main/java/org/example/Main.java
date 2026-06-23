package org.example;

interface Animal {
    void running();
}

class Cat {
    //傳合介Animal介面成份的物件
    public void showMe(Animal obj) {
        System.out.println("showMe......");
        obj.running();
    }
}

//原始寫法
class Temp implements Animal {
    @Override
    public void running() {
        System.out.println("running(temp)......");
    }
}

public class Main {
    static void main() {
        System.out.println(".......1........");
        Cat c1 = new Cat();
        c1.showMe(new Temp());

        System.out.println(".......2........");
        //使用匿名類別，來實作介面
        Cat c2 = new Cat();
        c2.showMe(new Animal() {
            @Override
            public void running() {
                System.out.println("running(匿名類別)......");
            }
        });
        c2.showMe(new Animal() {      //使用匿名類別，來實作介面);
            @Override
            public void running() {
                System.out.println("running(匿名類別2)......");
            }
        });
    }
}

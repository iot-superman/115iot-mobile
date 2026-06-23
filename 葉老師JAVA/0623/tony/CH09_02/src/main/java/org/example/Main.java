package org.example;

class SmallMath {
    int x,y;    //屬性
    SmallMath(int x, int y) {  //建構方法
        this.x = x;
        this.y = y;
    }
    public void add() {       //一般方法
        System.out.println("add=" + (x + y));
    }
    public void mul() {       //一般方法
        System.out.println("mul=" + (x * y));
    }
}

public class Main {
    static void main() {
        SmallMath obj1 = new SmallMath(5, 10);
        obj1.add();
        obj1.mul();
    }
}

package org.example;
//抽象類別
abstract class Cal{
    public int x;
    public int y;
    Cal(int x, int y){ //建構方法
        this.x = x;
        this.y = y;
        System.out.println("初始化數值!");
    }
    double mul(){ //一般方法
        return x*y;
    }
    abstract double answer();//抽象方法
}
class CalPlus extends Cal{
    CalPlus(int x, int y) {
        super(x, y);
    }
    @Override
    double answer() {
        return x+y;
    }
}
class CalMinus extends Cal{
    CalMinus(int x, int y) {
        super(x, y);
    }
    @Override
    double answer() {
        return x-y;
    }
}
public class Main {
    static void main() {
        CalPlus myPlus = new CalPlus(10,20);
        System.out.println(myPlus.mul());
        System.out.println(myPlus.answer());

        CalMinus calMinus = new CalMinus(10,20);
        System.out.println(calMinus.mul());
        System.out.println(calMinus.answer());
    }
}

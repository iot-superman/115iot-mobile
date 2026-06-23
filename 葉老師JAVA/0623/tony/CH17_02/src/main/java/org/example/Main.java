package org.example;

interface Shape{
    double PI = 3.14;//static final
    double area();//抽象方法
}
class Rectangle implements Shape{
    public double height, width;
    Rectangle(double height, double width){
        this.width = width;
        this.height = height;
    }
    @Override
    public double area() {
        return width*height;
    }
}
class Circle implements Shape{
    public double r;
    Circle(double r){
        this.r = r;
    }
    @Override
    public double area() {
        return r*r*PI;
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Rectangle r = new Rectangle(2,3);
        System.out.println("矩形面積:"+r.area());
        System.out.println("r_PI:"+r.PI);
        Circle c = new Circle(2);
        System.out.println("圓面積:"+c.area());
        System.out.println("c_PI:"+c.PI);
        System.out.println("s_PI:"+Shape.PI);
        //Shape.PI = 40;//不能被修改
    }
}

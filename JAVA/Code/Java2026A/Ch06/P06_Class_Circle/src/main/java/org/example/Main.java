package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Circle circle = new Circle();       //Via 內定建構子
        Circle circle1 = new Circle(2.3);        //Via 標準建構子
        System.out.printf("circle: %.2f\n" ,  circle.getPerimter());
        System.out.printf("circle1: %.2f\n" ,  circle1.getPerimter());
    }
}


class  Circle{
     double radius = 0;

     Circle(){}
     Circle(double radius){                 //屬性可以加this，用以區別屬性radius參數raduis的差別
         this.radius = radius;
     }
     double getPerimter(){
         return  2* Math.PI * radius;
     }
     void setRadius(double r){
         radius =r;
     }


}

package org.example;

import java.util.Scanner;

/*

* */
public class Main {
    static void main() {
        /*
        第三次作業(物件與類別: 熟悉如何建立類別)
        1.	定義一個圓形類別(class Circle), 該類別包含屬性double radius, 標準建構子,
        及一個方法double getArea().
        2.	請使用標準建構子, 並傳入半徑為5.67
        3.	請印出該圓面積為多少?
    */
        double r = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please input radius:");
        r= scanner.nextDouble();


        Circle c = new Circle(r);
        System.out.printf("Radius:%f, Area=%f",r,c.getArea());
        scanner.close();


    }
}

class  Circle{
    double radius;
    Circle(){}
    Circle(double radius){
        this.radius = radius;
    }
    double getArea(){
        return Math.PI * radius * radius;
    }







}


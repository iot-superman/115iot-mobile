package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Rectangle rec = new Rectangle();   //call Default Constuctor ; 內定建構子；
        rec.length = 123;
        rec.width = 456;
        System.out.println("width: "+rec.width +" lenght: "+rec.length +" area:"+rec.Area());
        Rectangle rec2 = new Rectangle(12,78);
        System.out.println("width: "+rec2.width +" lenght: "+rec2.length +" area:"+rec2.Area());

    }
}

class Rectangle{
    double length;
    double width;
    double Area(){
        return  length* width;
    }
    Rectangle(){}   //內定建構子，有下面的標準建構子這行要補

    Rectangle(int len, int wid){ //all attributes are
        length = len;
        width = wid;
    }

    }

package org.example;

public class Main {

    // ✅ 正確 Java 入口點
    public static void main(String[] args) {

        Geometry g = new Geometry();
        Rect     r = new Rect(1.23, 4.56);
        Circle   c = new Circle(7.89);

        System.out.println("g Area: " + g.computeArea());
        System.out.println("r Area: " + r.computeArea());
        System.out.println("c Area: " + c.computeArea());

        System.out.println("-----------------");

        // ✅ 多型呼叫
        System.out.println("g Area: " + getArea(g));
        System.out.println("r Area: " + getArea(r));
        System.out.println("c Area: " + getArea(c));
    }

    // ✅ 要寫在 main() 外面
    static double getArea(Geometry g){        // parent type

        if (g instanceof Circle){
            System.out.println("radius"+ ((Circle) g).radius);
        } else if(g instanceof Rect){
          System.out.println("width:"+ ((Rect) g).width + ", length:"+ ((Rect) g).length);
        }
        else if(g instanceof Geometry ){
         System.out.println("No Attribute");
        }

        return g.computeArea();               // 動態繫結（Polymorphism）
    }
}

// ==========================
// 父類別
// ==========================
class Geometry {
    double computeArea(){
        return 0;
    }
}   // ❗這個大括號你原本少了

// ==========================
// 矩形
// ==========================
class Rect extends Geometry {
    double width;
    double length;

    Rect(double width, double length){
        this.width = width;
        this.length = length;
    }

    // Override
    double computeArea(){
        return width * length;
    }
}

// ==========================
// 圓形
// ==========================
class Circle extends Geometry {
    double radius;

    Circle(double radius){
        this.radius = radius;
    }

    // Override
    double computeArea(){
        return Math.PI * radius * radius;
    }
}
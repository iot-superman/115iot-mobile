package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        Point<String> p1 = new Point<>("Andy", "Smith");
        p1.showInfo();

        Point<Integer> p2 = new Point<>(10, 20);
        p2.showInfo();

        Point<Float> p3 = new Point<>(1.5f, 2.5f);
        CPoint<Integer,Double> p4 = new CPoint<>(1, 2.5);


        p3.showInfo();
        p4.showInfo();
    }
}


class Point<T> {

    T x, y;

    Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    void showInfo() {
        System.out.println("x: " + x + ", y: " + y);
    }
}

// Generic class with multiple type parameters 複合型態
class CPoint<T,P> {

    T x;
    P y;
    CPoint(T x, P y) {
        this.x = x;
        this.y = y;
    }
    void showInfo() {
        System.out.println("x: " + x + ", y: " + y);
    }

}



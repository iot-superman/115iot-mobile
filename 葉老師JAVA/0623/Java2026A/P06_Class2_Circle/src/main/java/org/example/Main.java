package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Triangle triangle = new Triangle( 3.0 , 4.0);
        System.out.printf("Triangel base: %.2f , heigt:%.2f Area: %.2f\n" ,triangle.base,triangle.height, triangle.Aera());

        Triangle triangle2 = new Triangle();
        triangle2.base=6;
        triangle2.height=10;
        System.out.printf("Triangel2 base: %.2f , heigt:%.2f Area: %.2f\n" ,triangle2.base,triangle2.height, triangle2.Aera());
    }
}
class  Triangle{
    double base;
    double height;

    Triangle(){}
    Triangle(double base,double height){
        this.base = base;
        this.height = height;
    }
    double Aera(){
        return  base * height /2.0;
    }
}
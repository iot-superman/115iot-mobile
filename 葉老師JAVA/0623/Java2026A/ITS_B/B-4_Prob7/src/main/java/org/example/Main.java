package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        double area = Main.PI*1.0*1.0;
        System.out.format("面積=%.3f",area);
        System.out.format("面積=%.0f",area);
        System.out.format("面積=%09f",area);
    }
}

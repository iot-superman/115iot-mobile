package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int sum = 55;
    for (int i = 1; i < 10; i++) {
        add(sum);
        }
    System.out.println(sum);
    }
    char x=65535;   //16 bits; //non-negative; //unsigned

    static void add(int sum) {
        sum ++;
        System.out.println("In add :" + sum);
    }
}

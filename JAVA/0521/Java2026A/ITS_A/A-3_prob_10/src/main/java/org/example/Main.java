package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int x=5;
    int y=10;
    int z= ++x*y--;    //6*10, then =9
    int n =x-- + ++y;  //6+10, then x=5


    System.out.println(z);  //60
     System.out.println("x="+x+" y="+y+" z="+z);  //x=5 y=10


    System.out.println(n);  //16
    }
}

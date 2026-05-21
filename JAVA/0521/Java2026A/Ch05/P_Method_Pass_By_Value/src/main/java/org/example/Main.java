package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int a=10;
    System.out.println("Brfore Call Merhod a=" +a);  //10
    multiply_10(a);
    System.out.println("Brfore Call Merhod a=" +a);  //10
    multiply_10(a);

    int b=20;
    System.out.println("Brfore Call Merhod a=" +b);  //20
    b = mmultiply_10(b);
    System.out.println("Brfore Call Merhod a=" +b);  //200
    mmultiply_10(b);

    a=10;
    b=20;
    System.out.println(a +" " +b);

    int tmp;
    a = 10;
    b=20;
    tmp = a;
    a = b;
    b= tmp;
    System.out.println(a+ " " +b);
    }

    static void multiply_10 (int a) {
        a= a*10;
        System.out.println("in mutiply_10 a=" +a);
    }


    static int mmultiply_10 (int b) {
        b= b*10;
        System.out.println("in mutiply_10 b=" +b);
        return  b;
    }
}

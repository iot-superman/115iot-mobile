package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
      int a;
      //加法和位移運算的優先級：加法（+）的優先級高於位移運算（<<）。因此，表達式會先計算加法部分，再進行位移運算。
      a = 9*4<<3+2; //36<<3+2   6<<5 = 36*2^5 = 36*32 = 1152

        System.out.println(a);
        int b=5, c=5;
        a=++b+c++*3; //a=6+5*3=6+15=21  b=6  c=6
        System.out.println("a= "+a+" "+"c= "+c);

        System.out.println("b= "+b+" "+"c= "+c);
                a = b++ + ++c*3; //a=6+7=13  b=7  c=7
        System.out.println("a= "+a+" "+"b= "+b);

        a=5*4+8%3<<3; //a=20+2<<3  a=22<<3  a=22*8=176

        System.out.println(a);
    }
}

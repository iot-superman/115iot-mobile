package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        int x; //1Byte
        int y;  //2Byte

        x=123;
        y=456;

        System.out.println("x = " + x + "And y=" +y);


        x=0b101;
        y=0B11111111111111111111111111111111;   //-1 所盃32位元佔滿，且皆是1
        System.out.println("x ="+ x+ "And y="+y);

        x=0123;  //1*8^2 +2*8^1 + 3  =64 + 16 + 3
        y=0456;  //4*8^2 +5*8^1 + 6  =64 + 40 + 6
        System.out.println("x ="+ x+ " And y="+y);

        x=0x123;  //1*16^2 +2*16^1 + 3  =256 + 32 + 3 = 291
        y=0x456;  //4*16^2 +5*16^1 + 6  =1024 + 86 + 80+6 =1110
        System.out.println("x ="+ x+ " And y="+y);
    }
}

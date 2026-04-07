package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
     int year;
     System.out.print("請輸入年份(西元): ");
     year = new Scanner(System.in).nextInt();

     if (year % 4 == 0) {
         if (year % 100 == 0|| year % 400 == 0) {
                System.out.println(year + " 閏年.");
            } else {
                System.out.println(year + " 是平年.");
         }
     } else {
         System.out.println(year + " 是平年.");
     }

    }
}

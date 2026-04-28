package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
/*
input 1
   while  1+3+5...+49
input 2
   while  2+4+6...+50

* */

        int sum;
        int i,inp;
        System.out.println("Please input 1 or 2 :");
        Scanner scanner = new Scanner(System.in);
            sum=0;
            inp = scanner.nextInt();
            if (inp == 1) {
                i = 1;
                while (i <= 49) {
                    sum = sum + i;
                    i += 2;
                }
                System.out.println("sum:" + sum);
            } else if (inp == 2) {
                i = 2;
                while (i <= 50) {
                    sum = sum + i;
                    i += 2;
                }
                System.out.println("sum:" + sum);
            } else {
                System.out.println("NOT 1 or 2");

            }

        scanner.close();
    }
}

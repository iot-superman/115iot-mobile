package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    /*
    input 1
         FOR 1 +3 +5 ...49

         2
         FOR 2 +4 +6 ... 50

     */

        int sum=0;
        int i,inp;

        System.out.println("Please input 1 or 2");
        Scanner scanner = new Scanner(System.in);

        inp = scanner.nextInt();

        if  (inp ==1) {
            for (i = 1; i <= 49; i+=2) {
                sum = sum + i;
            }
        } else {
            for (i = 2; i <= 50; i+=2) {
                sum = sum + i;
            }
        }
        System.out.println(sum);
    }
}

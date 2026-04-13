package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.Scanner;

public class Main {

    static void main() {
        Scanner scanner = new Scanner(System.in);
        int n1,n2;
        System.out.println("請輸入一個數字：");
        n1 = scanner.nextInt();
        System.out.println("請輸入另外一個數字：");
        n2 = scanner.nextInt();
        int res;
        res = Max(n1,n2);     // n1, n2 are actual arguments //引數;
        System.out.println("Max of n1 n2: "+res);


    }
    static int Max(int num1, int num2){      //num1, num2 are formal parameters;//參數;
        if (num1>num2) return num1;
        else return num2;
    }
}

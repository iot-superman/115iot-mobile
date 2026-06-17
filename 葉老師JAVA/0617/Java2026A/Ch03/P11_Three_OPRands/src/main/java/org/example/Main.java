package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        int a,b;
        System.out.println("Please enter int a:");
        a = scanner.nextInt();
        System.out.println("Please enter int b:");
        b = scanner.nextInt();
        int larger,smaller;
        larger = a>b ? a : b;
        smaller = a<b ? a : b;
    }
}

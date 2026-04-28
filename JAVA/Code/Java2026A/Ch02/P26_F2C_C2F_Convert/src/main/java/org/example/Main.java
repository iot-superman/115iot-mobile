package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please Input your Selection (1 for F2C, 2 for C2F): ");
        int sel = scanner.nextInt();
        double c, f;
        if (sel == 1) {
            System.out.print("Please Input oC ");
            c = scanner.nextDouble();
            f = c * 9 / 5 + 32;
            System.out.println("Correspoding oF " + f);
        } else if (sel == 2) {
            System.out.print("Please Input oC ");
            c = scanner.nextDouble();
            f = c * 9 / 5 + 32;
            System.out.println("Correspoding oF " + f);
        } else {
            System.out.println("Invalid Selection!Byebye!");
        }
    }
}

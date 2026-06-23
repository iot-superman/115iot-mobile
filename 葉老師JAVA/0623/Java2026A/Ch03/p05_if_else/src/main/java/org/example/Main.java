package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int age;
    System.out.println("please input your age:");
        Scanner scanner = new Scanner(System.in);
        age = scanner.nextInt();
        if (age >= 18) {
            System.out.println("可以領取選票");
            System.out.println("請投神聖一票");
        } else {
            System.out.println("未滿18歲，無法領取選票");
        }
        System.out.println("Thank you");
    }
}

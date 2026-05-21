package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        double height,weight,bmi;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please Input your Weight (kg): ");
        weight = scanner.nextDouble();
        System.out.print("Please Input your Height (cm): ");
        height = scanner.nextDouble();
        height = height / 100; // cm 轉換為 m
        bmi = weight / (height * height);
        System.out.println("Your BMI is " + bmi);
    }
}

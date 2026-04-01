package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    double price ;
    int age;
    int ticket =100;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your age:");
        age = scanner.nextInt();

        if(age>=80 || age<=6) price =ticket *0.2;
        else if(age>=60 ||age<=12) price = ticket *0.5;
        else price = ticket;

        System.out.println("Your ticket price is " + price);


    }
}

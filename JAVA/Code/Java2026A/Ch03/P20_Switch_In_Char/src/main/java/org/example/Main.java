package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    System.out.println("請輸入成績級別A, B, C, D, F");
    char socre = new Scanner(System.in).next().charAt(0);
    switch (socre){
        case 'A':
            IO.println("Your score isd between 90-100");
            break;
        case 'B':
            IO.println("Your  score isd between 80-89");
            break;
        case 'C':
            IO.println("Your  score isd between 70-79");
            break;
        case 'D':
            IO.println("Your  score isd between 60-69");
            break;
        case 'F':
            break;
        default:
            IO.println("Invaild Input Error");
            break;
    }
    }
}

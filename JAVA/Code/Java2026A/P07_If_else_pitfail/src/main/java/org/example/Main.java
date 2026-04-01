package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        int score = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your score:");
        score = scanner.nextInt();
        if (score==10); //這裡的分號會讓if條件失效，導致下面的程式碼無論如何都會執行 pitfall
            System.out.println("Perfect score!");



        System.out.println("測試已經完!!!");

    }
}

package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a Score:");
        int score = scanner.nextInt();
        char grade;
        if(score>=90)  //code block;
            grade = 'A';
        else if(score>=80)
            grade = 'B';
        else if(score>=70)
            grade = 'C';
        else if(score>=60)
            grade = 'D';
        else
            grade = 'F';


        System.out.println("Score is " + score + "And Grade is " + grade);


    }
}

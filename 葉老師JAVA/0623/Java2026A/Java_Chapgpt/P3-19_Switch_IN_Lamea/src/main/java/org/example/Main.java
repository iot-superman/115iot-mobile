package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
            System.out.println("請輸入成績級別A, B, C, D, F");
            char socre = new Scanner(System.in).next().charAt(0);
            switch (socre){
                case 'A'->
                    IO.println("Your score isd between 90-100");
                case 'B'->
                    IO.println("Your  score isd between 80-89");
                case 'C'->
                    IO.println("Your  score isd between 70-79");
                case 'D'->
                    IO.println("Your  score isd between 60-69");
                case 'F'->
                        IO.println("Please major it again!!!");
                default->
                    IO.println("Invaild Input Error");

            }
        }
    }


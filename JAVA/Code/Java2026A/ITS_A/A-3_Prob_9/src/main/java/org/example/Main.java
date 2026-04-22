package org.example;

import java.util.Scanner;

public class Main {
    static void main() {
        System.out.println(getData());
    }
    public static  String getData(){
        System.out.println("請輸入日期(格式:MMDDYYYY):");
        Scanner sc = new Scanner(System.in);   //B
        String fristDate = sc.next();          //C
        sc.close();                           //D
        return fristDate;
    }
}

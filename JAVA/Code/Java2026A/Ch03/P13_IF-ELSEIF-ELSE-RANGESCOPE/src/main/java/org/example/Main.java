package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main(String[] args) {
     char ch;
        Scanner scanner = new Scanner(System.in);
        System.out.println("輸入一個字元:");
        ch = scanner.nextLine().charAt(0);

        if(ch>='A' && ch<='Z')
            System.out.println(ch + " 是大寫字母"+ch);
        else if(ch>='a' && ch<='z')
            System.out.println(ch + " 是小寫字母"+ch);
        else if(ch>='0' && ch<='9')
            System.out.println(ch + " 是數字" +ch);
        else
            System.out.println(ch + " 是特殊字元 "+ch);


    }
}

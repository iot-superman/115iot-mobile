package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("輸入入一個數字");
        int n1 = scanner.nextInt();
        System.out.println("輸入入另一個數字");
        int n2 = scanner.nextInt();
        int result = 0;
        try {   //try block
            result = n1 /  n2;   //Force Casting;強制轉型；
        }catch(Exception e){ //catch block
            System.out.println("Exeception Occurs" + e.getMessage());
        }
        finally {   //finally block
            System.out.println("result is :" + result);
        }
        System.out.println("Bye");
     
    }

}

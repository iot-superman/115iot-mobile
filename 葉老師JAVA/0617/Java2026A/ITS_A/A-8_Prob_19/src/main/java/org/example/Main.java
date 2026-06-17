package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        String kind;
        int age;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Input age:");
        age = scanner.nextInt();
        if (age >=65) {
            kind = "老人";
//        } else if (age >= 20 && age<65){       //但低於65歲   (<65)
        } else if (age >= 20){       //但低於65歲不用寫出來，因為會自動成立   (<65)
            kind = "成人";
        } else {
            kind = "青年";
        }
        System.out.println("The person is " + kind);
    }
}

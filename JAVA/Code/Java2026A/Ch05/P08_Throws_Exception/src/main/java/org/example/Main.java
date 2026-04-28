package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        int i=0;
        System.out.println("Please Input a int number: ");
        try {
            i = new Scanner(System.in).nextInt();
        }
        catch (Exception e){
            System.out.println("Excepion " +e.getMessage());

        }finally {
            System.out.println("i= "+i);
        }

        System.out.println("Bye !!");

    }
}

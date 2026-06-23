package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
       System.out.println("請輸入學年: ");
         int year = new java.util.Scanner(System.in).nextInt();
        System.out.print("You are named ");
         switch (year){
                case 1:
                    System.out.println("Freshman");
                    break;
                case 2:
                    System.out.println("Sophomore");
                    break;
                case 3:
                    System.out.println("Junior");
                    break;
                case 4:
                    System.out.println("Senior");
                    break;
                default:
                    System.out.println("Extended");
         }
    }
}

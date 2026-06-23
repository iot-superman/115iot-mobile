package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        System.out.println("請輸入weekday: ");
        String weekday = new Scanner(System.in).next();
        switch (weekday) {
            case "Sun":
                System.out.println("Take a Rest");
                break;
            case "Mon":
                System.out.println("Reserve a Room");
                break;
            case "Tue":
                System.out.println("Prepare a Slide");
                break;
            case "Wed":
                System.out.println("Send out meeting minutes");
                break;
            case "Thu":
                System.out.println("Order snacks");
                break;
            case "Fri":
                System.out.println("Meetinjg at 9 AM!");
                break;
            case "Sat":
                System.out.println("Take a Rest");
                break;
            default:
                break;
        }

    }

}



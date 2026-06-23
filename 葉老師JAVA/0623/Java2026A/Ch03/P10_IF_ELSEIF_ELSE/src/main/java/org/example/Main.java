package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main(String[] args) {
        final double LOWER_LIMIT = 500.0;
        final double LOW_LIMIT = 0.05;
        final double MED_LIMIT = 1000.0;
        final double MED_RATE = 0.06;
        final double HIGH_RATE = 0.08;

        double salesAmount;
        double CommissionRate;

        Scanner scanner = new Scanner(System.in);
        System.out.println("please Enter sales amount:");
        salesAmount = scanner.nextDouble();

        if(salesAmount<LOWER_LIMIT)
            CommissionRate = LOW_LIMIT;
        else if(salesAmount<=MED_LIMIT)
            CommissionRate = MED_RATE;
        else
            CommissionRate = HIGH_RATE;

        System.out.println("SalesAmount: " + salesAmount + " And Commission Rate is " + CommissionRate);

    }
}

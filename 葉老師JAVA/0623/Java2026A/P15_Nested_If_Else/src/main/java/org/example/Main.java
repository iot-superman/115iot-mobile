package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        final int MIN_ITEMS;
        MIN_ITEMS = 3;

        final int MIN_VALUE = 1000;
        final int SMALL_BOUNS = 10;
        final int MEDIUM_BOUNS = 50;
        final int LARGE_BOUNS = 50;

        int itemsold,totalValue,bonus;
        System.out.println("請輸入銷售的商品數量，及多少金額:");
        Scanner scanner = new Scanner(System.in);
        itemsold = scanner.nextInt();
        totalValue = scanner.nextInt();

        if (itemsold>MIN_ITEMS){
            if(totalValue> MIN_VALUE){
                bonus = LARGE_BOUNS;
            } else {
                bonus = MEDIUM_BOUNS;
            }
        }else {
            bonus = SMALL_BOUNS;
        }
            System.out.printf("itemSold =%d,totalValue=%d ,bouns=%d",itemsold,totalValue,bonus);


    }
}

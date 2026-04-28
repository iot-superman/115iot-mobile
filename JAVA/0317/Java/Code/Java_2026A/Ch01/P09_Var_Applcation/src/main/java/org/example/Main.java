package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        int hourlyRate = 123;
        int hours_per_day = 8;
        int days_per_year = 280;
        int earn;
        earn = hourlyRate * hours_per_day * days_per_year;
        System.out.println("Earn Per Yeaer:" + earn);  //String cascade; 字串的串啦， +

        int rent_per_month = 9000;
        int saved_per_yaer;
        saved_per_yaer = earn - rent_per_month *12;
        System.out.println("Earn Per Year:"+ earn +"and saved_per_year:" + saved_per_yaer);  //



    }
}

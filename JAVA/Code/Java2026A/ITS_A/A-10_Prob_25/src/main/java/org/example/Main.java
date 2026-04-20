package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        int times = 60;
        while (times > 0) {
            System.out.println("倒數計時中..... " + times);
            times--;
        }


        times = 60;
        while (times>=0) {
            if (times == 0) break;         //一行不用大括號
            else{
                System.out.println("倒數計時中..... " + times);
                times--;
            }
        }
    }
}

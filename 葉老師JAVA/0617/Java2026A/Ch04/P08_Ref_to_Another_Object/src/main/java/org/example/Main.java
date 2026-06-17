package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //x 是一個參考變數；
        int [] x;
        x = new int[]{1,2,3};
        for (int e: x) System.out.print(e+"\t");System.out.println();

        int[] y = x;
        x = new int[] {7,8};

        for (int e: x) System.out.print(e+"\t");System.out.println();
        for (int e: y) System.out.print(e+"\t");System.out.println();



    }
}

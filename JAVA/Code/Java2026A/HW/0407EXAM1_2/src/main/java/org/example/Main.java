package org.example;
/*
+1*3*5*7 -2*4*6*8 - 3*5*7*9 + 4*6*8*9 +5*7*9*11 - 6*8*10*12 +7*9*11*13

\
......  -99*101*103*105 + 100*102*104*106

 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        int sign = 1;
        long big_sum = 0;

        for (int i = 1; i <= 100; i++) {

            // ✅ 每一項：n*(n+2)*(n+4)*(n+6)
            long item = (long)i * (i + 2) * (i + 4) * (i + 6);

            // ✅ 符號規律：+ - - +
            switch (i % 4) {
                case 1:
                    sign = 1;
                    break;
                case 2:
                    sign = -1;
                    break;
                case 3:
                    sign = -1;
                    break;
                case 0:   // ⭐ 修正重點
                    sign = 1;
                    break;
            }

            // ✅ 累加
            big_sum += sign * item;
        }

        System.out.println("結果 = " + big_sum);
    }
}

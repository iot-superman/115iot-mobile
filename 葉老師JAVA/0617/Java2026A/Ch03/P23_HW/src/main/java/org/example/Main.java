package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import static java.lang.Math.pow;

public class Main {



    static void main() {
        /*
          sum = 1 * 2 ^2 +2*3^2 + ....  10*11^2
         */

        int sum=0;
        int i;
        for( i=1;i<=10;i++){

       sum = sum+  i *((i+1)*(i+1));
//        sum = (int) (sum+  i *(pow(i+1,2)));

        }
        System.out.println("sum:"+sum);
    }
}

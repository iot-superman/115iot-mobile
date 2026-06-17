package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static boolean isPrimeF(int n) {

        if (n < 2) return false;   // <2  ,1 不是質數

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;      // 只要找到一個因數就結束  ==>非質數
            }
        }

        return true;    //其他都是為質數
    }
    static void main() {

        ArrayList<Integer> arrayList = new ArrayList<Integer>(); // ✅ 泛型

        int sum = 0; int c=0;

        boolean isPrime=true;

        for(int i=3; i<=100; i+=2){
            isPrime = true;
            for (int j=2; j<=i-1; j++){
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) arrayList.add(i);
        }



//        for (int i = 2; i <= 1000; i++) {
//            if (isPrimeF(i)) {
//                arrayList.add(i);
//                c++;   // ✅ 順便累加總和
//            }
//        }

        System.out.println("2~100 的質數如下：");

        for (int e : arrayList) {
            System.out.print(e + "\t");
        }

        System.out.println(); // 換行

        // ✅ 額外輸出總和（你原本有宣告 sum 但沒用）
        System.out.println("質數有幾個 = " + arrayList.size());

    }
}

package org.example;

//import java.util.HashSet;
import java.util.TreeSet;

public class Main {
    static void main() {
//        HashSet<Integer> hashSet = new TreeSet<>();   //集合沒有順序，內容值不會重複
        TreeSet<Integer> hashSet = new TreeSet<>();   //集合沒有順序，內容值不會重複//內容會由小到排序

        int[][]lottos = new int[4][6];

        // 2. 第一步：生成所有號碼並存入二維陣列
        for(int i=0; i<=3; i++) {
            hashSet.clear();
            //樂透 6個號
            // 確保生成 6 個不重複的號碼
            while (hashSet.size() <= 5) {    // 0....5
                hashSet.add((int) (Math.random() * 49 + 1));   //1~4
            }
            int j = 0;
            // 確保生成 6 個不重複的號碼 填入Array中
            for (int e : hashSet) {
                lottos[i][j] = e;
                j++;
            }
        }

//// 3. 第二步：分開處理輸出，避免迴圈嵌套混亂
        for (int i = 0; i < 4; i++) {
            System.out.print("第 " + (i + 1) + " 組號碼: ");
            for (int j = 0; j < 6; j++) {
                System.out.print(lottos[i][j] + "\t");
            }
            System.out.println(); // 換行
        }

//            for(int i = 0; i<=3 ; i++) {
//                for (int j = 0; j <= 5; j++) {
//                    System.out.print(lottos[i][j] + "\t");
//
//                }
//        System.out.println();
//            }


    }
}


package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // 建立 ArrayList
        var aList = new ArrayList<Integer>(
                Arrays.asList(1, 13, 17, 19, 2, 3, 1, 1, 1, 11)
        );

        System.out.println("aList = " + aList);

        // 移除 index 1
        aList.remove(1);
        System.out.println(aList);

        // 移除 index 4~7
        aList.subList(4, 8).clear();
        System.out.println(aList);

        // 移除 >10
        aList.removeIf(e -> e > 10);  //lambda 表達式：e -> e > 10 // Anonymous Function //if pramter only one ()可以省略
        System.out.println(aList);

        aList.removeAll(Arrays.asList(1,2)); // 移除所有 1 和 2
        System.out.println(aList);
        aList.addAll(Arrays.asList(1,13,17,19,2,3,5,1,1,11));
        System.out.println(aList);
        // 查詢元素位置
        System.out.printf("Index of 17:%d ,Index of 1:%d\n", aList.indexOf(17), aList.indexOf(11)); //17 is auto box to Integer, 1 is auto box to Integer


    }
}
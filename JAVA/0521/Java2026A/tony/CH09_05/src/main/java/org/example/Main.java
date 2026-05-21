package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

class Math {
    //overload
    void addition(long x, int y) { // 2個數字加法
        System.out.println((x + y));
    }

    void addition(double x, double y, double z) { // 3個數字加法
        System.out.println((x + y + z));
    }

    //縮小
//	void addition(int x, double y, double z) { // 3個數字加法
//		System.out.println((x + y + z));
//	}

}

public class Main {
    static void main() {
        Math A = new Math(); // Math類別物件
        A.addition(5, 10); // 第1個int升級為long
        A.addition(5, 10, 15);//只能放大，不能縮小

        
        ArrayList<Integer> aList =null;
        aList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println(aList);



        //縮小
        //A.addition(5.0, 10, 15);//只能放大，不能縮小
    }
}

package org.example;

class Math {  //overload
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
    public static void main(String[] args) {
        Math A = new Math(); // Math類別物件
        A.addition(5, 10); // 第1個int升級為long
        A.addition(5, 10, 15);//只能放大，不能縮小

        //縮小
        //A.addition(5.0, 10, 15);//只能放大，不能縮小
    }
}

package org.example;

import java.util.Scanner;

/*

第二次作業(各種函數類型: 了解函數的建立方法)
1.	定義一個static double getArea(double base, double height)函數
2.	由Scanner input, base=123.4, height=234.5
3.	呼叫該函數, 並印出三角形面積為多少?


 */
public class Main {
    static void main() {
    double base;
    double height;
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please input base:");
    base = scanner.nextDouble();
    System.out.println("Please input height:");
    height = scanner.nextDouble();
    System.out.printf("Base: %.2f,Height:%.2f  Aera: %.2f",base,height,getArea(base,height));

        
    }
    static  double getArea(double base, double height   ){
        return  (base * height /2.0);
    }
}

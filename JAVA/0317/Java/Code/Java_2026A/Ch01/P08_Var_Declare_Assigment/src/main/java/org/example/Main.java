package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        int x = 10; //int: Type,x:variable name, 10: value of int //Comment, 註解，給程式人員看的，不會被硬體執行
                    //x:Declare a var, named as x; 宣告一個變數，名稱叫做x;  =10 Assigment, 設定一個數值；
        int y;  //Declare a ver ,named y;
        y=100;  //Assignemnt 100 to y;

        System.out.println("x= "+ x +" And y = " + y);

        short _z =123; //變數名稱可以是底線開頭, 1, 字母, 2_ ,3$
        System.out.println("_z" + _z);

        short _1z =456; //變數名稱不可以是數字開頭1z
        System.out.println("_z" + _1z);

        float $salary;
        $salary = 123.456f;       //float 數值，要加f
        System.out.println("salary ="+$salary);
        double $mySalary;
        $mySalary= 456.789;       //double 數值，不用加f
        System.out.println("mySalary ="+$mySalary);
    }
}

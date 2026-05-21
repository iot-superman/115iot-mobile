package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        System.out.println(A.printA());
        A.printData();
        System.out.println(new A().printB());
    }

}


class A{
    static int s_a; //statifc attribute;
    int i_b=12;      //instance attribute

    static void printData(){

        System.out.println("s_a =" +s_a );
        System.out.println("i_b= "+new A().printB());

    }

    static String printA(){
//        return  "a= " +s_a + i_b;       //x  static  不可以直接i_b (instance) ,沒有new實體還沒做出來
//
//        return  "a= " +s_a + new A().i_b;   //         加上 new 產生了物件的實體可以
         return  "a =" +s_a;
    }

    String printB(){
       return  "b ="+ i_b;
    }

}

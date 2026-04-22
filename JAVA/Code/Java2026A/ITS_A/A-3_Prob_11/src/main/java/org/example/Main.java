package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        String userName ="JavaCoffee";
        userName = userName.substring(0, 5); //A
        String outStr =  String.format("%s字有%d個字元", userName, userName.length());
        //                              //B   //C                        //D
        System.out.println(outStr);

    }

}

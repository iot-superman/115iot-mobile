package org.example;

import java.util.function.LongFunction;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        System.out.printf("byte的數值範圍%d~%d %n",Byte.MIN_VALUE,Byte.MAX_VALUE);      //第一個%d 對應到第1個變數，第二個對應第2變數 (%n表示跳下行)
        System.out.printf("short的數值範圍%d~%d %n",Short.MIN_VALUE,Short.MAX_VALUE);   //第一個%d 對應到第1個變數，第二個對應第2變數 %n表示跳下行
        System.out.printf("int的數值範圍%d~%d %n",Integer.MIN_VALUE,Integer.MAX_VALUE); //第一個%d 對應到第1個變數，第二個對應第2變數 %n表示跳下行
        System.out.printf("long的數值範圍%d~%d %n", Long.MIN_VALUE,Long.MAX_VALUE);      //第一個%d 對應到第1個變數，第二個對應第2變數 %n表示跳下行


    }
}

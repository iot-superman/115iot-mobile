package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    char V1 = 65;
    System.out.println(V1);

    long V2 = 20;
    System.out.println(V2);

    float V3 =  new Float("-50.0"); //From Float to float: Unboxing From float to Float: Boxing
    System.out.println(V3);
//    //(X) : Short短整數不可放入小數點，會發生錯誤
//    short V4 = new Short("65.0"); //From Short to short: Unboxing From short to Short: Boxing
//    System.out.println(V4);

    ArrayList<Short> arrayList = new ArrayList<>();  //ArrayList只能放入物件(參考形態)，不能放入基本資料型態(short)，所以要使用Short包裝類別來存取short值
    arrayList.add((short) 123);

    }
}

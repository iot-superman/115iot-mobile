package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int i;          //32位元的整數類型，佔用4個字節（32位元），範圍從-2,147,483,648到2,147,483,647
    byte bt = 10;   //8位元的整數類型，佔用1個字節（8位元），範圍從-128到127\
    char ch = 'A'; //16位元的字符類型，佔用2個字節（16位元），範圍從0到65535，通常用於表示Unicode字符
    float f;       //32位元的單精度浮點數類型，佔用4個字節（32位元），範圍約為1.4E-45到3.4E+38，精度約為7位十進制數

    i = bt;          //將 byte 類型的變量 bt 賦值給 int 類型的變量 i。這是一個自動類型轉換（implicit type conversion），因為 int 類型的範圍比 byte 類型大，所以不會丟失數據。

    System.out.println("i="+i +" "+"bt="+bt);

    i = ch;
    System.out.println("i="+i +" "+"ch="+ch);

    f = bt;
    System.out.println("f="+f +" "+"bt="+bt);


    ch =0x42; // 0x42 是十六進制表示，對應的十進制值是 66，對應的字符是 'B'。

    i = ch;
    System.out.println("i="+i +" "+"ch="+ch);



    }
}

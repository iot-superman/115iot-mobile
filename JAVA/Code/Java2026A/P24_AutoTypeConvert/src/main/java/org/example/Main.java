package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int i;
    double db;
    float f;
    long ll;
    short st=10;
    byte bt=5;

    db = (i = 10) +3.3 ; // int + double  =>double
    System.out.println("i="+i+" "+"db="+db);
    f = i +5.5f; // int + float =>float
    System.out.println("i="+i+" "+"f="+f);
    ll = i + 100L; // int + long =>long
    System.out.println("i="+i+" "+"ll="+ll);
    i = st + bt; // short + byte =>int  //  short 和 byte 在進行算術運算時會被自動提升為 int 類型，因此結果是 int 類型。
    System.out.println("i="+i+" "+"st="+st+" "+"bt="+bt);



    }
}

package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int [] score = {90,79,92};
    int [] myscore = score;      //myscore is alias of scoe;myscore是scoret的別名；
                                            //A                   //B
    for (int e:score) System.out.print(e+"\t"); System.out.println();    //e is local variable;e 是區域變數
    for (int e:myscore) System.out.print(e +"\t");System.out.println();  //f is local variable;f 是區域變數

        myscore[2] =101;

    for (int e:score) System.out.print(e+"\t"); System.out.println();    //e is local variable;e 是區域變數
    for (int e:myscore) System.out.print(e +"\t");System.out.println();  //f is local variable;f 是區域變數

    }
}

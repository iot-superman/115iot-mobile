package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static int extra=300;
                                      //10          //true     //100
    public static int changePoint(int point ,Boolean bonus ,int extra){
            if (bonus == true) {
                //point + extra; //paramter extra, not static attribute extra ; //110
                 point += Main.extra; //paamter exta ,not static attribute extra ; //310
            }
            return point;
        }
        static  void  main(){
        Boolean boulean = true;
        int point = 10;
        int newPoint = changePoint(point, boulean, 100);
        System.out.println(point);    //10
        System.out.println(point);   //110
        }
    }



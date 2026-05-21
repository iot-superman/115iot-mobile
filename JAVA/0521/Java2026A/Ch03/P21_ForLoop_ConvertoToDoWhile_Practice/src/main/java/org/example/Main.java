package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int i=1, sum=0; //1
        //   1   //2   //4
        for(i=1;i<=99;i+=2){
            sum+=i; //3     //1+3+5....+99
        }
        System.out.println("sum= "+ sum);

        for (i=1;i<=99;i+=2){
            int item = 2*i -1;
            sum += item;
        }
        System.out.println("via for ,sum=" + sum);

        i=1;
        sum=0; //1
        while (i<=99){ //2
            sum+=i;    //3
            i  +=2;    //4
        }
        System.out.println("via whiel,sum="+sum);

        i=1;
        sum=0;  //1
        do{
             sum+=i;    //3
             i  +=2;    //4
        }while (i<=99); //2
        System.out.println("via do-while ,sum= "+sum);



    }
}

package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        int i, sum;
        sum =0;
        //1    //2     //4
        for(i=1;i<=10;i++){
            sum = sum +i;       //3:
        }
        System.out.println("Sum form 1 to 10 via for-loop:  " +sum);

        sum = 0;
        i = 1;
        while (i<=10){
            sum =sum +i;
            i++;
        }
        System.out.println("Sum form 1 to 10 via while-loop:  " +sum);

        sum =0;
        i=1;
        do{
            sum=sum+i;
            i++;
        }while (i<=10);

        System.out.println("Sum form 1 to 10 via do-while-loop: " +sum);

    }
}

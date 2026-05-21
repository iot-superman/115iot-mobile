package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int i,j;
        //1  //2 //4
    for(i=1;i<=9;i++){
          //i1  /i2 //i4
        for(j=1;j<=9;j++){
            System.out.printf("%d x %d =%d\t",i,j,i*j); //i3
        }
        System.out.println();
    }
    }
}

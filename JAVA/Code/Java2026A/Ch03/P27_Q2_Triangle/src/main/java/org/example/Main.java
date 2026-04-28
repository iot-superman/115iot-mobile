package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        int r,c;
        for(r=0;r<=4;r++){
            for(c=0;c<=4;c++){
                if (c<4-r){
                    System.out.print(" ");
                }else{
                    System.out.print("*");
                }
            }
            IO.println();
        }
    }
}

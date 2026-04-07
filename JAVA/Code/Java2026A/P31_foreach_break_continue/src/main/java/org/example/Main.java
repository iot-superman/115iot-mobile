package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
      int[] a = {0,1,2,3,4,5,6,7,8,9};
        for (int e:a){
            if (e==8) break;   //跳開最靠近的回圈
            if (e==4) continue;  //回跳回圈下一個起點
            System.out.print(e);
        }

        IO.println("\nThis is last line!!!");

    }
}

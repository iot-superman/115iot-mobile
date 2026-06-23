package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    int data0=10,data1=20;
    System.out.println("Before call swap: "+data0+" "+data1);
    swap(data0,data1);
    System.out.println("After call swap: "+data0+" "+data1);

    System.out.println("------------------------------------");
    int[] data = {10,20};
    System.out.println("Before call swap: "+data[0]+" "+data[1]);
    sswap(data);
    System.out.println("Before After swap: "+data[0]+" "+data[1]);
    }



    static  void swap(int data0,int data1){
        int tmp=data0;
        data0=data1;
        data1=tmp;
        System.out.println("Inside swap swap: "+data0+" "+data1);
    }

    static void sswap(int[] data){
        int tmp=data[0];
        data[0]=data[1];
        data[1]=tmp;
        System.out.println("Insdie swap: "+data[0]+" "+data[1]);
    }
}

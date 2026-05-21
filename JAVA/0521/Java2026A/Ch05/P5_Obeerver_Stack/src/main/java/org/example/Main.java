package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        int i=5;
        int j=2;
        int k = max(i,j);
        System.out.println("i ="+i +" j=" +j +" k= "+k);


    }
    static int max(int num1, int num2){
        int result;
        if (num1>num2)
            result = num1;
        else
            result = num2;
        return  result;
    }
}

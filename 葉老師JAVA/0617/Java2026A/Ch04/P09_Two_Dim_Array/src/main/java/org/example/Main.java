package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
     static int[][] x;     //Static 2Dim Attribute;belongs to class scope
     static int[][] y ={{11,22},{33,44,55,66}};
     static void main() {

        x = new int[2][];
        x[0]= new int[]{1,2,3};  //2 階陣例的出始化
        x[1] = new int[]{4,5};

        displayInfox();  //method
        System.out.println();
        x[0][1]=22;         //x[0][1]元素的修改;
        x[1][0]=44;         //x[1][0]元去的修改；

         displayInfox();  //method
         System.out.println();

         displayInfoy();
    }
    static void displayInfox(){

        for (int e:x[0]){
            System.out.print(e+"\t");
        }
        System.out.println();
        for (int e:x[1]){
            System.out.print(e+"\t");
        }
        System.out.println();
    }

    static void displayInfoy(){

        for (int e:y[0]){
            System.out.print(e+"\t");
        }
        System.out.println();
        for (int e:y[1]){
            System.out.print(e+"\t");
        }
        System.out.println();
    }
}

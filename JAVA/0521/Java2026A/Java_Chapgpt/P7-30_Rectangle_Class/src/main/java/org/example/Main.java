package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        int areaNum;
    /*
        Recrangle rec = new Recrangle();
    Scanner scanner = new Scanner(System.in);
    System.out.println("input your width:");
    rect.width = scanner.nextInt();
    System.out.println("input your lenght:");
    rect.lenght = scanner.nextInt();
      */
        Recrangle rect = new Recrangle(20,40);//1. Recrangle rect = new Recrangle(20,40)
    areaNum = rect.area();  //2.(B)areaNum = rect.area();

    System.out.printf("Width=%d Lenght=%d\n", rect.getWidth(),rect.getLenght()); //(3) rect.getWidth(),rect.getLenght()

    System.out.printf("Aerea is correct %b\n",areaNum == 800);

    }
}
class Recrangle {
    private int  width;    //私有
    private int  lenght;  //私有

    //Recrangle(){}

    Recrangle(int width ,int lenght){
          this.width = width;
          this.lenght = lenght;
      }

      public int area(){
            return  this.width * this.lenght;
      }
      public int getWidth(){
         return  width;
      }
     public int getLenght(){
        return  lenght;
    }
}
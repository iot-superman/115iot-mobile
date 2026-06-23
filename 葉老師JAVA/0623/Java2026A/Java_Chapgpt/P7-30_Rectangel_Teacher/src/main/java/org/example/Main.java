package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

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

        static void main() {
           int areaNum;

            Recrangle rect = new Recrangle(20,40);//1. Recrangle rect = new Recrangle(20,40)
            areaNum = rect.area();  //2.(B)areaNum = rect.area();

            System.out.printf("Width=%d Lenght=%d\n", rect.width,rect.lenght); //(3) rect.getWidth(),rect.getLenght()

            System.out.printf("Aerea is correct %b\n",areaNum == 800);

        }




}

}
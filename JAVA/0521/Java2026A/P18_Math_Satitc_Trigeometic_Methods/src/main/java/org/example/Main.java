package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        System.out.printf("Math.toDegrees(6.28) =%.3f\n ",Math.toDegrees(6.28));
        System.out.printf("Math.toDegrees(6.28) =%.3f\n ",Math.toDegrees(2*Math.PI));

        System.out.printf("Math.toDegrees(360.0) =%.3f\n ",Math.toRadians(360.0));  // 2PI
        System.out.printf("Math.toDegrees(45.0) =%.3f\n ",Math.toRadians(45.0));    //   1/4 PI

                                                            //Radians
        System.out.printf("Math.sin(90.0) =%.3f\n ",Math.sin(Math.PI/2));    //180度/2 =90度
        System.out.printf("Math.sin(45.0) =%.3f\n ",Math.cos(Math.PI/2));


        System.out.printf("Math.pow(2,3) =%.3f\n ", Math.pow(2,3));
        System.out.printf("Math.pow(2.2,3.3) =%.3f\n ", Math.pow(2.2,3.3));

        Random rand = new Random();
        rand.setSeed(123);
        ArrayList<Double> arrayList = new ArrayList<>();


        for(int i=0;i<5;i++){
            arrayList.add(rand.nextDouble());
        }

        for(double e:arrayList) System.out.print(e + "\t");
        System.out.println();
  }
}

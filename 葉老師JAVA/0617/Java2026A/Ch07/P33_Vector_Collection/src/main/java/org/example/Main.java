package org.example;

import java.util.Arrays;
import java.util.Vector;

 
public class Main {
    static void main() {

        Vector<String> vector = new Vector<>();
        vector.add("Eric");
        vector.add("Deniel");
        System.out.println(vector);
        Vector vector2 = new Vector(Arrays.asList(3,4,5,6));
        System.out.println(vector2);
        Vector vector3 = new Vector(Arrays.asList("Andy","Bill","Carol","David","Eddy"));
        System.out.println(vector3);
        vector3.set(3,"Daniel");
        System.out.println(vector3);
        vector3.clear();
        System.out.println(vector3);

    }
}





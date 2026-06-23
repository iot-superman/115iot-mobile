package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    Integer intArray[] = {1, 2, 3, 4, 5};
    Double doubleArray[] = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6};
    Character charArray[] = {'H', 'E', 'L', 'L', 'O'};
    displayArray(intArray);
    displayArray(doubleArray);
    displayArray(charArray);

    }

//    static void DisplayArray(Integer[] iArray) {  //OR
//        for (var e: iArray) System.out.print(e + "\t");
//        System.out.println();
//
//        System.out.println();
//    }
//    static void DisplayArray(Double[] dArray) { //OR
//        for (var e: dArray) System.out.print(e + "\t");
//        System.out.println();
//    }
//    static void DisplayArray(Character[] cArray) { //OR
//        for (var e: cArray) System.out.print(e + "\t");
//        System.out.println();
//    }

    static <T> void displayArray(T[] genericArray) {   //泛型方法 Generic Method

        for (var e: genericArray) System.out.print(e + "\t");
        System.out.println();
    }
}

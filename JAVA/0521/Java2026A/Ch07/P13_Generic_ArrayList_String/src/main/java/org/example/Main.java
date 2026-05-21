package org.example;

import java.util.ArrayList;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        ArrayList<String> aList = new ArrayList<>(Arrays.asList("Andy", "Bill", "Carol"));
        System.out.println(aList);

        aList.add("Eric");
        System.out.println(aList);
        aList.add(1,"inser at 1");
        System.out.println(aList);
        aList.set(1, "modify at 1");
        System.out.println(aList);
        aList.addAll(Arrays.asList("Grace","Rebert"));
        System.out.println(aList);
        aList.addAll(1,Arrays.asList("Ted","Vicky"));
        System.out.println(aList);
        System.out.println(aList.size());

    }
}

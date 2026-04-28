package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static void main() {
        ArrayList<String> aList = new ArrayList<>(Arrays.asList("Andy","Mary","Morris","Grace","Bill","Candy","Erice","Bruce"));
        System.out.println(aList);
        aList.remove(1);
        System.out.println(aList);

        aList.subList(4, 6+1).clear();
        System.out.println(aList);
            aList.removeIf(e -> e.length() > 5);
        System.out.println(aList);
        aList.removeAll(Arrays.asList("Andy","Bill"));
        System.out.println(aList);
        aList.addAll(Arrays.asList("Andy","Mary","Morris","Grace","Bill","Candy","Erice","Bruce"));
        System.out.println(aList);
        System.out.printf("index of \"Andy\": %s, Index of \"Andy\": %s\n", aList.indexOf("Andy"), aList.indexOf("Andy"));

        System.out.println(aList.get(2)); //Morris
        aList.set(2, "Maersk"); //Modify Morris to Maersk
        System.out.println(aList.get(2)); //Maersk




//        aList.add("Eric");
//        System.out.println(aList);
//        aList.add(1,"inser at 1");
//        System.out.println(aList);
//        aList.set(1, "modify at 1");
//        System.out.println(aList);
//        aList.addAll(Arrays.asList("Grace","Rebert"));
//        System.out.println(aList);
//        aList.addAll(1,Arrays.asList("Ted","Vicky"));
//        System.out.println(aList);
//        System.out.println(aList.size());

    }
}

package org.example;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        List<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry", "Apple"));
        System.out.println(list);

        Set<String> aSet = new HashSet<>(Arrays.asList("Cherry","Apple", "Banana", "Apple"));
        System.out.println(aSet);

        Set<String> tSet = new TreeSet<>(Arrays.asList("Cherry","Apple", "Banana", "Apple"));
        System.out.println(tSet);

        //lkList
        List<String> lkList = new LinkedList<>(Arrays.asList("Apple", "Banana", "Cherry", "Apple"));
        System.out.println(lkList);

        //HasHSet
        Set<String> hSet = new HashSet<>(Arrays.asList("Cherry","Apple", "Banana", "Apple"));
        System.out.println(hSet);
//BOOK: Page:12-2
        //TreeSet
        Set<String> tSet2 = new TreeSet<>(Arrays.asList("Cherry","Apple", "Banana", "Apple"));
        System.out.println(tSet2);

        //Map  無    序
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 1);
        map.put("Banana", 2);
        map.put("Cherry", 3);
        System.out.println(map);

        //TreeMap 有排    序
        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Apple", 1);
        treeMap.put("Banana", 2);
        treeMap.put("Cherry", 3);
        System.out.println(treeMap);


    }

}

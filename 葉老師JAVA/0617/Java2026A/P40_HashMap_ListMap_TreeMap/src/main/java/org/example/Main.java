//https://chatgpt.com/s/m_6a0ea6ce71b88191bb191c91e20e3ca7
package org.example;

import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        // Map: 建立一個可變的 HashMap，並放入一個鍵值對
        //HashMap: 無序的 Map，使用哈希表實現，提供快速的查找和插入操作
        Map<String,Integer> hashMap = new HashMap<>();
        hashMap.put("Apple", 3);
        hashMap.put("Banana", 2);
        hashMap.put("Cherry", 5);
        hashMap.put("Banana", 4);
        System.out.println(hashMap);

        //TreeMap:   有序的 HashMap，會根據鍵的自然順序（或自定義的比較器）來排序
        Map<String,Integer> treeMap = new java.util.TreeMap<>();
        treeMap.put("Apple", 3);
        treeMap.put("Banana", 2);
        treeMap.put("Cherry", 5);
        treeMap.put("Banana", 4);
        System.out.println(treeMap);

        //LinkedList 有序的 HashMap，會保持插入順序
        Map<String,Integer> linkedHashMap = new java.util.LinkedHashMap<>();
        linkedHashMap.put("Apple", 3);
        linkedHashMap.put("Banana", 2);
        linkedHashMap.put("Cherry", 5);
        linkedHashMap.put("Banana", 4);
        System.out.println(linkedHashMap);

    }
}

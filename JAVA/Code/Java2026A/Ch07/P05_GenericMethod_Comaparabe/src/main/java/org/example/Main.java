package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Integer intArray[] = {1, 2, 3, 4, 5};  //Auto Boxing:Primitives to Wrapper Class
        String strArray[] = {"Andy", "Bill", "Carol", "Eric", "David"};
        Character charArray[] = {'y', 'B', 'a', 'i', 'D'};  //y > D > i > B > a   ,ASCII: 121(0x79) > 68(0x44) > 105(0x69) > 66(0x42) > 97(0x61)
        System.out.println(findMax(strArray));
        System.out.println(findMax(charArray));
    }

           //<T>：定義一個泛型類型 T，這裡 T 是一個占位符，可以代表任何類型。
           //extends Comparable<T>：這是對 T 的約束，表示 T 必須實現 Comparable<T> 介面。這樣我們就可以使用 compareTo 方法來比較
                                    //return用的不同的 T 類型的對象。
    static <T extends Comparable<T>> T findMax(T[] array) {
        T max = array[0];
        for(var e:array){
            if(e.compareTo(max) > 0)max = e;
        }
        return max;
    }
}

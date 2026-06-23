package org.example;


import java.util.ArrayList;
import java.util.function.Consumer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //建立
        ArrayList<String> arr = new ArrayList<>();
        //add
        arr.add("aaa");
        arr.add("bbb");
        arr.add("ccc");
        System.out.println("contents:" + arr);
        arr.add(1, "eee");
        System.out.println("contents:" + arr);
        //修改
        arr.set(0, "ddd");
        System.out.println("contents:" + arr);
        //刪除
//        arr.remove("eee");
//        System.out.println("contents:"+arr);
//        arr.remove(2);
//        System.out.println("contents:"+arr);
        System.out.println(".............1............");
        //走訪
        //for
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
        System.out.println(".............2............");
        //foreach
        for (String data : arr) {
            System.out.println(data);
        }
        //forEach()
        System.out.println(".............3............");
        arr.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        //lambda
        System.out.println(".............4............");
        arr.forEach((s)->{
            System.out.println(s);
        });
        //or
        System.out.println(".............5............");
        arr.forEach((s)->System.out.println(s));

 }
}

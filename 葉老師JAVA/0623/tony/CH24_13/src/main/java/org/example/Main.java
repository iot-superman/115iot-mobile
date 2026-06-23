package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //建立
        HashMap<Integer,String> map1 = new HashMap<>();
        //新增
        map1.put(105,"明新科大");
        map1.put(102,"台灣科大");
        map1.put(103,"台北科大");
        map1.put(104,"清華大學");
        System.out.println(map1);
        //修改
        map1.put(104,"中央大學");
        System.out.println(map1);
        //刪除
        map1.remove(104);
        System.out.println(map1);
        //取值
        System.out.println("key=103:"+map1.get(103));
        //走訪
        System.out.println(".......1.........");
        //foreach
        for(Map.Entry<Integer,String> e: map1.entrySet()){
            Integer key = e.getKey();
            String value = e.getValue();
            System.out.println("key:"+key+",value:"+value);
        }
        //
        System.out.println(".......2.........");
        HashMap<String,String> map2 = new HashMap<>();
        map2.put("a101","Mary");
        map2.put("a102","Bill");
        map2.put("a103","Natasha");
        map2.put("a104","Joe");
        System.out.println(map2);
        //foreach
        for(Map.Entry<String,String> e:map2.entrySet()){
            String key = e.getKey();
            String value = e.getValue();
            System.out.println("key:"+key+",value:"+value);
        }
        System.out.println(".......3.........");
        HashMap<Object,Object> map3 = new HashMap<>();
        map3.put(123,"aaaa");
        map3.put("a1002","bbbb");
        map3.put("a103","Natasha");
        map3.put(false,"Joe");
        //foreach
        for(Map.Entry<Object,Object> e:map3.entrySet()){
            Object key = e.getKey();
            Object value = e.getValue();
            System.out.println("key:"+key+",value:"+value);
        }
        System.out.println(".......4.........");
        //forEach()
        map3.forEach(new BiConsumer<Object, Object>() {
            @Override
            public void accept(Object o, Object o2) {
                System.out.println("key:"+o+",value:"+o2);
            }
        });
        //lambda
        System.out.println(".......5.........");
        map3.forEach((k,v)->{
            System.out.println("key:"+k+",value:"+v);
        });





    }
}

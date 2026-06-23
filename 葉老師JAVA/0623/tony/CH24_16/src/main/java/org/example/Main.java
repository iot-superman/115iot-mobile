package org.example;

import java.util.HashMap;
import java.util.Map;

class Phone{
    private String pname;
    private int price;
    public Phone(String pname, int price) {
        this.pname = pname;
        this.price = price;
    }
    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        HashMap<String, Phone> p = new HashMap<>();
        p.put("iphone",new Phone("哀鳳",40000));
        p.put("samsung",new Phone("三星",35000));
        p.put("oppo",new Phone("歐珀",20000));
        //修改
        p.get("oppo").setPrice(15000);
        //刪除
        //p.remove("samsung");
        System.out.println("........1.........");
        //foreach
        for(Map.Entry<String, Phone> e: p.entrySet()){
            String key = e.getKey();
            Phone value = e.getValue();
            System.out.println(key+":"+value.getPname()+":"+value.getPrice());
        }
        System.out.println("........2.........");
        //lambda
        p.forEach((k,v)-> {
            System.out.println(k+":"+v.getPname()+":"+v.getPrice());
        });

    }
}

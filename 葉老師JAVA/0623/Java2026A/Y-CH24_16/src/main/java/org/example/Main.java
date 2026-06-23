package org.example;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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

public class Main {
    static void main() {
        //建立HashMap 物件，key為String，value為Phone物件(Object)
        HashMap<String, Phone> p = new HashMap<>();
        p.put("iPhone", new Phone("愛瘋", 46000));
        p.put("Samsung", new Phone("三星", 20000));
        p.put("Oppo", new Phone("歐珀", 15000));

        p.get("Oppo").setPrice(12345); //修改 Oppo價格

        System.out.println(".............1...........");
        //foreach
        for(Map.Entry<String, Phone> e : p.entrySet()){
            System.out.println("KEY:"+ e.getKey() + ", " + e.getValue().getPname() + " " + e.getValue().getPrice());
        }
        System.out.println(".............2...........(lambda)");
        //lambda
        p.entrySet().forEach(e ->
        System.out.println("KEY:"+ e.getKey() + ", " + e.getValue().getPname() + " " + e.getValue().getPrice()));

        System.out.println(".............3..........(lambda2)");

        p.forEach((k, v) -> {
            System.out.println("KEY:"+ k + ", " + v.getPname() + " " + v.getPrice());
        });
    }
}

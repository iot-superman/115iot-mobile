package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        String s1 = "Andy";
        String s2 = "Bill";

        int result=0;
        result = s1.compareTo(s2);
        System.out.println(result);

        Integer i1=123;
        Integer i2=456;

        result = i1.compareTo(i2);
        System.out.println(result);

        Rect r1 = new Rect(12,34);
        System.out.println("Area: " +r1.getArea());
        Rect r2 = new Rect(22,34);
        System.out.println("Area: " +r2.getArea());



        // r1 與 r2 比較
        result = r1.compareTo(r2);
        System.out.println(result);




    }
}

class  Rect implements Comparable<Rect>{    //介面的繼承使用 implements
    int length;
    int width;
    int getArea(){
        return  length*width;
    }
    Rect(int length, int width){
        this.length = length;
        this.width = width;
    }

    @Override                       //compareTo() method is used to compare the current object with the specified object and returns an integer value based on the comparison.
    public int compareTo(Rect o) {
        int res = 0;

        if ( getArea() > o.getArea()) {   //面積比傳入的物件面積大
            return res =1;
        } else if (getArea() == o.getArea()) {
            res = 0;
        }else {
            res = -1;
        }
        return res;
    }
}
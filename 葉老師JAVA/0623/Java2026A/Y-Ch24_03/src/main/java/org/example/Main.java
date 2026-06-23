package org.example;

import java.util.ArrayList;
import java.util.function.Consumer;

class Student {
    private String name;
    private int no;
    private int socre;

    public Student(String name, int no, int socre) {
        this.name = name;
        this.no = no;
        this.socre = socre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getSocre() {
        return socre;
    }

    public void setSocre(int socre) {
        this.socre = socre;
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        //建立
        ArrayList<Student> arr = new ArrayList<>();
        //新增
        //原始
        //Student t = new Student("John",1,100);
        //arr.add(t);
        //匿名物件
        arr.add(new Student("John", 1, 100));
        arr.add(new Student("Mary", 8, 80));
        arr.add(new Student("Tom", 4, 90));

        //走訪
        for(Student s:arr) {
            System.out.println(s);
        }

        for(Student data:arr){
            System.out.println("no:"+data.getNo()+" name:"+data.getName()+" score:"+data.getSocre());
        }
        //修改
//        arr.get(0).setName("Bill");
//        System.out.println(arr);
//        System.out.println("........1........");
//        //走訪
//        //foreach
//        for(Student data:arr){
//            System.out.println("no:"+data.getNo()+" name:"+data.getName()+" score:"+data.getSocre());
//        }

//        //刪除
//        arr.remove(1);
//        System.out.println(arr);
//
//        for(Student data:arr){
//            System.out.println("no:"+data.getNo()+" name:"+data.getName()+" score:"+data.getSocre());
//        }
        //forEach()
        System.out.println();
        arr.forEach(new Consumer<Student>() {
            @Override
            public void accept(Student student) {
                System.out.println("no:" + student.getNo() + " name:" + student.getName() + " score:" + student.getSocre());
            }
        });

        //lambda
        System.out.println(".....3......(lambda)");
        arr.forEach(student -> {
            System.out.println("no:"+student.getNo()+" name:"+student.getName()+" score:"+student.getSocre());
        });


    }
}

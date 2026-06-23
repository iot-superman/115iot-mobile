package org.example;

import java.util.ArrayList;
import java.util.function.Consumer;

class Student{
    private String sname;
    private int no;
    private int socre;

    public Student(String sname, int no, int socre) {
        this.sname = sname;
        this.no = no;
        this.socre = socre;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
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
        // 原始
        // Student t = new Student("John",1,100);
        // arr.add(t);
        //匿名物件
        arr.add(new Student("John",1,100));
        arr.add(new Student("Mary",8,80));
        arr.add(new Student("Tom",4,90));
        //修改
        arr.get(0).setSname("Bill");
        //System.out.println(arr);
        //刪除
//        arr.remove(1);
        System.out.println("......1.......");
        //走訪
        //foreach
        for(Student data:arr){
            System.out.println("no:"+data.getNo()+",name:"+data.getSname()+",score:"+data.getSocre());
        }
        System.out.println("......2.......");
        //forEach()
        arr.forEach(new Consumer<Student>() {
            @Override
            public void accept(Student student) {
                System.out.println("no:"+student.getNo()+",name:"+student.getSname()+",score:"+student.getSocre());
            }
        });
        //lambda
        System.out.println("......3.......");
        arr.forEach((e)->{
            System.out.println("no:"+e.getNo()+",name:"+e.getSname()+",score:"+e.getSocre());
        });

    }
}

package org.example;

import java.util.ArrayList;

class Student {
    private String name;
    private int no;
    private int score;

    public Student(String name, int no, int score) {
        this.name = name;
        this.no = no;
        this.score = score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public int getNo() {
        return this.no;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", no=" + no +
                ", score=" + score +
                '}';
    }
}

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
    }
}

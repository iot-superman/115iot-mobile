package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Student s1 = new Student("Andy",95);
        s1.printInfo();
        Student s2 = new Student();
        s2.printInfo();
        Student s3 = new Student(67,"Carol");
        s3.printInfo();

    }
}

class Student{
    String name;
    int score;
    Student(String name,int score){
        this.name = name;
        this.score = score;
    }
    Student(int score,String name){
        this(name,score);
    }

    Student(){
        this("Bill",78);
    }
    void printInfo(){
        System.out.println("Student name: " + name + " score: " + score);
    }
}
        

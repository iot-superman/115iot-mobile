package org.example;

class Student{
    public String sno;
    public String sname;
    public void iam(){
        System.out.println("I am " + sno + ":" + sname + ", 3Q");
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Student s1 = new Student();
        s1.sno = "a1001";
        s1.sname = "John";
        s1.iam();

        Student s2 = new Student();
        s2.sno = "b1002";
        s2.sname = "Mary";
        s2.iam();
    }
}

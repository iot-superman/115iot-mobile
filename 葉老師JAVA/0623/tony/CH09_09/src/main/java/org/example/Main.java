package org.example;

class Student{
    private String sno;
    private String sname;
    private int score;//加入
    public String getSno() {
        return "我的學號是:"+sno;
    }
    public void setSno(String sno) {
        this.sno = sno;
    }
    public String getSname() {
        return sname+"先生，請多多指教!";
    }
    public void setSname(String sname) {
        this.sname = sname;
    }
    public void setScore(int score){//加入
        if (score<0){
            score=0;
        }
        if (score>100){
            score=100;
        }
        this.score = score;
    }
    public int getScore(){//加入
        return score;
    }
    public String iam(){
        return ("I am "+sno+":"+sname+"，3Q!");
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Student obj1 = new Student();
        obj1.setSname("Bill");
        obj1.setSno("A10101");
        System.out.println(obj1.iam());
        System.out.println(obj1.getSno());
        System.out.println(obj1.getSname());
        //obj1.sname = "aaa";//false
        obj1.setScore(500);
        System.out.println(obj1.getScore());
    }
}

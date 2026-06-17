package org.example;

/**
 * 學生類別
 */
class Student {

    // 封裝資料（Private）
    private String sno;     // 學號
    private String sname;   // 姓名
    private int score;      // 成績

    /**
     * 設定學號
     */
    public void setSno(String sno) {
        this.sno = sno;
    }

    /**
     * 取得學號
     */
    public String getSno() {
        return "我的學號是 " + sno;
    }

    /**
     * 設定姓名
     */
    public void setSname(String sname) {
        this.sname = sname;
    }

    /**
     * 取得姓名
     */
    public String getSname() {
        return sname + "先生，請多多指教！";
    }

    /**
     * 設定成績
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * 取得成績
     */
    public int getScore() {
        return score;
    }

    /**
     * 自我介紹
     */
    public String iam() {
        return "I am "
                + sno
                + ":"
                + sname
                + ", score="
                + score;
    }
}

/**
 * 主程式
 */
public class Main {

    public static void main(String[] args) {

        Student obj1 = new Student();

        // 設定學號
        obj1.setSno("A10101");

        // 設定姓名
        obj1.setSname("Bill");

        // 設定成績
        obj1.setScore(500);

        // 輸出資料
        System.out.println(obj1.iam());

        System.out.println(obj1.getSno());

        System.out.println(obj1.getSname());

        System.out.println("成績：" + obj1.getScore());
    }
}
package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
    String msg ="";
        level ="乙";
        if (level=="甲"){
        msg = "超越標準";
    }else if (level=="乙"){
        msg = "達到標準";

    }else {
        msg = "需要改進";
    }
        System.out.println(msg);


        msg="";
        level ="乙";
        switch (level) {
            case "甲":
                msg = "超越標準";
                break;
            case "乙":
                msg = "達到標準";
                break;
            default:
                msg = "需要改進";
                break;
        }
        System.out.println(msg);



}

    static String level = "甲";
}

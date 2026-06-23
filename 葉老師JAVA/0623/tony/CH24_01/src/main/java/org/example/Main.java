package org.example;

class MyData {
    private Integer obj1;// 整數資料
    private Double obj2;// 浮點數資料
    //overload，多形
    void setobj(Integer obj) {
        this.obj1 = obj;					// 設定整數
    }
    void setobj(Double obj) {
        this.obj2 = obj;					// 設定整數
    }
    //取得資料
    Integer getobj1() {
        return this.obj1;				// 回傳整數
    }
    Double getobj2() {
        return this.obj2;				// 回傳整數
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        MyData m = new MyData();		// 建立物件
        m.setobj(10);
        System.out.println(m.getobj1());	// 列印整數值

        m.setobj(12.3);
        System.out.println(m.getobj2());	// 列印浮點數
    }
}

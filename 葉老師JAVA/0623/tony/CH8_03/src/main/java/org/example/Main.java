package org.example;

//類別
class MyClass{
    public String text = "ABC";  //屬性
    public void clear() {
        text="";
    }
}



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        MyClass obj = new MyClass();  //類別透過new物件化成變數
        //取值
        System.out.println("text="+obj.text);
        //修改
        obj.text = "DEF";
        System.out.println("text="+obj.text);
        MyClass oop = new MyClass();
        System.out.println("text="+oop.text);
        obj.clear();
        System.out.println("text="+obj.text);
    }
}

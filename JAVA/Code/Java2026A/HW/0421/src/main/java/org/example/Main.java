package org.example;

// 主程式
public class Main {

    // ⚠️ 正確入口寫法
    public static void main(String[] args) {

        // =====================================================
        // 🔷 第一種：name=String，id=Integer
        // =====================================================
        Person<String, Integer> p1 =
                new Person<>("Alice", 123);

        p1.showInfo();  // 呼叫方法

        // =====================================================
        // 🔷 第二種：name=String，id=String
        // =====================================================
        Person<String, String> p2 =
                new Person<>("Bob", "456");

        p2.showInfo();
    }
}


// =====================================================
// 🔷 泛型類別（Multiple Generic Types）
// =====================================================
// 定義一個泛型類別 Person，使用兩個型別參數 A 和 I
// <A, I> → 兩個型別參數
// A = name 的型別
// I = id 的型別
class Person<A, I> {

    private A name;  // 名字型別（可變）
    private I id;    // ID 型別（可變）

    // 建構子
    public Person(A name, I id) {
        this.name = name;
        this.id = id;
    }

    // 顯示資料
    void showInfo() {
        System.out.println("Name: " + name + ", ID: " + id);
    }
}
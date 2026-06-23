package org.example;

// 主程式入口
public class Main {

    // ⚠️ 正確 Java 入口應為：public static void main(String[] args)
    // 這裡假設是教學簡化版
    static void main() {

        // ===== 第一段：固定型別（Integer）=====
        // Box 已經「寫死」只能存 Integer
        Box box = new Box();
        box.store(456);       // 存入 Integer
        box.showInfo();       // 輸出結果

        // ===== 第二段：泛型型別（使用時決定）=====
        // GBox<T> 在建立物件時指定型別
        GBox<String> gbox = new GBox<>();  // T = String
        gbox.store("Garol");               // 存入 String
        gbox.showInfo();                   // 輸出結果
    }
}


// ======================================================
// 🔷 泛型介面（Generic Interface）
// ======================================================

// <T> = 型別參數（Type Parameter）
// 代表「這個介面可以套用不同型別」
interface IGCollection<T> {

    // 存資料（型別由 T 決定）
    void store(T item);

    // 顯示資料
    void showInfo();
}


// ======================================================
// 🔷 實作方式 1：固定型別（Integer）
// ======================================================

// 👉 這裡直接指定 T = Integer
// 所以這個類別「只能處理 Integer」
class Box implements IGCollection<Integer> {

    int myBox;  // 實際存的是 int（Integer 會自動拆箱）

    @Override
    public void store(Integer item) {
        // 自動拆箱（Integer → int）
        myBox = item;
    }

    @Override
    public void showInfo() {
        System.out.println("myBox: " + myBox);
    }
}


// ======================================================
// 🔷 實作方式 2：泛型類別（Generic Class）
// ======================================================

// 👉 類別本身也宣告 <T>
// 👉 並且實作 IGCollection<T>
// 👉 型別「延後到使用時決定」
class GBox<T> implements IGCollection<T> {

    T myBox;  // 型別不固定，依使用者決定

    @Override
    public void store(T item) {
        myBox = item;
    }

    @Override
    public void showInfo() {
        System.out.println("myBox: " + myBox);
    }
}
package org.example;

import java.util.ArrayList;
import java.util.Arrays;

// 主程式
public class Main {

    // ⚠️ 正確入口應為 public static void main(String[] args)
    static void main() {

        // =====================================================
        // 🔷 建立 ArrayList（泛型：Integer）
        // =====================================================

        // var = 型別推斷（Java 10+）
        // Arrays.asList(...) → 先建立固定長度 List
        // 再轉成 ArrayList（可增減元素）
        var aList = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));

        // 明確寫型別（Double）
        ArrayList<Double> bList =
                new ArrayList<>(Arrays.asList(1.23, 2.34, 3.45, 4.56));

        // =====================================================
        // 🔷 印出初始內容
        // =====================================================
        System.out.println("aList = " + aList);
        System.out.println("bList = " + bList);

        // =====================================================
        // 🔷 add(E e) → 在尾端新增元素
        // =====================================================
        aList.add(6);   // 加到最後
        System.out.println("aList add(6) → " + aList);

        // =====================================================
        // 🔷 add(index, element) → 指定位置插入
        // =====================================================
        aList.add(3, 12);  // 在 index=3 插入 12
        // 原 index=3 之後的元素全部往後移
        System.out.println("aList add(3,12) → " + aList);

        // =====================================================
        // 🔷 set(index, element) → 修改指定位置
        // =====================================================
        aList.set(3, 24);  // 將 index=3 改成 24（覆蓋）
        System.out.println("aList set(3,24) → " + aList);

        // =====================================================
        // 🔷 addAll(index, collection) → 批次插入
        // =====================================================
        bList.addAll(1, Arrays.asList(15.67, 16.78, 17.89));

        // 從 index=1 開始插入三個元素
        // 原本的元素會往後移
        System.out.println("bList addAll → " + bList);
        bList.remove(2);  // 移除 index=2 的元素（16.78）
        System.out.println("bList remove(2) → " + bList);
    }
}
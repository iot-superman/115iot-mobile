package org.example;

import java.util.ArrayList;
// 請實作一個 Stack（LIFO）資料結構，並使用 ArrayList 來儲存元素。Stack 應該支援以下操作：
//
// １. pop()：從 Stack 的開頭(頂端)取出元素並返回該
// ２. push(item)：將元素放到 Stack 的開頭(頂端)

public class Main {

    // ✅ Java 正確入口
    public static void main(String[] args) {

        // ✅ 修正型別名稱
        ArrayList<Integer> arrayList = new ArrayList<>();

        // 👉 用 push 來操作（不要直接操作 list，比較乾淨）
        push(arrayList, 123);
        push(arrayList, 456);
        push(arrayList, 789);

        // 👉 現在 stack = [789, 456, 123]
        System.out.println("Before pop: " + arrayList);

        Integer val = pop(arrayList);  // 取出一個
        System.out.println("Popped value: " + val);

        System.out.println("After pop: " + arrayList);
    }

    // ✅ pop：從「最前面」拿（你目前設計）
    static Integer pop(ArrayList<Integer> stack) {

//        // ⚠️ 防呆：避免空集合 error
//        if (stack.isEmpty()) {
//            System.out.println("Stack is empty!");
//            return null;
//        }

        int ip = 0;  // A 👉 index pointer（頂端）
//        int ip = stack.size() - 1; // 👉 從最後面拿（更符合 LIFO） -->方法二
        return stack.remove(ip); // B 👉 移除並回傳
    }

    // ✅ push：放到「最前面」
    static void push(ArrayList<Integer> stack, Integer item) {

        int ip = 0;  // C 👉 插入位置（頂端）
//        int ip = stack.size();   // 👉 從最後面插入（更符合 LIFO） -->方法二
        stack.add(ip, item); // D 👉 插入
    }
}
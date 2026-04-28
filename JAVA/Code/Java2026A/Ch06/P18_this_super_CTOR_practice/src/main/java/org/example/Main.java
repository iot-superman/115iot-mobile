package org.example;

public class Main {

    // ✅ main 一定要 public static void main(String[] args)
    public static void main(String[] args) {

        // 建立 D 物件（會一路呼叫 A → B → D 建構子）
        D obj_d = new D(2, 3, 4);

        // 輸出
        System.out.println("obj_d.a = " + obj_d.a);
        System.out.println("obj_d.b = " + obj_d.b);
        System.out.println("obj_d.d = " + obj_d.d);

        A obj_d2 = new D(5, 6, 7);     //  D 也是A Class  (//人類 也是 靈長類）
        System.out.println("obj_d2.a = " +obj_d2.a);
        System.out.println("obj_d2.b = " +((B)obj_d2).b); //D　強制轉型為Ｄ　type class
        System.out.println("obj_d2.d = " +((D)obj_d2).d);

        A obj_c = new C(8,9);
        System.out.println("obj_c.a" +obj_c.a);
        System.out.println("obj_c.c" +((C)obj_c).c);

    }
}

// ==========================
// 父類別 A
// ==========================
class A {
    int a;

    A(int a) {
        this.a = a;
    }
}

// ==========================
// 子類別 B（繼承 A）
// ==========================
class B extends A {
    int b;

    // ❌ 原本錯：iny → int
    B(int a, int b) {
        super(a);   // 呼叫 A 的建構子
        this.b = b;
    }
}

// ==========================
// 子類別 D（繼承 B）
// ==========================
class D extends B {
    int d;

    D(int a, int b, int d) {
        super(a, b);   // 呼叫 B → B 會再呼叫 A
        this.d = d;
    }
}

// ==========================
// 子類別 C（繼承 A）
// ==========================

// ❌ 原本錯：Class 要小寫 class
class C extends A {
    int c;

    C(int a, int c) {
        super(a);
        this.c = c;
    }
}
package org.example;

public class Main {

    // ✅ 正確 main 進入點
    public static void main(String[] args) {

        // 建立 Geometry 物件
        Geometry g1 = new Geometry(100,200);
        g1.printInfo();


        // 建立 Rec 物件（含座標）
        Rec r1 = new Rec(23, 56, 10, 20);
        r1.printInfo();
    }
}

// ==========================
// 父類別 Geometry
// ==========================
class Geometry {

    int x;
    int y;

    // 預設建構子
    Geometry() {}

    // 帶參數建構子
    Geometry(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void printInfo() {
        System.out.println("x = " + x + " y = " + y);
    }
}

// ==========================
// 子類別 Rec（矩形）
// ==========================
class Rec extends Geometry {

    int length;
    int width;

    // ✅ 建構子1：只設定寬高
    Rec(int l, int w, int x ,int y) {
        super(x,y); //這預設只會call 預設supe(),要自己改成這個//similer to this(), super() is tocall father's CTOR
        length = l;
        width = w;
    }

        // 覆寫方法
    void printInfo() {
        // 先印父類別資訊
        super.printInfo();     //similar to this. super. is to affess father's atttribute


        // 再印自己的
        System.out.println("width = " + width + " length = " + length);
    }
}
package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

//public class Main {
//    static void main() {
//        Plane plane = new Plane(10, 20);
//        Plane space = new Space(1, 2, 3);
//        Plane plane2 = new Space(4, 5, 6);
//        System.out.println("x=" + plane.x + ",y =" + plane.y + " Area=" + plane.computeArea());
//        System.out.println("x=" + plane.x + ",y =" + plane.y + "z=" + plane.z + " Area=" + space.computeArea());
//        System.out.println("x=" + plane2.x + ",y =" + plane2.y + "z=" + ((Space) plane2).z + " Area=" + plane2.computeArea()); // Attribute 要Casting,Method要不用Casting
//        System.out.println("Extra Area=" + Plane.computeExtraArea());
//        System.out.println("Extra Area=" + Space.computeExtraArea());
//
//    }
//    }

public class Main {
    public static void main(String[] args) {
        // 1. 一般父類別物件
        Plane plane = new Plane(10, 20);

        // 2. 一般子類別物件
        Space space = new Space(1, 2, 3);

        // 3. 多型（Polymorphism）：父類別引用 指向 子類別物件
        Plane plane2 = new Space(4, 5, 6);

        // --- 輸出結果解析 ---

        // 基本屬性與方法
        System.out.println("x=" + plane.x + ", y=" + plane.y + " Area=" + plane.computeArea());

        // 注意：plane 引用類型是 Plane，所以不能直接存取 .z
        System.out.println("x=" + space.x + ", y=" + space.y + " z=" + space.z + " Area=" + space.computeArea());

        // 多型重點：屬性看「引用類型」，方法看「實際物件」
        // 必須強制轉型 (Casting) 才能存取子類別特有的屬性 z
        System.out.println("x=" + plane2.x + ", y=" + plane2.y + " z=" + ((Space) plane2).z + " Area=" + plane2.computeArea());

        // 靜態方法：屬於類別，不建議用物件調用，直接用類別名
        System.out.println("Extra Area=" + Plane.computeExtraArea()); // 輸出 100
        System.out.println("Extra Area=" + Space.computeExtraArea()); // 輸出 110 (10 + 100)
    }
}





class Plane{

    int x;
    int y;
    static final int extra = 100;  // 靜態變數無法被覆寫（Override）/ cannot be OR(overridden)
    Plane(int x, int y){
        this.x = x;
        this.y = y;
    }
    int computeArea(){     // 非靜態方法可以被覆寫（Override）//non static method can be overridden
        return x * y ;
    }
    static int computeExtraArea(){  // 靜態方法無法被覆寫（Override）
        return extra;
    }
}

class Space extends Plane{
    int z;
    static int ss =10;
    Space(int x, int y, int z){
        super(x, y);
        this.z = z;
    }
    int computeArea(){
        return x * y * z;
    }
    static  int  computeExtraArea(){      // 靜態方法無法被覆寫（Override） Non OR (Override)  ;owing to static method cannont be overridden(OR)
         return  ss +Plane.computeExtraArea();
    }
}


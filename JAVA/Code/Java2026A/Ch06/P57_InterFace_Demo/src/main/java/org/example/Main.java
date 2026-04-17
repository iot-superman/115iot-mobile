package org.example;

public class Main {

    public static void main(String[] args) {

        GrandFather g = new GrandFather();
        Father f = new Father();
        Son s = new Son();

        g.driving();

        System.out.println("-------------------------");

        f.driving();
        f.swimming();

        System.out.println("-------------------------");

        s.driving();        // override
        s.swimming();       // 繼承
        s.running();        // interface
        s.driving("Wave");  // overload（你圖中有）
    }
}


// ==========================
// interfaces
// ==========================
interface CanDriving {
    void driving();
}

interface CanSwimming {
    void swimming();
}

interface CanRunning {
    void running();
}


// ==========================
// GrandFather
// ==========================
class GrandFather implements CanDriving {

    @Override
    public void driving() {
        System.out.println("I can do GrandFather Driving!");
    }
}


// ==========================
// Father
// ==========================
class Father extends GrandFather implements CanSwimming {

    @Override
    public void swimming() {
        System.out.println("I can do Father Swimming!");
    }
}


// ==========================
// Son（你畫面重點在這）
// ==========================
class Son extends Father implements CanRunning {

    // ✅ Override（覆寫）
    @Override
    public void driving() {
        System.out.println("I can do Son Driving!");
    }

    // ✅ Overload（多載）← 你圖中有這個
    public void driving(String type) {
        System.out.println("I can do Son " + type + " Driving!");
    }

    // ✅ Interface 實作
    @Override
    public void running() {
        System.out.println("I can do Son Running!");
    }
}
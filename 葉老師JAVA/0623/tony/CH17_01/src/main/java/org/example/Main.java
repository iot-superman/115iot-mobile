package org.example;
//介面
interface Fly{
    void flyling();//抽象方法
}
class Bird implements Fly{
    @Override
    public void flyling() {
        System.out.println("flyling.....");
    }
}
class AirPlane implements Fly{
    @Override
    public void flyling() {
        System.out.println("AriPlane.....");
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //Fly obj1 = new Fly();//介面無法直接物件化
        Bird b = new Bird();
        b.flyling();
        AirPlane a = new AirPlane();
        a.flyling();



    }
}

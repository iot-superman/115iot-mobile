package org.example;
interface Vehicle{
    String getBrand();//抽象方法
    String run();//抽象方法
    default String alarmOn(){ //default method，一般方法
        return "alarmOn..";
    }
    default String alarmOff(){//default method，一般方法
        return "alarmOff..";
    }
}
class Car implements Vehicle{
    private String brand;
    Car(String brand){
        this.brand = brand;
    }
    @Override
    public String getBrand() {
        return brand;
    }
    @Override
    public String run() {
        return "running.....";
    }
    public String alarmOff(){
        return "自己要關閉!";
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Car c = new Car("Toyota");
        System.out.println(c.getBrand());
        System.out.println(c.run());
        System.out.println(c.alarmOn());
        System.out.println(c.alarmOff());
    }
}

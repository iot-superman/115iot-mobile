package org.example;

// Vehicle 介面
interface Vehicle {

    // 抽象方法
    String getBrand();

    // 抽象方法
    String run();

    // 預設方法
    default String alarmOn() {
        return "alarmOn ...";
    }

    // 預設方法
    default String alarmOff() {
        return "alarmOff ...";
    }
}


// Car 類別
class Car implements Vehicle {

    private String brand;

    // 建構子
    public Car(String brand) {
        this.brand = brand;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String run() {
        return "running....";
    }

    // 覆寫預設方法
    @Override
    public String alarmOff() {
        return "自己要關閉!";
    }
}


// Motorcycle 類別
class Motorcycle implements Vehicle {

    private String brand;

    // 建構子
    public Motorcycle(String brand) {
        this.brand = brand;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String run() {
        return "running with speed....";
    }

    // alarmOn()、alarmOff()
    // 使用介面的 default 方法
}


// 主程式
public class Main {

    public static void main(String[] args) {

        // 建立 Car 物件
        Car car = new Car("Tesla");

        System.out.println("=== 汽車 ===");
        System.out.println("品牌：" + car.getBrand());
        System.out.println("狀態：" + car.run());
        System.out.println(car.alarmOn());
        System.out.println(car.alarmOff());

        System.out.println();

        // 建立 Motorcycle 物件
        Motorcycle motorcycle = new Motorcycle("Yamaha");

        System.out.println("=== 機車 ===");
        System.out.println("品牌：" + motorcycle.getBrand());
        System.out.println("狀態：" + motorcycle.run());
        System.out.println(motorcycle.alarmOn());
        System.out.println(motorcycle.alarmOff());
    }
}
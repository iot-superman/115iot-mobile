package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Employee e = new Employee("Andy", "Taipei");
        Salary s = new Salary("John", "Taipei", 12346);
        Hourly h = new Hourly("Carol", "Taoyuan", 198, 40);
        System.out.println( e.mail() + "with computePay : " + e.computePay());
        System.out.println( s.mail() + "with computePay : " + s.computePay());
        System.out.println( h.mail() + "with computePay : " + h.computePay());
    
    }
}


// ==========================
// 父類別 Employee（員工）
// ==========================
class Employee {

    // 成員變數（屬性）
    String name;   // 員工姓名
    String addr;   // 員工地址

    // 一般方法：寄信通知
    String mail() {

        return ( "Mail to " + name + " " + addr);
    }

    // 建構子（Constructor）
    // 👉 用來初始化物件
    Employee(String name, String addr) {
        this.name = name;   // this 代表目前物件
        this.addr = addr;
    }

    // 薪資計算方法（父類預設）
    // 👉 子類別會 override（覆寫）
    double computePay() {
        return 0;  // 預設回傳 0（實務上通常會改成 abstract）
    }
}


// ==========================
// 子類別 Salary（年薪制）
// ==========================
class Salary extends Employee {

    double salary; // 年薪

    // 建構子
    Salary(String name, String addr, double salary) {
        super(name, addr);  // 呼叫父類別建構子
        this.salary = salary;
    }

    // 覆寫（Override）薪資計算方法
    // 👉 年薪 / 52 = 每週薪資
    double computePay() {
        return salary / 52.0;
    }
}


// ==========================
// 子類別 Hourly（時薪制）
// ==========================
class Hourly extends Employee {

    double hourlyRate;   // 時薪
    double hoursWorked;  // 工作時數

    // 建構子
    Hourly(String name, String addr, double hourlyRate, double hoursWorked) {
        super(name, addr);  // 呼叫父類別建構子
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    // 覆寫（Override）薪資計算方法
    // 👉 時薪 × 工時
    double computePay() {
        return hourlyRate * hoursWorked;
    }
}
package org.example;

public class Main {
    public static void main(String[] args) {   // 🔧 修正1：main 必須是 public + 有參數

        Employee e1, e2;

        e1 = new Employee();
        e1.name = "Andy";
        e1.salary = 3200000;
        e1.address = "Taipei";
        e1.mailCheck();

        e2 = new Employee();
        e2.name = "Bill";
        e2.salary = 2200000;
        e2.address = "Japan";
        e2.mailCheck();

        // 🔧 修正2：建構子參數錯誤（原本少 address）
        Employee e3;
        e3 = new Employee("Carol", "USA", 12345);
        e3.mailCheck();

        // 🔧 修正3：e4 未宣告
        Employee e4;
        e4 = new Employee("djw", "Adder", 1234);
        e4.mailCheck();
    }
}

class Employee {
    String name;
    String address;
    double salary;

    double computeWeeklyPay() {
        return salary / 52.0;
    }

    void mailCheck() {
        System.out.println("Mailing check to " + name +
                " addr:" + address +
                " Salary:" + salary);
    }

    Employee() {}  // 預設建構子

    // 🔧 修正4：正確使用 this(...) 呼叫另一個建構子
    Employee(String name, String addr, double salary) {
        this(name, addr);   // 呼叫兩參數建構子（必須放第一行）
        this.salary = salary; // 🔧 補上 salary 設定（原本沒設）
    }

    // 🔧 建議：不要設 private，否則外部不能用
    Employee(String name, String addr) {
        this.name = name;
        this.address = addr;
        this.salary = 0;
    }
}
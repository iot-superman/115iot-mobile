package org.example;


public class Main {
    static void main() {

        // 建立業務員物件
        Sales salesObj = new Sales("Andy", 123, 0.08);

        // 加入銷售金額
        salesObj.addToSales(100000);

        // 顯示名稱
        System.out.println("Name: " + salesObj.getName());

        // 顯示佣金
        System.out.println("Commission: " + salesObj.computeCommission());

    



    }
}

// ==========================
// Sales 類別
// ==========================
class Sales {

    private String name;
    private int id;
    private double commissionRate;
    private double sales;

    // 建構子
    Sales(String name, int id, double commissionRate) {
        this.name = name;
        this.id = id;
        this.commissionRate = commissionRate;
        this.sales = 0; // 初始化銷售額
    }

    // 累加銷售額
    void addToSales(double s) {
        sales += s;
    }

    // 取得名稱
    String getName() {
        return name;
    }

    // 計算佣金
    double computeCommission() {

        double commission = 0;  // ✅ 先初始化

        if (sales > 0.0) {
            commission = sales * commissionRate;
        }

        return commission; // ✅ 一定要 return
    }
}
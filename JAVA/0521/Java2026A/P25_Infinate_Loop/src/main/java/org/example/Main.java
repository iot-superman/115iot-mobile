package org.example;

import java.util.Scanner;

public class Main {

    // ✅ Java 程式進入點（一定要這樣寫）
    public static void main(String[] args) {

        int lucky_num = 1688; // ✅ 設定幸運數字
        Scanner scanner = new Scanner(System.in);

        int inp; // ✅ 修正型別宣告（原本拼錯 inwt）

        // ✅ 無限迴圈（直到猜中才跳出）
        for (;;) {
            System.out.println("Please Input a Number:");

            // ✅ 讀取使用者輸入
            inp = scanner.nextInt();

            // ✅ 判斷是否猜中
            if (inp == lucky_num) {
                break; // ✅ 猜中 → 離開迴圈
            }

            // ❌ 猜錯提示
            System.out.println("Get Wrong, please Try Again");
        }

        // ✅ 猜對後顯示
        System.out.println("Got Correct, See You Again");

        // ✅ 關閉 Scanner（避免資源浪費）
        scanner.close();
    }
}
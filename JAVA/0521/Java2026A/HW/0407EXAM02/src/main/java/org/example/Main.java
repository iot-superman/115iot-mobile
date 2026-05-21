package org.example;

/*
2. 繼上題. 請問(a)最高薪資, 發生在第幾鄰? 第幾戶?第幾人?  (b)最低薪資,
發生在第幾鄰? 第幾戶?第幾人?
* */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        // 三維陣列：鄰 -> 戶 -> 人
        int[][][] LionLi = {                                     //3-Dim

                // 第0鄰
                {
                        {34500, 21400, 56700},
                        {23200, 41400},
                        {36800, 55600}
                },

                // 第1鄰
                {
                        {43500, 28700, 74700, 58900},
                        {43500, 29800, 35700}
                },

                // 第2鄰
                {
                        {54300, 41200, 76500},
                        {33500, 22400},
                        {36800, 24200, 67900, 45200}
                }
        };

        // =========================
        // (a) 找最高薪
        // =========================
//        int max = Integer.MIN_VALUE; // 初始化為最小值
        int max = LionLi[0][0][0];
        int min = LionLi[0][0][0];

        //  記最大的鄰，戶，人
        int max_lin = 0, max_who = 0, max_ps = 0; // 記錄位置

        // 三層迴圈
        for (int i = 0; i < LionLi.length; i++) { // 鄰
            for (int j = 0; j < LionLi[i].length; j++) { // 戶
                for (int k = 0; k < LionLi[i][j].length; k++) { // 人

                    // 如果找到更大的
                    if (LionLi[i][j][k] > max) {
                        max = LionLi[i][j][k];

                        // 記錄位置
                        max_lin = i;
                        max_who = j;
                        max_ps = k;
                    }
                }
            }
        }

        // =========================
        // (b) 找最低薪
        // =========================
//        int min = Integer.MAX_VALUE; // 初始化為最大值
        //  記最小的鄰，戶，人
        int min_lin = 0, min_who = 0, min_ps = 0;


        for (int i = 0; i < LionLi.length; i++) {
            for (int j = 0; j < LionLi[i].length; j++) {
                for (int k = 0; k < LionLi[i][j].length; k++) {

                    if (LionLi[i][j][k] < min) {
                        min = LionLi[i][j][k];

                        min_lin = i;
                        min_who = j;
                        min_ps = k;
                    }
                }
            }
        }

        // =========================
        // 輸出結果
        // =========================

//        System.out.println("lioLi.length=" + LionLi.length);
//        System.out.println("lioLi[2].length=" + LionLi[2].length);
//        System.out.println("lioLi[2][2].length=" + LionLi[2][2].length);
        // (a) 幼獅里的 length（幾個鄰）
        System.out.println("===EXAM1===");
        System.out.println("(a) 幼獅里有幾個鄰: " + LionLi.length);

        // (b) 第2鄰的 length（第2鄰有幾戶）
        System.out.println("(b) 第2鄰有幾戶: " + LionLi[2].length);

        // (c) 第2鄰 第2戶的 length（該戶有幾人）
        System.out.println("(c) 第2鄰 第2戶有幾人: " + LionLi[2][2].length);

        System.out.println("\n===EXAM2===");
        System.out.println("【最高薪】");
        System.out.println("薪資: " + max);
        System.out.println("位置: 第" + max_lin + "鄰 第" + max_who + "戶 第" + max_ps + "人");

//        System.out.println();

        System.out.println("【最低薪】");
        System.out.println("薪資: " + min);
        System.out.println("位置: 第" + min_lin + "鄰 第" + min_who + "戶 第" + min_ps + "人");



    }
}

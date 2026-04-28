package org.example;
/*

1. 利用下圖,形成幼獅里的三維陣列. 請印出(a)幼獅里的length (b)幼獅里
第2鄰的length (c)幼獅里 第2鄰 第2戶的length


 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        int[][][] lionVillage = {

                // 第0鄰
                {
                        {34500, 21400, 56700},   // 第0戶（3人）
                        {23200, 41400},          // 第1戶（2人）
                        {36800, 55600}           // 第2戶（2人）
                },

                // 第1鄰
                {
                        {43500, 28700, 74700, 58900},  // 第0戶（4人）
                        {43500, 29800, 35700}          // 第1戶（3人）
                },

                // 第2鄰
                {
                        {54300, 41200, 76500},   // 第0戶（3人）
                        {33500, 22400},          // 第1戶（2人）
                        {36800, 24200, 67900, 45200} // 第2戶（4人）
                }
        };

        // (a) 幼獅里的 length（幾個鄰）
        System.out.println("(a) 幼獅里有幾個鄰: " + lionVillage.length);

        // (b) 第2鄰的 length（第2鄰有幾戶）
        System.out.println("(b) 第2鄰有幾戶: " + lionVillage[2].length);

        // (c) 第2鄰 第2戶的 length（該戶有幾人）
        System.out.println("(c) 第2鄰 第2戶有幾人: " + lionVillage[2][2].length);
    }
}

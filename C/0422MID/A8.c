/*

8.

請撰寫計程車計費程式，可輸入里程及計時運價，並計算出車資。

計算規則：

里程在 1250 公尺內：85 元
每超過 200 公尺加 5 元
不足 200 公尺 → 以 200 公尺計算
計時費：每累計 60 秒加 5 元


*/

 
 
#include <stdio.h>
#include <stdlib.h>

int main(void) {
    int distance, time;
    float fee,timefee;
    float t=0;

    printf("請輸入里程（公尺）：");
    scanf("%d", &distance);

    printf("請輸入時間（秒）：");
    scanf("%d", &time);
    fee =85;

     // ===== 距離費 =====
    if (distance > 1250) {
        int extra = distance - 1250;

    // ★無條件進位 (不足200也算一段)
        fee += ((extra + 199) / 200) * 5;
    }

    // ===== 時間費 =====
    if (time > 0) {
        // ★
        timefee = ((time ) / 60) * 5;
    }

    // ===== 總金額 =====
    float total = fee + timefee;

    printf("車費為：%.2f\n", total);

    
    system("pause");
    return 0;
}

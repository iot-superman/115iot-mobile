//https://youtu.be/iBDT2U8hUzc
#include <stdlib.h>
#include <stdio.h>
/*
輸入分數
   │
   ├─ >=90  → A
   │
   ├─ >=80  → B
   │
   ├─ >=70  → C
   ├─ >=60  → D
   │
   └─ 其他 → F
*/

int main(void)
{
    int score;   // 宣告整數變數 score，用來存放成績

    // 提示使用者輸入成績
    printf("Your score: ");

    // 讀取使用者輸入的整數
    scanf("%d", &score);

    // 判斷成績等級
    if (score >= 90)
    {
        printf("%d is A\n", score);
    }
    else if (score >= 80)
    {
        printf("%d is B\n", score);
    }
    else if (score >= 70)
    {
        printf("%d is C\n", score);
    }
        else if (score >= 60)
    {
        printf("%d is D\n", score);
    }
    else
    {
        printf("%d is Fail!\n", score);
    }

    // Windows 主控台暫停
    system("pause");

    return 0;  // 程式正常結束
}

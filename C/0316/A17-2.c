#include <stdlib.h>
#include <stdio.h>

int main(void)
{
    // 宣告變數
    int chinese, english, bonus;

    // 輸入國文成績
    printf("Please input the score of chinese: ");
    scanf("%d", &chinese);

    // 輸入英文成績
    printf("Please input the score of english: ");
    scanf("%d", &english);

    // 判斷加分條件
    if (chinese == 100)
    {
        // 如果國文100
        if (english == 100)
        {
            bonus = 50;   // 國文100 且 英文100
        }
        else
        {
            bonus = 20;   // 國文100 但 英文不是100
        }
    }
    else
    {
        // 如果國文不是100
        if (english == 100)
        {
            bonus = 20;   // 只有英文100
        }
        else
        {
            bonus = 0;    // 都不是100
        }
    }

    // 印出加分
    printf("bonus: %d\n", bonus);

    // Windows 主控台暫停
    system("pause");

    return 0;
}

#include <stdio.h>
#include <stdlib.h>

int main(void){
    float deposit = 0;   // 累積金額
    float money = 0;     // 每次輸入金額
    int num = 0;         // 次數（改成 int）

    while(deposit < 35000)
    {
        printf("Please enter deposit amount:\n");

        scanf("%f", &money);   // ? 修正：float 要用 %f

        num++;   // ? 放在輸入後

        printf("Deposit count: %d\n", num);

        deposit = deposit + money;

        printf("Current total: %.2f\n\n", deposit);
    }

    printf("Total accumulated: %.2f\n", deposit);

    system("pause");
    return 0;
}

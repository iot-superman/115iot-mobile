// 
#include <stdlib.h>
#include <stdio.h>

int main(void)
{
    int total = 0;
    int value = 0;

    printf("Please input an integer: ");
    scanf("%d", &value);   // ??:&value ??????
    total = total + value;

    printf("Please input an integer: ");
    scanf("%d", &value);   // ??:&value ??????

    printf("Total:%d\n", total);

    total = total + value;

    printf("Total:%d\n", total);

    system("pause");

    return 0;
}

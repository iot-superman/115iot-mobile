/*
1.

假設在程式碼裡有如下的敘述

float num=12.6f;

試撰寫指標變數 ptr 指向 num，列印出變數 num 與指標變數 ptr 的值與位址。

*/


#include <stdio.h>
#include <stdlib.h>

int main(void)
{
    float num = 12.6f;
    float *ptr = &num;   // ? 指向 num

    
    printf("num = %f\n", num);          // 值
    printf("num = %p\n", &num);          // ptr 指標的位址 
    printf("*ptr = %f\n", *ptr);        // 指標中的數值 
    printf("ptr = %p\n", ptr);          // ptr 指標的位址 


    system("pause");
    return 0;
}

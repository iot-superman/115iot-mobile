/*
試撰寫一函數 void count(int * )，可接收一個整數變數 num 的位址（num 的初值請設為 0）。
每當 count() 函數被呼叫一次，主程式裡的 num 之值也會被加 1，並於主程式測試 count() 函數五次，試印出 num 的值。
*/


#include <stdio.h>
#include <stdlib.h>
void count(int* );




int main(void)
{
    int num = 0;
      printf("num=%d\n", num);
    int i;
    for( i = 1; i <= 5; i++) {
        count(&num);
        printf("num=%d\n", num);
    }

    system("pause");
    return 0;
}

void count( int *num) {
	printf("+1\n");
    *num=*num+1;
}


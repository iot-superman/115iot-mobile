/*
刚级糶祘Αノ while 癹伴璸衡 1+2+?+100 ┮Τ案计羆㎝籔计羆㎝
*/


#include <stdio.h>
#include <stdlib.h>
void count(int, int*, int*);




int main(void)
{
	int *sum0 = 0;
	int *sum1 = 0;
    printf("璸衡 1+2+...+100 ┮Τ案计羆㎝籔计羆㎝:\n"); 
    int i = 1;
    while(i <= 100) {
        count(i, &sum0, &sum1);
        i++;
    }

    printf("案计羆㎝  = %d\n", sum0);
    printf("计羆㎝  = %d\n", sum1);
    system("pause");
    return 0;
}

void count(int num, int *sum0, int *sum1) {
    if(num % 2 == 0) {
        *sum0 = *sum0 + num;
    } else {
        *sum1 = *sum1 + num;
    }
}


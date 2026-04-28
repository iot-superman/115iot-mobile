/*
試撰寫替換 PP 陣列元素值的程式，輸入第 N 個元素替換，輸入替換數值 99
*/


#include <stdio.h>
#include <stdlib.h>



int main(void)
{
    int AK[7]={23,39,77,74,25,82,91};
    int *pp = AK;
    int i,n=0;
    printf("Before AK[]: ");
    
    for(i=0; i<7; i++)  printf("%d ", AK[i]);
    
    printf("\n");
    printf("要換第幾個: ");
    scanf("%d", &n);
    
    *(pp+n-1)=99;
    printf("After AK[]:  ");
    
    for(i=0; i<7; i++)  printf("%d ", AK[i]);
    
    printf("\n");

    system("pause");
    return 0;
}




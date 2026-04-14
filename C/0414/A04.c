#include <stdio.h>
#include <stdlib.h>

int main(void){
    int num1,num2;
    char str[30];
    printf("請輸入兩個整數：");
    scanf("%d %d",&num1,&num2);
    printf("%d + %d 是：%d\n",num1,num2,num1+num2);
    printf("Inpur a string: \n");
    scanf("%s",str);
    printf("Inpur at most 10 chars: \n");
    scanf("%10s",str);
    printf("==>%s \n\n",str);

    
    return 0;
}

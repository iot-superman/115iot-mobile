#include <stdlib.h>   // 提供 system() 等函式
#include <stdio.h>    // 提供 printf() 等輸入輸出函式
int main(void) {
    unsigned char cht,ch1,ch2;
    ch1=41;
    ch2=11;
    cht=ch1&ch2; //
    printf("101001 and  001011 =%2d\n",cht);
    cht = ch1 | ch2;
    printf("101001 or  001011 =%2d\n",cht);
    cht  = ch1^ ch2;
    printf("101001 xor  001011 =%2d\n",cht);
    system("pause");
    return 0;
}
//https://chatgpt.com/s/m_69f01469b7248191b0566da5f0cd11b9
#include <stdlib.h>   // 提供 system() 等函式
#include <stdio.h>    // 提供 printf() 等輸入輸出函式
int main(void) {

    unsigned char ch=53;
    unsigned char ix=5;
    ch=ch<<1;
    printf("ch=%x\n",ch);
    ch=ch<<1;
    printf("ch=%x\n",ch);
    ch=ch<<1;
    printf("ch=%x\n",ch);


    ch=ch>>1;
    printf("ch=%x\n",ch);
    ch=ch>>1;
    printf("ch=%x\n",ch);

    ix=ix<<5;
    printf("ix<<5=%x\n",ix);
    ix=ix>>3;
    printf("ix>>3=%x\n",ix);

    system("pause");
    return 0;
}

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(void) {   // 修正：補上 main 函式開始

    short value_1, value_2, result;
    int length;

    length = sizeof(short);
    printf("short %d\n\r", length);

    length = sizeof(int);
    printf("int %d\n\r", length);

    value_1 = -1;
    printf("value_1 %x\n\r", value_1);

    value_1 = -100;
    printf("value_1 %#x\n\r", value_1);

    value_1 = -112;
    printf("value_1 %x\n\r", value_1);

    value_1 = 32765;
    value_2 = 310;
    result = value_1 + value_2;   // 修正：用 short result 接溢位結果
    printf("value_1+value_2 %x\n\r", result);

    value_1 = -32765;
    value_2 = -310;
    result = value_1 + value_2;   // 修正：用 short result 接溢位結果
    printf("value_1+value_2 %x\n\r", result);

     float x = 125.625;

    unsigned int *ptr;

    // 強制轉型成整數指標
    ptr = (unsigned int *)&x;

    printf("float = %f\n", x);

    // 印出 float 的 IEEE754 Hex
    printf("HEX = 0x%08X\n", *ptr);
    
    char data1[]={'A','B','C','D'};
    char data2[]={0x66,0x67,0x68,0x69};
    
    system("pause");
    return 0;
}   // 修正：補上 main 函式結尾

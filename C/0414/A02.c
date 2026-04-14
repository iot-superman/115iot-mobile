#include <stdio.h>
#include <stdlib.h>

int main(void){
    char ch='c';
    char str1[]="C";
    char str2[]="We are Family";

    printf("ch=%c %4dbyte\n", ch     , sizeof(ch));
    printf("str1=%s %4dbyte\n", str1, sizeof(str1));
    printf("str2=%s %4dbyte\n", str2, sizeof(str2));
    

    return 0;
}

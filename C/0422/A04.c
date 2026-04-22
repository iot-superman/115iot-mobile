#include <stdlib.h>
#include <stdio.h>
#include <string.h>
int fac(int n);

int main(void)
{
    char str1[]="xyz";
    char str2[]="abcdef";
    printf("st1:%s\n",str1);
    strcat(str1,str2);     // 將 str2 連接到 str1 後
    printf("st1:%s\n",str1);
    printf("st2:%s\n",str2);
    system("pause");
    return 0;
}

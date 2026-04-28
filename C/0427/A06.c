#include <stdio.h>
#include <stdlib.h>

int main(void)
{
    // ? 修正1：正確宣告 a, b
    int *ptr;
    int num=20;
    ptr=&num;
    printf("num %d , &num=%p,\n",num,&num);
    printf("*ptr=%d,ptr%p, &ptr=%p\n",*ptr,ptr,&ptr);

    // ? 修正3 + 4：拼字 + include
    system("pause");

    return 0;
}

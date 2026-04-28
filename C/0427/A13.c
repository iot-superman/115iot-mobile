//https://chatgpt.com/s/m_69eeff6f91d48191943e517de1a89ae3
#include <stdio.h>
#include <stdlib.h>
void swap(int* ,int*);
int main(void)
{
    int a=17, b=25;
    printf("Before swap ...\n");
    printf("a=%d , b=%d\n",a,b);
    swap (&a,&b);
    printf("After swap ...\n");
    printf("a=%d , b=%d\n",a,b);

    system("pause");

    return 0;
}

void swap(int *a, int *b) {
    int tmp;
    tmp = *a;
    *a = *b;
    *b = tmp;

    printf("In swap funcion() a=%d , b=%d\n", *a, *b);
}

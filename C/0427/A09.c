//https://chatgpt.com/c/69eed71b-4e74-83aa-a552-82882ba5edcc
#include <stdio.h>
#include <stdlib.h>

int main(void)
{
    int a1=100, *ptri;
    float a2=3.2f , *ptrf;
    ptri=&a1;
    ptrf=&a2;

    printf("sizeof(a1)=%d\n",sizeof(a1));
    printf("sizeof(a2)=%d\n",sizeof(a2));
    printf("a1=%d,*ptr=%d\n",a1,*ptri);
    printf("a2=%.1f, *ptrf=%.1f\n",a2,*ptrf);


    system("pause");

    return 0;
}

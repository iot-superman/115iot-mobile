//https://chatgpt.com/s/m_69eef9179d9c81919baeb1aa90bf4b30
#include <stdio.h>
#include <stdlib.h>
void add(int *);
int main(void)
{
    int a=5,b=10;
    int *ptr1,*ptr2;
    ptr1=&a;
    ptr2=&b;
    printf("a=%p, b=%p, \nptr1=%p,ptr2=%p\n", &a, &b ,ptr1,ptr2);
    *ptr1=7;
    *ptr2=32;
    a=17;
    ptr1=ptr2;
    *ptr1=9;
    ptr1=&a;
    a=64;
    *ptr2 = *ptr1+5;
    ptr2=&a;

//    printf("a=%2d, b=%2d, *ptr=%2d, *ptr2=%d\n",*ptr1,*ptr2);
    printf("a=%2d, b=%2d, *ptr1=%2d, *ptr2=%2d\n",
       a, b, *ptr1, *ptr2);
    printf("ptr1=%p,ptr32=%p",ptr1,ptr2);

    system("pause");
    return 0;
}

void add(int *ptr) {
    printf("Varible content %d\n",*ptr);

}

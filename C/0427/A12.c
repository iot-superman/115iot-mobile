https://chatgpt.com/s/m_69eefbb1401c8191bd6f46e32542c4b8
#include <stdio.h>
#include <stdlib.h>
void add(int *);
int main(void)
{
     int a=20;
    int *ptr;
    ptr =&a;
    printf("Before call add() funcion, a=%d\n",a );
    add(ptr);
    printf("After call add() function, a=%d\n",a);

    system("pause");
    return 0;
}

void add(int *ptr) {
    *ptr=*ptr+30;
}
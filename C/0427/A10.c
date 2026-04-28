#include <stdio.h>
#include <stdlib.h>
void add(int *);
int main(void)
{
    int a=886 , *ptr;
    ptr=&a;
    add(ptr);
    

    system("pause");

    return 0;
}

void add(int *ptr) {
    printf("Varible content %d\n",*ptr);

}

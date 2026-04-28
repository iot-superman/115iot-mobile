#include <stdio.h>
#include <stdlib.h>
/*建立C語言的函數*/
void dot(void);

int main(void)
{
    dot();
    printf("Welcome to C Language!\n");
    dot();
    printf("Today is Thesday.\n");
    dot();
    system("pause");
    return 0;
}

void dot(void){
    printf("...............................\n");
}   

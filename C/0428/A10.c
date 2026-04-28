// https://chatgpt.com/s/m_69f032ea18088191bed4833ac0168abd

/*

 */

#include <stdlib.h>
#include <stdio.h>
int *maximun(int *);

int main(void) {
    int AB[6]={23, 37, 58, 97, 75, 92};
    int i,*ptr;
    printf("Aarray AB[]= ");

    for (i=0;i<6;i++) {
        printf("%d ",AB[i]);
    }
    ptr=maximun(AB);
    printf("\nmaximum=%3d\n",*ptr);

    system("pause");
    return 0;
}

int *maximun(int *ptr) {
    int i,*max;
    max=ptr;
    for (i=0;i<6;i++) {
        if (*max<*(ptr+i)) {
            max=ptr+i;
        }
    }
    return max;
}

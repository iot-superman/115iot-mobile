//
// Created by User on 2026/3/31.
//

#include <stdio.h>
#include <stdlib.h>

int main(void) {
    int i,j;
    int sum =0;
    int sale[2][4];
    for (i=0; i<2; i++) {
        for (j=0; j<4; j++) {
            printf("Please input the sale of %d %d quarter result: \n ",i+1,j+1);
            scanf("%d",&sale[i][j]);
            fflush(stdin);
        }
    }
    printf("***********************************");
    for (i=0; i<2; i++) {
        for (j=0; j<4; j++) {
            sum=sum+sale[i][j];
        }
    }
    printf("\n2025 result for total sales volue of %d iphone\n",sum);
    system("pause");
    return 0;
}

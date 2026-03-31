//https://youtu.be/HDR50iOGVoM?si=98vzB20yCxkrHm65
// Created by User on 2026/3/31.
//
#include <stdio.h>
#include <stdlib.h>
#define MAX 5
int main(void) {
    int score[MAX];
    int i,num;
    float sum =0.0f;
    printf("\nPlease input the score,input 0 to end:\n");
    do{
    if (i==MAX) {
        printf("Array space has ben used up!!");
        i++;
        break;
    }
        printf("Please input the score: ");
        scanf("%d", &score[i]);
        fflush(stdin);
    }while(score[i++]>0);
    num=i-1;
    for (i=0; i<num; i++) {
        sum+=score[i];
    }
    if (score[0]==0) {
        printf("No data\n");
    }
    else {
        printf("aberage score:%.1f\n", sum/num);
    }


    system("pause");

    return 0;
}

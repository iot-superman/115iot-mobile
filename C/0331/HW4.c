//
// Created by User on 2026/3/31.
//

/*

試撰寫 do while 程式輸出 200 到 500 之間可以被 3 跟 7 整除的整數。
*/

#include <stdio.h>
#include <stdlib.h>
#define MIN 200
#define MAX 500

int main(void) {
int number = MIN;
printf("程式輸出 %d 到 %d 之間可以被 3 跟 7 整除的整數:\n",MIN,MAX);
    do{
     if (number%3==0 && number%7==0) {
         printf("%d ",number);
     }
     number++;
 }while(number<=MAX);

printf("\n");
 system("pause");
 return 0;

}

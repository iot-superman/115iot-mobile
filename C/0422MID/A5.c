/*
 

5.


試撰寫一程式，利用 while 迴圈印出 5～20 之間所有整數的平方值，最後再印出這些平方值的總和。

 
 
*/
 
#include <stdio.h>
#include <stdlib.h>

#define MIN 5
#define MAX 20

int n2(int); 

int main(void) {

int i=MIN;
int sum=0;
while (i<=MAX) {
     
    printf("%d^2=%d\n",i,n2(i));
    sum+=n2(i);
    i++;    
}
printf("-----\n");

printf("sum=%d\n",sum);



    system("pause");
    return 0;
}

//平方
int n2(int n) {
   return n*n;
}





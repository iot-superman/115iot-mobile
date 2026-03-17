/* 
  3.請撰寫一程式，可輸入三個不同整數，然後 if…else if 找出這三個數值哪個最大
*/

#include <stdlib.h>
#include <stdio.h>

int main(void)
{
   int a, b,c ;
   printf("輸入整數a: ");
   scanf("%d", &a);
   printf("輸入整數b: ");
   scanf("%d", &b);
   printf("輸入整數c: ");
   scanf("%d", &c);
   
   if (a>b && a>c){
   		printf("a");
   } else if (b>c && b>c){
   		printf("b");
   }else{
   		printf("c");
   }
   
   
    

	
   printf("值最大\n");
   system("pause");
   return 0;
}

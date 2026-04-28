#include <stdio.h>
#include <stdlib.h>
/*建立C語言的函數*/
int abs(int);

int main(void)
{
  int i;
   
  printf("請輸入一個整數：");
  scanf("%d",&i);
  fflush(stdin);
  printf("abs(%d)=%d\n",i,abs(i));
  
  system("pause");
  return 0;
}

 int abs(int a){
    if(a<0){
        return -a;
    }
    return a;
 }
#include <stdio.h>
#include <stdlib.h>
/*建立C語言的函數*/
int add(int ,int);

int main(void)
{
  int sum=0,a=5,b=6;
  sum=add(a, b);
  printf("sum=%d\n",sum);
  
  
  return 0;
}

int add(int a,int b){
    return a+b;
}   

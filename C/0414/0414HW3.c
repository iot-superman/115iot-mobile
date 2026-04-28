#include <stdio.h>
#include <stdlib.h>
/* 
3、一個具有7個元素的超輸入數,輸出平均值 
*/
float avg7(int[]);

int main(void)
{
  int num[7];
  int i;
  for(i=0;i<7;i++){
    printf("input integer number%d:",i+1);
    scanf("%d",&num[i]);    
    printf("7numbers avg=%.2f\n",avg7(num));
  }
  
 
  system("pause");
  return 0;
}

float avg7(int num[]){
  int i;
  float sum=0;
  for(i=0;i<7;i++){
    sum+=num[i];
  }
    return sum/7;
}

//https://youtu.be/oaRDCgKMUlQ
#include <stdlib.h>
#include <stdio.h>

int main(void)
{
   
   int score;
   printf("Please input a scoe: ");
   scanf("%d",&score);
   if(score<0||score>100){
      printf("input error!!\n ");
   }
   if(score<60 && score>49){
      printf("make up exam!!\n ");
   }
  
   system("pause");
   return 0;
}

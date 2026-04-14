#include <stdio.h>
#include <stdlib.h>
#define NUM 72
 /*建立C語言的函數*/
 int guess_num(int,int,int);

int main(void) {
  int min=1,max=100,keyin=0,count=0;
  while (keyin!=NUM) {
    count=count+1;
    printf("guess a number (%d - %d):",min,max);        
    scanf("%d",&keyin);
    fflush(stdin);
    guess_num(keyin,min,max);
    printf("You have guessed %d times\n",count);
  }
  printf("Game Over!\n");
  system("pause");
  return 0;
}

int guess_num(int keyin, int min, int max) {
  if (keyin >= min && keyin <= max) {
    if (keyin == NUM) {
      printf("恭喜您猜對了，答案是 %d\n", NUM);
    } else if (keyin > NUM) {
      printf("您猜的數字大了，小點~\n");
    } else if (keyin < NUM) {
      printf("您猜的數字小了，大點~\n");
    }
  } else {
    printf("Please enter a number within the suggested range (%d - %d)\n", min, max);
  }
  return (keyin == NUM);
}
   


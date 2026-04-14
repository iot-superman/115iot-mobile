#include <stdio.h>
#include <stdlib.h>
/*2.試撰寫void kitty(intk)函數·可輸入行數k當主程式呼呼叫kitty(k)時·董幕
上會顯示出k行的 "Hello Kitty"*/
void kitty(int);

int main(void)
{
  int times;
  printf("input times number:");
  
  scanf("%d",&times);
  kitty(times); 
  system("pause");
  return 0;
}

 void kitty(int a){
    int i;
    for(i=a;i>=1;i--){
            printf("Hello Kitty");
        printf("\n");
    }
    printf("\n");
 }

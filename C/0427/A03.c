#include <stdlib.h>
#include <stdio.h>
void add20(int,int);

int main(void) {
  int a=33,b=55;
  printf("Befoere call add20() funcion:  ");
  printf("a= %d , b=%d\n", a ,b);
  add20(a,b);
  printf("After call add20()funcions:");
  printf("a= %d ,b =%d\n",a,b);

  system("pause");
  return 0;
  }


void add20(int a, int  b) {
  a=a+20;
  b=b+20;
  printf("In function a=%d , b=%d\n",a ,b);
}

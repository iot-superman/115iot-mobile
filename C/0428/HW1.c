 
#include <stdio.h>
#include <stdlib.h>

/*
 1 .  請宣告兩個指標 ptrA, ptrB  分別指向變數 a=15, b=16，顯示兩個相乘後的結
果。 
 
*/
int main(void) {
  int *ptrA, *ptrB;
  int a = 15, b = 16;
  ptrA = &a;
  ptrB = &b;
  int result = (*ptrA) * (*ptrB);
  printf("a=%d, b=%d, result=%d\n", a, b, result);      



  system("pause");
  return 0;
}

//
#include <stdio.h>
#include <stdlib.h>
/*
 
c[5]={2,4,6,8,10}  to square
*/
void square(int *);

void square(int *arr) {
  int *p;
  for (p = arr; p < arr + 5; p++) {  //移動指標 
    *p = (*p) * (*p);              // 指標的值去用平方改動  
  }
}
int main(void) {

  int c[5] = {2,4,6,8,10};
  square(c);
  int i;
  for (i = 0; i < 5; i++) {
    printf("%d ", c[i]);
  }
  printf("\n");

  
  system("pause");
  return 0;
}

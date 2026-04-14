#include <stdio.h>
#include <stdlib.h>
/* 
3¡B5x5 star
*/
void nxnstar(int);

int main(void)
{
  nxnstar(5);
  system("pause");
  return 0;
}

 void nxnstar(int a){
    int i;
    for(i=a;i>=1;i--){
        int j;
        for(j=1;j<=a;j++){
            printf("*");
        }
        printf("\n");
    }
    printf("\n");
 }

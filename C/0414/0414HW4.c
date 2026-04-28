#include <stdio.h>
#include <stdlib.h>
  
  /*
  *****
  ****
  ***
  **
  *
  */
void star(int);

int main(void)
{
  star(5);
  system("pause");
  return 0;
}

 void star(int a){
    int i;
    for(i=a;i>=1;i--){
        int j;
        for(j=1;j<=i;j++){
            printf("*");
        }
        printf("\n");
    }
    printf("\n");
 }

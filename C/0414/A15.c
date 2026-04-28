#include <stdio.h>
#include <stdlib.h>
void sum(int),fac(int);

int main(void) {
  fac(4);
  sum(4);
  fac(6);
  sum(6);
  fac(7);
  sum(7);
  system("pause");
  return 0;
}
//¶¥­¼
void fac(int a){
    
    int i=1,total=1;
    for(i=1;i<=a;i++){
        total=total*i;
    }
    printf("1x2x....x%d=%d\n",a,total);
}
//1+2+3...b
void sum(int b){
    int i=1,total=0;
    for(i=1;i<=b;i++){
        total=total+i;
    }
    printf("1+2+3+...+%d=%d\n",b,total);
}
 
 
   


//
#include <stdio.h>
#include <stdlib.h>
/*
 
4. 
int abc[5] = {10, 20, 30, 40, 50};   
reveser print
  50.. 40.. 10
*/
#define arraysize 5
void displayArray(){
}
int main(void) {

    int abc[arraysize] = {10, 20, 30, 40, 50 }; 

    int *ptr = abc+(arraysize-1);
    int i;
    
    for (i = 0; i < 5; i++) { printf(" %d ", *(ptr-i)); }
    printf("\n");

  
  system("pause");
  return 0;
}



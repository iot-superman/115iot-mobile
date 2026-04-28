//
#include <stdio.h>
#include <stdlib.h>
/*
 
3.  假設整數陣列 arr 宣告為 
int arr[5]={34,76,33,42,76}; 
試利用指標常數 arr 的算術運算，將陣列 arr 裡每一個元素的值加上 10，並
列印出結果。   
 
*/
 
int main(void) {

    int arr[5]={34,76,33,42,76};
    int *ptr = arr;  
    int i;
    for (i = 0; i < 5; i++) {
      *(ptr+i) = *(ptr+i) + 10;   
    }
    for (i = 0; i < 5; i++) {
      printf(" %d ", *(ptr+i));
    }
    printf("\n");

  
  system("pause");
  return 0;
}

#include <stdio.h>
#include <stdlib.h>
/*建立C語言的函數*/

// float就可以用了
int is_prime(int); // 判斷是否為質數

int main(void) {
  int i;
  for (i = 2; i <= 100; i++) {
    if (is_prime(i)) {
      printf("%3d", i);
    }
  }

  return 0;
}
int is_prime(int num) {
  int i;
  for (i = 2; i < num/2; i++) {            //num/2 省時 
    if (num % i == 0) {
      return 0;
    }
  }
  return 1;
}

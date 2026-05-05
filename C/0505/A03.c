// https://gemini.google.com/share/f7f08b70d41b
#include <stdio.h>
#include <stdlib.h>

int main() {
  int num[3][4] = {{11, 23, 42, 18}, {43, 22, 16, 14}, {31, 19, 13, 28}};

  int m, n;
  for (m = 0; m < 3; m++) {
    for (n = 0; n < 4; n++) {
      printf("num[%d][%d]=%d,address=%p\n", m, n, *(*(num + m) + n),
             (*(num + m) + n));
    }
  }

  system("pause");
  return 0;
}

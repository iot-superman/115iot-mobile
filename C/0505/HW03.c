
// 印出 IOT 陣列的數值元素 7，並且印出該元素加 6 後的結果。
// IOT[2][5]={{11, 22, 33, 44, 55}, {2, 4, 7, 8, 9}};

#include <stdio.h>
#include <stdlib.h>

int main(void) {

  int IOT[2][5] = {{11, 22, 33, 44, 55}, {2, 4, 7, 8, 9}};
  int *ptr = &IOT[0][0]; // ? 指向第一個元素

  int i, j;

  for (i = 0; i < 2; i++) {
    for (j = 0; j < 5; j++) {

      // ? 正確位移公式：i*5 + j
      if (*(ptr + i * 5 + j) == 7) {

        printf("Fine 7 in IOT[%d][%d]=%d , then +7 Value=%d\n", i, j,
               *(ptr + i * 5 + j), *(ptr + i * 5 + j) + 7);
                *(ptr + i * 5 + j)=*(ptr + i * 5 + j) + 7;
      }
    }
  }

  printf("After IOT :\n");
  for (i = 0; i < 2; i++) {
    for (j = 0; j < 5; j++) {
      printf("%02d ", IOT[i][j]);
    }
    printf("\n");
  }
  system("pause");
  return 0;
}

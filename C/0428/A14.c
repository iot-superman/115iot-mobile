// https://chatgpt.com/s/m_69f053942eb88191b863bc92e46657d6
#include <stdio.h>
#include <stdlib.h>
// 將兩個二維陣列的元素相加後，再將結果存入第三個二維陣列中
// 然後利用指標輸出第三個二維陣列
int main(void) {

  int i, j;
  int A[2][3] = {{3, 3, 4}, {6, 2, 7}};
  int B[2][3] = {{2, -1, 3}, {3, 1, 2}};
  int C[2][3];

  int *ptrA, *ptrB, *ptrC;
  for (i = 0; i < 2; i++) {
    ptrA = A[i];
    ptrB = B[i];
    ptrC = C[i];
    for (j = 0; j < 3; j++) {
      *(ptrC + j) =
          *(ptrA + j) +
          *(ptrB +
            j); // 將兩個二維陣列的元素相加後，再將結果存入第三個二維陣列中
    }
  }
  printf("A+B\n");
  for (i = 0; i < 2; i++) {
    ptrC = C[i];
    for (j = 0; j < 3; j++) { // 然後利用指標輸出第三個二維陣列
      printf("%3d ", *(ptrC + j));
    }
    printf("\n");
  }

  system("pause");

  return 0;
}

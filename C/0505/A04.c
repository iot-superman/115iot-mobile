// https://chatgpt.com/c/69f95520-f324-83a3-a28e-f366b7e19cc1
// https://gemini.google.com/share/ada23d9d556e
#include <stdio.h>
#include <stdlib.h>

int main() {
  // 1. 宣告與初始化一個 2x5 的二維整數陣列
  int arr[2][5] = {
      {1, 2, 3, 4, 5}, // 第一列 (row 0)
      {5, 6, 7, 8, 9}  // 第二列 (row 1)
  };

  int m, n;
  // 2. 使用巢狀迴圈遍歷陣列
  for (m = 0; m < 2; m++) {   // 外層迴圈控制列 (row)
    for (n = 0; n < 5; n++) { // 內層迴圈控制行 (column)

      // 3. 核心解說：存取陣列元素與取得其記憶體位址
      printf("arr[%d][%d]=%d, address=%p\n", m, n,
             *(*(arr + m) + n), // (A) 透過指標運算「取值」
             *(arr + m) + n     // (B) 透過指標運算「取得位址」
      );
    }
  }

  // 在 Windows 上暫停程式，以便查看輸出結果
  // 註：在 Linux/macOS 上通常不需要這行，或可改用 getchar();
  system("pause");

  return 0;
}

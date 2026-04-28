#include <stdio.h>
#include <stdlib.h>

// ? 修正：回傳型別要一致 → int*
int *max(int *, int *);

int main(void)
{
  int a = 56, b = 78;
  int *ptr;

  // ? 這行其實不用（你原本寫的 result）
  // int result = max(&a, &b);  // 錯誤：型別不符

  ptr = max(&a, &b);

  printf("%d betwenn %d ,Max is: %d\n",a,b, *ptr);  // 解參考
  

  system("pause");
  return 0;
}

// ? 回傳「比較大的那個變數的位址」
int *max(int *a, int *b)
{
  if (*a > *b) {
    return a;
  } else {
    return b;
  }
}
// https://chatgpt.com/s/m_69f94596f0448191b7bda0e4f2af6d92
#include <stdio.h>
#include <stdlib.h>

int main() {
  int num[3][4];
  printf("num=%d\n",num);
  printf("&num=%p\n",&num);
  printf("num[0]=%p\n",num[0]);
  printf("num[1]=%p\n",num[1]);
  printf("num[2]=%p\n",num[2]);
  printf("&num[0]=%p\n",&num[0]);
  printf("&num[1]=%p\n",&num[1]);
  printf("&num[2]=%p\n",&num[2]);


  system("pause");
  return 0;
}


// https://chatgpt.com/s/m_69f04807276c81919108ec1f0e4a9b08

#include <stdio.h>
#include <stdlib.h>
int *maximun(int *);

int main(void) {
  int IOT[5] = {31, 17, 33, 22, 16};
  int *ptr = IOT;
  int i;
  printf("IOT[5]={");

  for (i = 0; i < 5; ++i) {
    if (i != 4) {
      printf("%d ", *(ptr + i));
    } else {
      printf("%d}\n", *(ptr + i));
    }
  }
  printf("\After adding 7 to the value\n");
  printf("IOT[5]={");
  for (i = 0; i < 5; ++i) {
    if (i != 4) {
      printf("%d ", *(ptr + i) + 7);
    } else {
      printf("%d}\n", *(ptr + i) + 7);
    }
  }

  system("pause");
  return 0;
}

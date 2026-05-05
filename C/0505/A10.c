#include <stdio.h>
#include <stdlib.h>

int main(void) // ? 建議寫 (void)
{
  // ================================
  // 定義 struct
  // ================================

  // sizeof data =28byte
  struct data {
    char name[23];
    int math;
  } student[18];

  // 23+4= 27 /4 = 6 rem 3  => 4*7=28  padding = 4 byte
  //  but we have 18 element 18*28 = 504

  printf("sizeof(student)=%d\n", sizeof(student));

  system("pause");
  return 0;
}

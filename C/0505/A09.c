#include <stdio.h>
#include <stdlib.h>
 

int main(void) // ? 建議寫 (void)
{
  // ================================
  // 定義 struct
  // ================================
  struct data {
    char name[15];
    int math;
  } s1 = {"Lily Chen", 83};

  struct data s2;    //宣告成一樣 
  s2 = s1;           //才可以指定 

  printf("%s, %d\n", s1.name, s1.math);
  printf("%s, %d\n", s2.name, s2.math);

  system("pause");
  return 0;
}

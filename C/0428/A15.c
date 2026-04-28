//https://chatgpt.com/s/m_69f05b05f44c8191be0d2aab07a957a7

//gemini: https://gemini.google.com/share/cd9bc11747c8
#include <stdio.h>
#include <stdlib.h>


/*
        +------------------+
        |      ptr2        |
        |  = &ptr1         |
        +--------+---------+
                 |
                 | *ptr2
                 ▼
        +------------------+
        |      ptr1        |
        |  = &p            |
        +--------+---------+
                 |
                 | *ptr1
                 ▼
        +------------------+
        |       p          |
        |  = 20            |
        +------------------+
        
        
        ptr2 ──> ptr1 ──> p ──> 20
*/
int main(void) {

  int p = 20;
  int *ptr1, **ptr2;

  ptr1 = &p;       // ptr1 指向 p
  ptr2 = &ptr1;    // ptr2 指向 ptr1

  printf("p=%d, &p=%p,\n*ptr1=%d, ptr1=%p, &ptr1=%p\n",
          p, &p, *ptr1, ptr1, &ptr1);

  // ? 修正：把 **ptr%d 改成 **ptr2（純字串）
  printf("**ptr2=%d, *ptr2=%p, ptr2=%p, &ptr2=%p\n",
          **ptr2,   // p 的值
          *ptr2,    // ptr1 的位址
          ptr2,     // &ptr1
          &ptr2);   // ptr2 自己的位址

  system("pause");
  return 0;
}

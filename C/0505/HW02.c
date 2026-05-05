#include <stdio.h>
#include <stdlib.h>

int main(void) {
  struct data {
    char name[15];
    int chinese;
    int physics;
  } student[2], *ptr;

  int i;

  // 將指標指向結構陣列的開頭
  ptr = student;

  // 輸入部分
  for (i = 0; i < 2; i++) {
    printf("Enter Student %d name: ", i + 1);
    // 使用指標算術運算存取：(ptr + i)
    gets((ptr + i)->name);

    printf("Enter chinese score: ");
    scanf("%d", &(ptr + i)->chinese);

    printf("Enter physics score: ");
    scanf("%d", &(ptr + i)->physics);

 
    // while (getchar() != '\n');
    fflush(stdin);
  }

  printf("----------------\n");

  // 輸出部分
  for (i = 0; i < 2; i++) {
    // 題目要求：皆以指標的算術運算來完成
    printf("Student %s\n", (ptr + i)->name);
    printf("Chinese: %d\n", (ptr + i)->chinese);
    printf("Physics: %d\n", (ptr + i)->physics);
    printf("Total Score: %d\n", (ptr + i)->chinese + (ptr + i)->physics);
    printf("\n");
  }

  system("pause");
  return 0;
}

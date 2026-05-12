/*
1.  試撰寫列舉  machine 內有成員 running / maintenance / failed,
使用者介面>輸入機器生產狀態 1.  生產中  2.  維修中  3.  故障中。
  enum machine
  {

  } state;
輸入 1  印出>  機器正常生產中
輸入 2  印出>  機器目前維修中
輸入 3  印出>  機器目前故障中

*/
#include <stdio.h>
#include <stdlib.h>
enum machine { running=1, maintenance, failed };

void move(enum machine);

int main() {
  int key;
  printf("輸入機器生產狀態 (1-3): ");
  scanf("%d", &key);

  printf("機器目前");
  switch (key) {
  case running:
    printf("生產中\n");
    break;
  case maintenance:
    printf("維修中\n");
    break;
  case failed:
    printf("故障中\n");
    break;
  }

  system("pause");
  return 0;
}

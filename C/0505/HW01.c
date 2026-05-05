/*
1. 設計一個結構儲存你個人電腦的基本資料，包含：

CPU 廠牌 (char:10)
記憶體大小 (int)
作業系統名稱 (char:20)
以及硬碟的大小 (int)

硬碟與記憶體大小都以 G 為單位。

在主程式中以這個結構宣告結構變數 comp，
以前面的方式依序輸入上述的資料，
最後並統一輸出所有輸入的資訊。
*/

#include <stdio.h>
#include <stdlib.h>

int main(void) {
  struct computer {
    char cpu[10];
    int mem;
    char os[20];
    int disk;
  } comp;

  printf("Enter CPU brand: ");
  gets(comp.cpu);
  fflush(stdin);
  printf("Enter memory size(G)): ");
  scanf("%d", &comp.mem);
  fflush(stdin);
  printf("Enter OS name: ");
  gets(comp.os);
  fflush(stdin);
  printf("Enter disk size(G): ");
  scanf("%d", &comp.disk);
  printf("======\n");
  printf("CPU brand: %s\n", comp.cpu);
  printf("Memory size (G): %d\n", comp.mem);
  printf("OS name: %s\n", comp.os);
  printf("Disk size (G): %d\n", comp.disk);

  system("pause");
  return 0;
}

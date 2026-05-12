//
#include <stdio.h>
#include <stdlib.h>

int main() {
  enum week { sun, mon, tue, wed, thu, fri, sat } day;
  for (day = sun; day <= sat; day++) {
    printf("Enumeratuion element: %d \n", day);
  }
  printf("\n");
  for (day = wed; day <= fri; day++) {
    printf("Enumeratuion element: %d \n", day);
  }

  system("pause");
  return 0;
}

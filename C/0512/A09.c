//
#include <stdio.h>
#include <stdlib.h>

int main() {

  enum color { black, brown, red };
  enum color shirt;
  printf("black=%d\n", black);
  shirt = black;
  if (shirt == brown) {
    printf("You choose brown fabric.\n");
  } else {
    printf("You choose not to use brown fabric.\n");
  }

  system("pause");
  return 0;
}

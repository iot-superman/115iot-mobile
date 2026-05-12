//
#include <stdio.h>
#include <stdlib.h>

int main() {
  char key;
  enum color {
    red = 114,
    green = 103,
    blue = 98
  }; // ASCII code of 'r', 'g', 'b'

  enum color trousers;
  do {
    printf("Please input the color (r,g,b): ");
    scanf("%c", &key);
    fflush(stdin);
  } while ((key != red) && (key != green) && (key != blue));
  trousers = key;
  switch (trousers) {
  case red:
    printf("You selected red.\n");
    break;
  case green:
    printf("You selected green.\n");
    break;
  case blue:
    printf("You selected blue.\n");
    break;
  }

  system("pause");
  return 0;
}

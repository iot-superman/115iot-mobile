//
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {

  enum fruit { APPLE, ORANGE, GRAPE };
  enum rgbColor { RED = 10, GREEN, BLUE };

  enum animal { LION = 1, TIGER = 11, BEAR = 21 };

  printf("APPLE=%d\tORANGE=%d\ GRAPE=%d\n", APPLE, ORANGE, GRAPE);

  printf("RED=%d\tGREEN=%d BLUE=%d\n", RED, GREEN, BLUE);

  printf("LION=%d\tTIGER=%d BEAR=%d\n", LION, TIGER, BEAR);

  system("pause");
  return 0;
}

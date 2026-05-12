//
#include <stdio.h>
#include <stdlib.h>
enum direction { north, south, east, west };

void move(enum direction);

int main() {
  move(east);
  move(west);
  system("pause");
  return 0;
}

void move(enum direction earth) {
  switch (earth) {
  case north:
    printf("Move north\n");
    break;
  case south:
    printf("Move south\n");
    break;
  case east:
    printf("Move east\n");
    break;
  case west:
    printf("Move west\n");
    break;
  }
}

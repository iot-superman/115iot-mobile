#include <stdio.h>
#include <stdlib.h>

struct data {
  char name[10];
  int student_id;
  int chinese;
  int english;
};

void result(struct data std);

int main() {

  struct data std_01 = {"JJ Cheng", 2601, 78, 52};
  result(std_01);
  printf("\n\n");
  struct data std_02 = {"JJ Cheng", 2602, 75, 81};
  result(std_02);
   printf("\n");
  system("pause");
  return 0;
}

void result(struct data std) {

  printf("Name: %s\n", std.name);
  printf("Student ID: %d\n", std.student_id);

  if (std.chinese + std.english > 175) {
    printf("Great");
  } else if (std.chinese + std.english > 155) {
    printf("Good");
  } else {
    printf("It require hardwork");
  }

 
}

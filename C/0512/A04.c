#include <stdio.h>
#include <stdlib.h>

struct data {
  char name[10];
  int math;
};

void studentInfo(struct data *, struct data *);

int main() {

  struct data std_01 = {"Fread", 83}, std_02 = {"Candy", 85};

  printf("Before calling studentInfo()\n");
  printf("std_01 Name=%s Score=%d", std_01.name, std_01.math);
  printf(",std_02 Name=%s Score=%d\n", std_02.name, std_02.math);

  studentInfo(&std_01, &std_02);

  printf("After calling studentInfo()\n");
  printf("std_01 Name=%s Score=%d", std_01.name, std_01.math);
  printf(",std_02 Name=%s Score=%d\n", std_02.name, std_02.math);

  system("pause");
  return 0;
}

void studentInfo(struct data *std_01, struct data *std_02) {
  struct data temp;
  temp = *std_01;
  *std_01 = *std_02;
  *std_02 = temp;
}
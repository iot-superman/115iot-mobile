#include <stdio.h>
#include <stdlib.h>

struct data {
  char name[10];
  int math;
};

void add5(struct data *);

int main() {
  struct data std = {"Jeeny", 74};
  printf("Before call add5(),std.math=%d\n", std.math);
  add5(&std);
  printf("After call add5(),std.math=%d\n", std.math);
  system("pause");
  return 0;
}

void add5(struct data *std) { std->math = std->math + 5; }
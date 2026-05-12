#include <stdio.h>
#include <stdlib.h>

struct data {
  char name[15];
  int english;
  int math;
};

int maximum(struct data[]);

int main() {

  int i;
  struct data student[3] = {
      {"Antony", 83, 87}, {"Candy", 85, 80}, {"Anne", 72, 60}};
  i = maximum(student);

  printf("The student %s with the highest math score is %d\n",
         (student + i)->name, (student + i)->math);

  system("pause");
  return 0;
}

int maximum(struct data student[]) {

  int max, i, index = 0;
  max = student->math;
  for (i = 1; i < 3; i++) {
    if ((student + i)->math > max) {
      max = (student + i)->math;
      index = i;
    }
  }
  return index;
}

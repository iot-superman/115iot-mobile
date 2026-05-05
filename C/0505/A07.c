// https://chatgpt.com/c/69f95520-f324-83a3-a28e-f366b7e19cc1
// https://gemini.google.com/share/ada23d9d556e
#include <stdio.h>
#include <stdlib.h>

int main() {
  struct data {
    char name[15];
    int eng;
  };
  struct data student = {"Mary Wang", 75};
  printf("Name:%s\n", student.name);
  printf("Score:%d\n", student.eng);

  system("pause");

  return 0;
}

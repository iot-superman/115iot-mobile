#include <stdio.h>
#include <stdlib.h>

int main(void) {
  struct employee {
    char name[20];
    int salary;
    char dempartment[20];
  } manager = {"William", 60000, "sysetm team"};
  struct employee *ptr;
  ptr = &manager;
  printf("%s\n", ptr->name);
  printf("%d\n", ptr->salary);
  printf("%s\n", ptr->dempartment);

  system("pause");
  return 0;
}

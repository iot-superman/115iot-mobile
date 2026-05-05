// https://chatgpt.com/s/m_69f966cd40ac8191912efbf0913d1cc2
#include <stdio.h>
#include <stdlib.h>

int main(void) {
  struct date {
    int year;
    int month;
    int day;
  };
  struct studentInfo {
    char name[20];
    int math;
    struct date birthday;
  } student = {"Mary Wang", 78, {2002, 5, 28}};
  printf("name=%s, math=%d, Birthday=%d/%d/%d\n", student.name, student.math,
         student.birthday.year, student.birthday.month, student.birthday.day);

  printf("Score:%d\n", student.math);

  system("pause");
  return 0;
}

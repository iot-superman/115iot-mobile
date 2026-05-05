#include <stdio.h>
#include <stdlib.h>
#define Male 1   // 陽光男孩
#define Female 0 // 陰柔女孩

int main(void) // ? 建議寫 (void)
{
  // ================================
  // 定義 struct
  // ================================
  struct studentInfo {
    char name[15];
    int sex; // 1 = male, 0 = female
    int age;
  } student_1 = {"John Lee", 1, 18}, student_2 = {"Mary Wang", 0, 19};

  // ================================
  // Student 1
  // ================================
  printf("%s Gender: ", student_1.name);

  // ?【修正1】避免直接寫 ==1，增加可讀性
  if (student_1.sex == Male) {
    printf("Male");
  } else {
    printf("Female");
  }

  printf("\nAge: %d\n\n", student_1.age); // ?排版優化

  // ================================
  // Student 2
  // ================================
  printf("%s Gender: ", student_2.name);

  if (student_2.sex == Female) {
    printf("Female");
  } else {
    printf("Male");
  }

  printf("\nAge: %d\n", student_2.age);

  system("pause");
  return 0;
}

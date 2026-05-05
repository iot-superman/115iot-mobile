#include <stdlib.h>
#include <stdio.h>
#include <string.h>   // ? 加上

int main(void)
{
    int i;

    struct data
    {
        char name[15];
        int age;
        int math;
    } student[3];

    // ============================
    // 輸入
    // ============================
    for(i = 0; i < 3; i++)
    {
        printf("Name: ");
        gets(student[i].name);   // ?? 依你要求保留

        printf("Age: ");
        scanf("%d", &student[i].age);

        printf("Score: ");
        scanf("%d", &student[i].math);

        getchar();  // 吃掉換行（重要）

        printf("\n");
    }

    // ============================
    // 輸出
    // ============================
    for(i = 0; i < 3; i++)
    {
        printf("%s\nAge: %d Score: %d\n\n",
               student[i].name,
               student[i].age,
               student[i].math);
    }

    system("pause");   // ? 放在 return 前
    return 0;
}

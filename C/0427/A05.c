#include <stdio.h>
#include <string.h>
#include <stdlib.h>   // ? system() 需要這個

int main(void)
{
    // ? 修正1：正確宣告 a, b
    int a = 5, b = 10;

    double c = 6.28;

    // ? 修正2：address 要用 %p 並轉型 (void*)
    printf("a=%4d , sizeof(a)=%d, address:%p\n", a, sizeof(a), (void*)&a);
    printf("b=%4d , sizeof(b)=%d, address:%p\n", b, sizeof(b), (void*)&b);
    printf("c=%6.2f , sizeof(c)=%d, address:%p\n", c, sizeof(c), (void*)&c);

    // ? 修正3 + 4：拼字 + include
    system("pause");

    return 0;
}

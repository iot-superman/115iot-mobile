 //https://chatgpt.com/s/t_69e85c74e6848191b434cfde7a05e593
#include <stdio.h>
#include <stdlib.h>
void func(void);
int a=50;
 
int main(void) {
    a=100;
    printf("Before call func(), a=%d\n",a);
    func();
    func();
 

    func();

    system("pause");
    return 0;
}

void func(void) {
    static int a=100;
    printf("In func(): a=%d\n",a);
    a+=200;
}

 

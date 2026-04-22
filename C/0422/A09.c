//https://chatgpt.com/s/t_69e83c771d708191b81bec6a6a86f3f2
#include <stdio.h>
#include <stdlib.h>

 
int main(void) {
    int a=100;
    printf("Before call func(), a=%d\n",a);
    func();
    printf("After call func(), a=%d\n",a);

    

    system("pause");
    return 0;
}

void func(void) {
    int a=300;
    printf("In func(): a=%d\n",a);
}

 

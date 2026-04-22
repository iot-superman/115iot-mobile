//https://chatgpt.com/s/t_69e8413eaf58819188d69373875d1bc6 
#include <stdio.h>
#include <stdlib.h>
void func(void);
int a=50;
 
int main(void) {
    int a=100;
    printf("Before call func(), a=%d\n",a);
    func();
    printf("After call func(), a=%d\n",a);

    

    system("pause");
    return 0;
}

void func(void) {
      a=a+400;
    printf("In func(): a=%d\n",a);
}

 

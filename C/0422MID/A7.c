/*

試撰寫一程式，請利用 for 迴圈印出 30 到 300 之間，所有：

可以被 7 整除
且可以被 5 整除
且可以被 3 整除 


*/

 
 
#include <stdio.h>
#include <stdlib.h>

int main(void) {
    int i;
    for (i = 30; i <= 300; i++) {
        if (i % 7 == 0 && i % 5 == 0 && i % 3 == 0) {
            printf("%d\n", i);
        }
    }
    
   
    system("pause");
    return 0;
}

 
 

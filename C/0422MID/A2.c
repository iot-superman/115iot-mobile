/*
 
撰寫一個函數可以計算 5!（階乘），並在螢幕上顯示結果。
 
*/
 
#include <stdio.h>
#include <stdlib.h>

#define CalNumber 5

int main(void) {
    printf("%d\n",fac(CalNumber));
    system("pause");
    return 0;
}


int fac(int n) {
    if (n > 1) {
       return n * fac(n - 1);
    }else{
        return 1;
    }
}





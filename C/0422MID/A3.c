/*
 
試撰寫 int cub(int x) 函數，可用來傳回 x 的 8 次方，並利用此函數來計算：

cub(5)（也就是 5^8）

 
*/
 
#include <stdio.h>
#include <stdlib.h>

#define CalNumber 5

int main(void) {
    printf("%d\n",cub8(CalNumber));
    system("pause");
    return 0;
}


int cub8(int x) {    
    int result=1;
    int i;
    for(i=0;i<8;i++){
        result*=x;
    }
    return result;
}





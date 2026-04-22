/*
試利用 #define 定義一巨集函數 CUBIC(X)，可用來計算 X 的 3 次方，並利用此巨集計算：

5^3
2.4^3

*/
#include <stdio.h>
#include <stdlib.h>

#define CUBIC(X) ((X)*(X)*(X))
int main(void) {
    printf("%d\n",CUBIC(5));
    printf("%f\n",CUBIC(2.4));
    return 0;
}






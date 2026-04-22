#include <stdlib.h>
#include <stdio.h>
int fac(int n);
int main(void)
{
    int n;
    printf("Please input a number: ");
    scanf("%d",&n);
    printf("The factorial of %d is %d\n",n,fac(n));
    system("pause");
    return 0;
}
/*
// 計算階層函式：輸入 n
// 遞迴計算 n 的階層並回傳結果
// 邊界條件：n 為 0 時回傳 1
*/
int fac(int n)
{
    if(n>0)   //1..2..n-1   階層公式
    {
        return n*fac(n-1);  //1 2 6 24 120 ...
    }
    else //n=0    邊界條件
    {
        return 1;
    }
    
}

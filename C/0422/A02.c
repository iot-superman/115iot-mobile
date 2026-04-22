#include <stdlib.h>
#include <stdio.h>
void  showChar(char,int);
/*
肈ヘ叫块糷计癸莱à
Please input the layer: 5
    A
   BBB
  CCCCC
 DDDDDDD
EEEEEEEEE
*/

/*
秆肈隔
1. 
2. ダ
3. –糷计㎝ダ计だ琌 n-i-1 ㎝ i*2+1  (计厩Α)
4. –糷ダ计琌 i*2+1  (计厩Α)
5. –糷ダ眖 A 秨﹍–Ω糤 1 ダ
*/
int main(void)
{
    int i,n;
    printf("Please input the layer: ");
    scanf("%d",&n);
    fflush(stdin);
    for (i=0;i<n;i++)
    {
        showChar(32,n-i-1);   //    n-i-1  
        showChar(65+i,i*2+1); //A(65)...B(66)...C(67)...D(68)..... n

        printf("\n");
    }
    printf("\n");
  
    system("pause");
    return 0;
}

/*
﹚才﹚Ω计
把计
    ch璶才
    n璶Ω计
*/
void showChar(char ch,int n)
{
    int i;
    for (i=0;i<n;i++)
    {
        printf("%c",ch);
    }
}

 
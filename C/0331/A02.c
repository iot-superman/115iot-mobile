//https://youtu.be/v8ftMUmwt40
#include<stdio.h>
#include<stdlib.h>
#define ABS(x) x>0?x:-x
int main(void)
{
int i, j=-3;
float x, y=1.35f;
i=ABS(j);
printf("ABS(%d)¡×%d\n",j,i);
x=ABS(y);
printf("ABS(%4.2f)=%4.2f\n x %d = %d",y,x);
system("pause");
return 0;

}


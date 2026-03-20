#include<stdio.h>
#include<stdlib.h>
#define SQUARE n*n
main()
{
int n;
printf("Please input an integer:");
scanf("%d",&n);
fflush(stdin);
printf("%d x %d = %d",n,n,SQUARE);
system("pause");
}


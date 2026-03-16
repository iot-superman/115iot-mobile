//
#include <stdlib.h>
#include <stdio.h>
int main(void)
{
int tea,coffee,total;
	printf("How many bottles of black tea do youwant to buy:\n");
	scanf("%d",&tea);
	printf("How many bottles of black tea do youwant to buy:\n");
	scanf("%d",&coffee);

    total=tea*45+coffee * 60;
	printf("Price:%d\n",total);
	system("pause");
    
	return 0;
}

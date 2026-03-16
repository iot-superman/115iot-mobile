//
#include <stdlib.h>
#include <stdio.h>
int main(void)
{
    int cups=8;
    float discount=0.85f;
    int total=cups*40;
	printf("Buy %d cpus,total %d dollars\n",cups, total);
 	printf("Buy %d cpus and get a discount %4.2f\n",cups+2, discount);
 
	system("pause");
    
	return 0;
}

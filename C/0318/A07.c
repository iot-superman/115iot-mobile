#include <stdio.h>
#include <stdlib.h>

int main(void){
    
    int i=1,sum=0;

	while(i<=10)
	{
		sum+=i;
		i++;
	}
	printf("Accmulate %2d\n",sum);
	system("pasue");
	

    return 0;
}

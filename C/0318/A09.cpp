#include <stdio.h>
#include <stdlib.h>

int main(void){
 	int i=1, j=1;
 	while(i<=9){	
		 	while(j<=9){
		 		printf("%dx%d=%2d\t",i,j, i*j);
		 		j++;
			 }	
			 printf("\n");
			 i++;
			 j=1;
	 }
    system("pause");
    return 0;
}

#include <stdio.h>
#include <stdlib.h>

int main(void){
    char str[3][10]={"Tom","Lily","Bruce Lee"};
    int i;
    
    for (i=0;i<3;i++){
     printf("str[%d]=%s\n",i,str[i]);
	}
	
    system("pause");
    return 0;
}

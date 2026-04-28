//
#include <stdlib.h>
#include <stdio.h>

int main(void)
{
   int num;
   printf("Please input an integer: ");
   scanf("%d", &num);
   if (num>0)
   {
   	    if(num<=30)
   	   {
   	     printf("number betweeen 0-30\n");
		}	
	}
	else
	{
       printf("number less then 0\n");
   }
   
   
   system("pause");
   return 0;
}

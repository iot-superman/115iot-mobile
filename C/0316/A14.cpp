// 
#include <stdlib.h>
#include <stdio.h>

int main(void)
{
   int a=3,b=5,c=6,d=7;
   printf("before calculaion: as=%d, b=%d\n",a,b);
   a+=b;
   printf(" calculaion: a=%d, b=%d\n",a,b);
 
   printf("before calculaion: as=%d, b=%d\n",c,d);
   c*=d;
   printf(" calculaion: c=%d, d=%d\n",c,d);
 
 

   
   system("pause");
   return 0;
}

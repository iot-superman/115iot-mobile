//https://www.youtube.com/watch?v=01q-73QICeQ

#include <stdlib.h>
#include <stdio.h>
int main(void)
{
	//short : -32768 ~ 32767 使用 16 位元 (2 bytes)

	short int sum=0,s=32767;
	sum = s+1;
	printf("s+1 = %d\n",sum);     // -32768 (overflow 溢位) 
	sum = s+2;
	printf("s+2 = %d\n",sum);      //-32767(overflow 溢位)
}


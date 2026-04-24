//Lab02_08    
/*   Basic C Program 
     Local and Global Variable */
#include <HT66F2390.H> 					
#include "MyType.h"					
void swapV(void);					
u8 i,j;					//办跑计曊
void main()
{	i=20; j=50;
	swapV();						
	GCC_NOP();			//砞耞翴芠代
}
void swapV()			//肚㊣			
{ 	u8	temp;			//虫ガ磅︽芠代
	temp=i;
	i=j;
	j=temp;
}
	
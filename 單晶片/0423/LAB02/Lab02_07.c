//Lab02_07    
/*   Basic C Program 
     Function call By Vaule/Reference example*/
#include <HT66F2390.H>   
#include "MyType.h"					
void swapV(u8,u8);					
void swapR(u8* ,u8*);				//指標 Pointer
void main()
{	u8 i=20,j=50;
	swapV(i,j);						//傳值呼叫
	GCC_NOP();						//設斷點觀測
	swapR(&i,&j);					//傳址呼叫
	GCC_NOP();						//& 取址運算子
}
void swapR(u8 *i,  u8 *j)			//Call By Reference
{ 	u8	temp;
	temp=*i;						//* 取值運算子
	*i=*j;
	*j=temp;
}
void swapV(u8 i, u8 j)				//Call By Value
{ 	u8	temp;
	temp=i;
	i=j;
	j=temp;
	GCC_NOP();						//設斷點觀測	
}
	
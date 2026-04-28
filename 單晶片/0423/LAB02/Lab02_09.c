//Lab02_09    
/*   Basic C Program 
     Pointer Demo*/
#include <HT66F2390.H> 				     
#include "MyType.h"					
void main()
{	u8 x=4,iArray[]={1,2,3,4,5};
	u16 y,jArray[]={5,6,7};
	u8 *iptr; u16 *jptr;		
	iptr=&x;
	y=*iptr;						//y=x
	GCC_NOP();						//設斷點觀測	
	iptr=&iArray[0];				//以下以單步執行觀測
	x= *(iptr+2);
	jptr=jArray;					//jptr=&jArray[0],陣列的名稱即代表位址
	y=*(jptr+2);
	y=*jptr++;
	y=*jptr;
	GCC_NOP();			
}
	
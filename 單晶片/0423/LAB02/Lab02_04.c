//Lab02_04    
/*   Basic C Program 
     if,else		   	*/
#include <HT66F2390.H> 
#include "MyType.h"
void main()
{	u8 i=0,j=0;
	while(1)
	{	if(i<=10)
		{	j+=i;				
			i++;
		}	
		else 
		{	break;		
		}
	}
	GCC_NOP();					//for Break Point Only	
}
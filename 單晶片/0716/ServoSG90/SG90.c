// PROGRAM	: SG90.c								2019.0529
// FUNCTION	: STM PWM O/P Mode for Servo Control   	By Steven
#include <HT66F2390.h>
#include "MyType.H"
const u16 TAB_PWM[] = {  						//STM2A Table (Duty Control)	
		1000,3000,5000,3000};					//for HS-522
void Delayms(u16 del);
void main()
{	u8 i;
	_wdtc=0b10101111;							//關閉看們狗計時器
	_pds1=0x20;									//PD6 as STP2
	_stm2c0=0; _stm2c1=0b10101000;			 	//fINT=fSYS/4, PWM O/P Mode //Active HI,Non-Inv
	_stm2rp=160;								//256*160*(1/fINT)=20.480ms
	_stm2al=(u8)2000; _stm2ah=2000>>8;			//Set PWM Duty
	_st2on=1;									//Start STM
	while(1)
	{	for(i=0;i<4;i++)
		{	_stm2al=(u8)TAB_PWM[i];				//Set PWM Duty
		 	_stm2ah=TAB_PWM[i]>>8;
		 	Delayms(1000);
		}	
	}
}
void Delayms(u16 del)
{	u16 i;											//@fSYS=8MH,延遲del*1ms
	for(i=0;i<del;i++) GCC_DELAY(2000);
}
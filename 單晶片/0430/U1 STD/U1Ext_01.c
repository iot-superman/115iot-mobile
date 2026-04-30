//https://chatgpt.com/s/m_69f308a935c081918f94694271192225
// PROGRAM	: U1EXT_01.c							2020.0317
// FUNCTION	: 7 SEGMENT WITH SPEED CONTROL         	By Steven
#include <HT66F2390.h>
#include "MyType.h"
#define		SEG_Port	_pg
#define		SEG_PortC	_pgc
#define		DIP_Port	_pe
#define		DIP_PortC	_pec
#define		DIP_PortPU	_pepu

const u8 SEG_TAB[] = {	0x3F,0x06,0x5B,0x4F,0x66,	//7-Segment Table
			         	0x6D,0x7D,0x07,0x7F,0x67};
void main()
{	u8 i,j,k;
	_wdtc=0b10101111;								//關閉看們狗計時器
	SEG_PortC=0x0;									//Config Port as O/P Mode
	DIP_PortC|=0b00001111;					      	//Config Port[4..0] as I/P
	DIP_PortPU|=0b00001111;				         	//Enable Port[4..0] Pull-up
	while(1)
	{	for (j=0;j<=9;j++)	
		{	SEG_Port=SEG_TAB[j];					//Read Table
		 

        // ⭐ DIP0 = 1 → 暫停（停在當前數字）
		//👉 DIP 往上（ON / 未接地）時，PE0 量到的是「高電位（VDD）」≈ 3.3V 或 5V（看你的系統供電）
        while(DIP_Port & 0x01); //1  =>off
        
			k=(DIP_Port & 0b00001111)*3;       //0~15  =  *0*5 ~15*5
			for(i=0;i<k;i++) GCC_DELAY(20000);		//Delay k*10 mSec
		}
	}
}	
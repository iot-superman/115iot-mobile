// PROGRAM	: U4EXT_01.c							2019.0910  // 此程式為按鍵發出16個音 DO~SI，並顯示8音於七段顯示器
// FUNCTION	: GENERATE TONE By USING DELAY SKILL	By Steven
// NOTE: Using PD6 to Toggle Buzzer
#include "HT66F2390.h"
#include "MyType.h"
#define	pSPK	_pd6
#define	pSPKC	_pdc6							
#define M1  1000000								   	//1000000
#define KeyPort		_pc
#define KeyPortC	_pcc
#define KeyPortPU	_pcpu

const u16 TAB_Pitch[] = {		   					//Pitch Constant
			M1/(523*2*10),M1/(587*2*10),
			M1/(659*2*10),M1/(698*2*10),
			M1/(785*2*10),M1/(880*2*10),
			M1/(988*2*10),M1/(523*2*2*10),
			
			M1/(587*4*10),		
			M1/(659*4*10),M1/(698*4*10),
			M1/(785*4*10),M1/(880L*4*10),
			M1/(988L*4*10),M1/(523L*4*2*10),
			M1/(587L*4*2*10),
			};

const u16 TAB_Duration[] = { 	      				//Duration Constant
			523/2,587/2,659/2,698/2,
			785/2,880/2,988/2,(523*2)/2,
			
			587,659,698,785,
			880,988,523*2,587*2,
			};
/*
const u16 Pitch_TAB[] = {			      			//音調常數建表區
		   fSYS/(523*2*4),fSYS/(587*2*4),fSYS/(659*2*4),
		   fSYS/(698*2*4),fSYS/(785*2*4),fSYS/(880*2*4),
		   fSYS/(998*2*4),fSYS/(523*4*4),fSYS/(587*4*4),
		   fSYS/(659*4*4),fSYS/(698*4*4),fSYS/(785*4*4),
		   fSYS/(880*4*4),fSYS/(998*4*4),fSYS/(523*8*4),
		   fSYS/(587*8*4)};
		   */
			
void PAUSE(u16);

u16 ScanKey(void);
void main()
{	//u8 i;u8
	u16 i;
	u16 j;
	_wdtc=0b10101111;								//關閉看們狗計時器
	pSPKC=0;
	_sledc1^=(3<<6);							//Source Current Control (Level 0/3)										//Config pSPK Pin as Output
	while(1)
	{	//for(i=0;i<8;i++)	   		
		do
		{
			i=ScanKey();
			GCC_DELAY(200000);
				
		}while(i==16);
		
		for(j=0;j<TAB_Duration[i];j++)   //Duration[i] i變固定0~7某數值，則變化發出聲音之變化  
			{	pSPK=!pSPK;				   			//Toggle pSPK State
				PAUSE(TAB_Pitch[i]);
			}
		
		/*
		for(i=1;i<8;i+=2)	   		
		{	for(j=1;j<TAB_Duration[i];j+=2)
			{	pSPK=!pSPK;				   			//Toggle pSPK State
				PAUSE(TAB_Pitch[i]);
			}
		}*/

	}	
}
void PAUSE(u16 i)
{	u16 j;
    for(j=0;j<i;j++) 
    	GCC_DELAY(20);	 //Delay i*10uS

}

u16 ScanKey()
{	u16 i,key=0;
	KeyPortC=0xF0; KeyPortPU=0xF0;			   		//規劃KeyPort[7:4]/[3:0]為輸入/輸出屬性，並致能KeyPort[7:4]提升電阻
	KeyPort=0b11111110;					         	//送出掃描碼KeyPort[3:0]=1110
	for(i=0;i<=3;i++)								//依序檢查四列
	{	if(!(KeyPort & 1<<4)) break;		      	//檢查第0行(KeyPort[4])是否按下
		key++;										//否，按鍵值+1
		if(!(KeyPort & 1<<5)) break;			   	//檢查第1行(KeyPort[5])是否按下
		key++;										//否，按鍵值+1
		if(!(KeyPort & 1<<6)) break;			   	//檢查第2行(KeyPort[6])是否按下
		key++;										//否，按鍵值+1
		if(!(KeyPort & 1<<7)) break;			   	//檢查第3行(KeyPort[7])是否按下
		key++;										//否，按鍵值+1
		KeyPort<<=1; KeyPort|=0b00000001;	   		//更新掃描碼，並確保KeyPort[3:0]只有一個位元為0
	}
	return key;	
}

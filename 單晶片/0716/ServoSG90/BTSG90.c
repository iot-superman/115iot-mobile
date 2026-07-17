// PROGRAM	: BTSG90.c								2021.1202
// FUNCTION	: STM PWM Servo Control 		 		By Steven
//			  Using FanControl APP to Control Servo
// HARDWARE : PD6 as PWM Output			
#include <HT66F2390.h>
#include "MyType.H"
#define	fH		8000000								//MCU HIRC Frequency
#define BR		19200								//Baud Rate(Must Match with HC-05)
void Delayms(u16 del);
void main()
{	s16 pwm;
	_wdtc=0b10101111;								//關閉看們狗計時器
	_pds1=0x20;										//PD6 as STP2
	_stm2c0=0; _stm2c1=0b10101000;			 		//fINT=fSYS/4, PWM O/P Mode //Active HI,Non-Inv
	_stm2rp=160;									//256*160*(1/fINT)=20.480ms
	_stm2al=(u8)3000; _stm2ah=3000>>8;				//Set PWM Duty
	_st2on=1;										//Start STM
	_pas1=0b00110000;								//設置RX0->PA6
	_u0cr1=0b10000000;								//UARTEN0/8-Bit/No_parity//1 Stop Bit///
	_u0cr2=0b01100000;								///RXEN0/BRGH0////	
	_brg0=fH/((u32)16*BR)-1;						//BRGH0=1
	while(1)
	{	while(1)
		{	while(!_rxif0);							//等待UR接收到資料
			pwm=_txr_rxr0;							//取得接收資料
			pwm<<=8;
			while(!_rxif0);							//等待UR接收到資料
			pwm|=_txr_rxr0;
			if(pwm<=4608) break;
			while(!_rxif0);							//等待UR接收到資料
			pwm=_txr_rxr0;							//Dummy Read
		}
		pwm=pwm-2304;								
		if(pwm>2000) pwm=2000;
		else if(pwm<-2000) pwm=-2000;
		pwm+=3000;									//PWM=1000~5000(0.5ms~2.5ms)
		_stm2al=(u8)pwm;							//Set PWM Duty
		_stm2ah=pwm>>8;
		Delayms(10);
			
	}
}
void Delayms(u16 del)
{	u16 i;											//@fSYS=8MH,延遲del*1ms
	for(i=0;i<del;i++) GCC_DELAY(2000);
}
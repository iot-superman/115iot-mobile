// PROGRAM	: U1_2_4.c								2017.0414
// FUNCTION	: 7 SEGMENT LED Demo Program			By Steven
#include "HT66F2390.h"
#include "MyType.h"
#define SEGPort		_pg
#define SEGPortC	_pgc
void Delay10ms(u8);
const u16 SEG_TAB[] = {		      					//7-Segment Table
			(20<<8)+0x3F,(30<<8)+0x06,(40<<8)+0x5B,	//High-Byte:延遲常數
			(50<<8)+0x4F,(60<<8)+0x66,(70<<8)+0x6D,	//Low-Byte:七段顯示碼
			(80<<8)+0x7D,(90<<8)+0x07,(100<<8)+0x7F,
			(110<<8)+0x67};
void main()
{	
//	s8 i;
	volatile u8 i;	  //volatile 不優化變數
	_wdtc=0xAB;										//關閉看們狗計時器
	SEGPortC=0;										//Config Port as O/P Mode
	while(1)
	{
		/*
		//s8:
		for(i=10;i>=0;i--)
		{	SEGPort=(u8)SEG_TAB[i];			   		//取得Low-byte並送至七段顯示器
			Delay10ms(SEG_TAB[i]>>8);				//延遲函式
		}
		hircc^0x08;
		whiel(!hircf);
*/
/*
*/
    //JUMP 2pin up 
    // ========= ① 設定腳位為 OSC =========
   // PB6 = OSC1, PB7 = OSC2
   //ps1
   //pb7pb6000
   //1111 0000
 
    _pbs1 =0xF0;
   
    // ========= ② 啟動 HXT =========
    _hxten = 1;   // Enable External Crystal

    // ========= ③ 設定 HXT 模式 =========
    _hxtm = 1;       // High frequency (>10MHz，例如 11.0592MHz)(MUST FIRST)

    // ========= ④ 等待 HXT 穩定 =========
    while(!_hxtf);   // 等待穩定 flag(MUST　FIRST)
    
    // ========= ⑤ 切換系統時脈 =========
    _fhs = 1;        // 系統 clock 選 HXT 

    // ========= ⑥ 關閉 HIRC =========
    _hircen = 0;     // 關閉內部 RC
    



 
        // u8
		for(i=0;i<10;i++)
		{	SEGPort=(u8)SEG_TAB[9-i];			   		//取得Low-byte並送至七段顯示器
			Delay10ms(SEG_TAB[9-i]>>8);				//延遲函式
		}
 	//    hircc^0x08;
	//	while(!hircf);
		
		
		/*
		// Point 
		u16 *ptr= SEG_TAB;
	   
		for(i=0;i<10;i++)
		{	SEGPort=(u8) *(ptr+9-i);			   		//取得Low-byte並送至七段顯示器
			Delay10ms(*(ptr+9-i)>>8);				//延遲函式
		}
		*/
		
	}
}
void Delay10ms(u8 del)
{	u8 i;											//@fSYS=8MH,延遲del*10ms
	for(i=0;i<del;i++) GCC_DELAY(20000);
}
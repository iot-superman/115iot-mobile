/*
https://share.gemini.google/c0Vxr4RWRM4m

試將 STM2 以 PTM2 取代，並實現程式 U4_2.c 相同的功能（注意：蜂鳴器控制腳位也需適當調整）。
----

功能模組轉換：
把基本定時器 STM2 換成功能更全的 PTM2。
控制與計數暫存器，由 _stm 開頭全換成 _ptm。
啟動與關閉的控制旗標，也由 _st2on 換成 _pt2on。

硬體訊號切換：
原本的 STM2 在比對吻合輸出模式下，硬體訊號叫 STP2。
現在改成 PTM2 模組之後，其硬體輸出訊號會變成 PTP2。

引腳功能重定向：
查詢引腳功能複用表後，可以發現 PTP2 支援輸出到 PD2。
修改引腳複用暫存器 _pds0，將 PD2 切換為硬體輸出。
硬體接線時，必須將蜂鳴器的控制線改接到晶片的 PD2 腳。

*/

#include <HT66F2390.h>
#include "MyType.H"

#define	SEGPort		_pg
#define	SEGPortC	_pgc
#define KeyPort		_pc
#define KeyPortC	_pcc
#define KeyPortPU	_pcpu

#define fSYS   11059200							

const u8 SEG_TAB[] = { 0x3F,0x06,					// 7-Segment Table(0~F)
		0x5B,0x4F,0x66,0x6D,0x7D,0x07,0x7F,
		0x67,0x77,0x7C,0x58,0x5E,0x79,0x71};

const u16 Pitch_TAB[] = {							// 音調常數建表區
		fSYS/(523*2*4),fSYS/(587*2*4),fSYS/(659*2*4),
		fSYS/(698*2*4),fSYS/(785*2*4),fSYS/(880*2*4),
		fSYS/(998*2*4),fSYS/(523*4*4),fSYS/(587*4*4),
		fSYS/(659*4*4),fSYS/(698*4*4),fSYS/(785*4*4),
		fSYS/(880*4*4),fSYS/(998*4*4),fSYS/(523*8*4),
		fSYS/(587*8*4)};

void Delayms(u16);		
u8 ScanKey(void);

void main()
{	u8 Key;
	_wdtc=0b10101111;								// 關閉看門狗計時器
	
#if fSYS==11059200
	_pbs1=0xF0;										// Set PB6/PB7 as OSC1/2 Function
	_hxtm=1;										// Set Mode, ESK303 XTAL=11.059MHz>10MHz
	_hxten=1;										// Enable HXT
	while(!_hxtf);									// Wait Stable
	_fhs=1;											// Switch to HXT
	_hircen=0;										// Disable LIRC	
#endif	
	
	SEGPort=0; SEGPortC=0;							// 清除SEGPort並規劃為輸出屬性
	
	// =======【修改部分 1：將 STM2 換成 PTM2 初始化設定】=======
	_ptm2c0=0; _ptm2c1=0b00110001;					// PTM2:比對吻合輸出模式,CCRA吻合清除
    // =======【補上這行：調整 PD2 源流為 Level 3，聲音才會變大】=======
   _sledc1=0x30;
	
	// =======【修改部分 2：調整蜂鳴器控制腳位至 PD2(PTP2)】=======
	// PDS0 暫存器控制 PD[3:0] 的功能
	// 我們要把 PD2 欄位（PDS0[5:4]）設為 01，其餘保持原樣（假設原本是 00）
	_pds0 = 0b00010000;  // 0x10：將 PD2 的功能切換為 PTP2
	
	while(1)
	{	SEGPort=1<<7;								// 點亮七段顯示器h節段，其餘節段熄滅
		do
		{ 	Key=ScanKey();
			GCC_DELAY(200000); 					// 延遲 100ms
			SEGPort^=(1<<7);						// 七段顯示器h節段亮滅切換
		} while(Key==16);	   						// 等待按壓按鍵
		SEGPort=SEG_TAB[Key];				 	// 查表並顯示按鍵值

		// =======【修改部分 3：寫入 PTM2 比較器暫存器以改變音調】=======
		_ptm2al=Pitch_TAB[Key];		       		// 取得計數時間常數低位元
		_ptm2ah=Pitch_TAB[Key]>>8;         	// 取得計數時間常數高位元

		// =======【修改部分 4：啟動 PTM2 計時器輸出波形】=======
		_pt2on=1;									// 啟動 PTM2 計數
		
		while (ScanKey()!=16);	                 	// 等待按鍵釋放
		
		// =======【修改部分 5：關閉 PTM2 計時器停止波形】=======
		Delayms(300); _pt2on=0; 					// 延遲300mS, 停止 PTM2 計數
	}
}

u8 ScanKey()
{	u8 i,key=0;
	KeyPortC=0xF0; KeyPortPU=0xF0;			   		// 規劃KeyPort[7:4]/[3:0]為輸入/輸出屬性，並致能KeyPort[7:4]提升電阻
	KeyPort=0b11111110;					         	// 送出掃描碼KeyPort[3:0]=1110
	for(i=0;i<=3;i++)								// 依序檢查四列
	{	if(!(KeyPort & 1<<4)) break;		     	// 檢查第0行(KeyPort[4])是否按下
		key++;										// 否，按鍵值+1
		if(!(KeyPort & 1<<5)) break;			   	// 檢查第1行(KeyPort[5])是否按下
		key++;										// 否，按鍵值+1
		if(!(KeyPort & 1<<6)) break;			   	// 檢查第2行(KeyPort[6])是否按下
		key++;										// 否，按鍵值+1
		if(!(KeyPort & 1<<7)) break;			   	// 檢查第3行(KeyPort[7])是否按下
		key++;										// 否，按鍵值+1
		KeyPort<<=1; KeyPort|=0b00000001;	   		// 更新掃描碼，並確保KeyPort[3:0]只有一個位元為0
	}
	return key;	
}

void Delayms(u16 del)
{	u16 i;										
	for(i=0;i<del;i++) GCC_DELAY(fSYS/4000);		// @fSYS,延遲del*1ms
}
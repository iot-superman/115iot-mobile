// PROGRAM :  .c                            2019.0306
// FUNCTION: 8*8 Dot Matrix LED Dynamic Control       By Steven
// NOTE    : 修改為箭頭向下捲動版本
#include <HT66F2390.h>
#include "DotMatrix.H"
#include "MyType.H"

//====================================================
// 8×8 字型資料
//====================================================
const u8 TAB[8] =
{
    0x18,
    0x18,
    0x3C,
    0x7E,
    0xFF,
    0x18,
    0x18,
    0x18
};

volatile u8 ScanCode;
volatile u8 Buffer[16];
volatile u8 *volatile ptr;

//====================================================
// Function Prototype
//====================================================
void Delayms(u16 del);
void Copy2Buffer(const u8 *ptr);
void ShiftDown(void);          // ← 改成向下位移

//====================================================
// Main
//====================================================
void main()
{
    _wdtc = 0b10101111;                     // 關閉看門狗計時器

    ScanPort = 0;
    ScanPortC = 0;                         // Config ScanPort as Output

    DotPort = 0;
    DotPortC = 0;                          // Config DotPort as Output

    // fPSC0=fSYS/4
    // Timer Base0週期 = 4096*(1/fPSC0)
    _psc0r = 0x01;
    _tb0c  = 0x04;
    _tb0e  = 1;

    ptr = Buffer;
    ScanCode = 0b00000001;

    _emi = 1;                              // Enable Global Interrupt

    Copy2Buffer(TAB);                      // 複製圖形至Buffer

    while(1)
    {
        ptr = Buffer;

        _tb0on = 1;                        // 啟動Timer
        Delayms(100);

        while(ScanCode != 1);

        _tb0on = 0;                        // 停止Timer
        DotPort = 0x00;

        //================================================
        // 原本是 ShiftUp();
        // 修改為 ShiftDown();
        //================================================
        ShiftDown();
    }
}

//====================================================
// 將圖形複製到Buffer
// Buffer[0~7] 清0
// Buffer[8~15] 放入圖形
//====================================================
/*
void Copy2Buffer(const u8 *ptr)
{
    u8 i;

    for(i = 0; i < 8; i++)
    {
        Buffer[i] = 0;
        Buffer[i + 8] = *ptr++;
    }
}
*/


//down arraw
void Copy2Buffer(const u8 *ptr)
{
    u8 i;

    for(i=0;i<8;i++)
        Buffer[i]=0;

    // 上下翻轉
    for(i=0;i<8;i++)
        Buffer[8+i]=ptr[7-i];
}



//====================================================
// 向下位移
//
// Buffer:
//
// 0
// 1
// 2
// ...
// 15
//
// ↓
//
// 15
// 0
// 1
// ...
// 14
//====================================================
void ShiftDown(void)
{
    u8 i;
    u8 temp;

    temp = Buffer[15];

    for(i = 15; i > 0; i--)
    {
        Buffer[i] = Buffer[i - 1];
    }

    Buffer[0] = temp;
}

//====================================================
// Timer Base0 Interrupt
//====================================================
DEFINE_ISR(ISR_TB0,0x24)
{
    DotPort = 0x00;

    ScanPort = ScanCode;
    DotPort = *ptr++;

    GCC_RL(ScanCode);

    if(ScanCode == 0b00000001)
    {
        ptr = Buffer;
    }
}

//====================================================
// Delay
// @fSYS = 8MHz
//====================================================
void Delayms(u16 del)
{
    u16 i;

    for(i = 0; i < del; i++)
    {
        GCC_DELAY(2000);
    }
}
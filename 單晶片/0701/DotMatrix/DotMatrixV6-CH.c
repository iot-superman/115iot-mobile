// ============================================================
// PROGRAM	: DotMatrixV6-CH.c
// FUNCTION	: 8*8 Dot Matrix LED 中文動態顯示
// 字串		: "鄭立強的程式"
// ============================================================

#include <HT66F2390.h>
#include "MyType.H"

// ============================================================
// I/O 埠定義 (請根據您的實際接線修改)
// ============================================================

// 假設 ScanPort (行掃描) 使用 PA0~PA7
#define ScanPort    _pa
#define ScanPortC   _pac

// 假設 DotPort (列資料) 使用 PB0~PB7
#define DotPort     _pb
#define DotPortC    _pbc

// 如果您使用其他埠，請修改為：
// #define ScanPort    _pa   // 或 _pb, _pc, _pd, _pe, _pf
// #define DotPort     _pb   // 或 _pa, _pc, _pd, _pe, _pf

// ============================================================
// 中文字模 "鄭立強的程式"
// ============================================================
const u8 FONT_0[8] = { 0xEE, 0x4A, 0xEC, 0xAA, 0xEA, 0x4E, 0xA8, 0x00 };   // 鄭
const u8 FONT_1[8] = { 0x10, 0xFE, 0x00, 0x44, 0x44, 0x08, 0xFE, 0x00 };   // 立
const u8 FONT_2[8] = { 0xC8, 0x5E, 0xD2, 0x9E, 0xC8, 0x4A, 0xDE, 0x00 };   // 強
const u8 FONT_3[8] = { 0x48, 0xEE, 0xB2, 0xEA, 0xAA, 0xA2, 0xE6, 0x00 };   // 的
const u8 FONT_4[8] = { 0xEE, 0x4A, 0xEE, 0x40, 0xEE, 0xC4, 0x4E, 0x00 };   // 程
const u8 FONT_5[8] = { 0x0A, 0xFE, 0x08, 0xE8, 0x48, 0x54, 0xE2, 0x00 };   // 式

const u8* FONT_TABLE[] = { FONT_0, FONT_1, FONT_2, FONT_3, FONT_4, FONT_5 };
const u8 FONT_COUNT = 6;

// ============================================================
// 全域變數
// ============================================================
volatile u8 ScanCode;
volatile u16 Buffer[16];
volatile u16 volatile *ptr;

// ============================================================
// 函數宣告
// ============================================================
void Delayms(u16 del);
void DisplayChar(u8 ms);
void ShiftDn(u8 index);

// ============================================================
// 主程式
// ============================================================
void main()
{
    u8 i, line;
    u8 charIndex;
    
    // ----- 關閉看門狗 -----
    _wdtc = 0b10101111;
    
    // ----- 設定 ScanPort & DotPort 為輸出 -----
    ScanPort = 0;
    ScanPortC = 0x00;          // PA0~PA7 設為輸出
    DotPort = 0;
    DotPortC = 0x00;           // PB0~PB7 設為輸出
    
    // ----- 設定 Timer B0 (掃描中斷) -----
    _psc0r = 0x01;
    _tb0c = 0x04;
    _tb0e = 1;
    
    // ----- 初始化掃描 -----
    ptr = Buffer;
    ScanCode = 0b00000001;
    _emi = 1;                  // 致能全域中斷
    
    // ============================================================
    // 主迴圈：依序顯示 "鄭立強的程式"
    // ============================================================
    while(1)
    {
        for(charIndex = 0; charIndex < FONT_COUNT; charIndex++)
        {
            const u8 *fontPtr = FONT_TABLE[charIndex];
            
            // ----- 逐行掃入 Buffer (垂直捲入效果) -----
            for(line = 0; line < 8; line++)
            {
                // 將字模的第 (7-line) 行放入 Buffer[15]
                Buffer[15] = fontPtr[7 - line];
                
                // 將 Buffer 向左平移 (7-line) 次
                for(i = 0; i < (8 - line); i++)
                {
                    ShiftDn(7 - line);
                    ptr = Buffer;
                    DisplayChar(6);
                }
                
                ptr = Buffer;
                DisplayChar(200);
            }
            
            // 字與字之間的停頓
            Delayms(500);
        }
    }
}

// ============================================================
// 將 Buffer 向下平移 index 格
// ============================================================
void ShiftDn(u8 index)
{
    u8 i;
    for(i = index; i != 0; i--)
    {
        Buffer[i] = Buffer[i - 1];
    }
    Buffer[0] = Buffer[15];
    Buffer[15] = 0;
}

// ============================================================
// 顯示字元 (等待掃描完成)
// ============================================================
void DisplayChar(u8 ms)
{
    _tb0on = 1;
    Delayms(ms);
    while(ScanCode != 1);      // 等待完整 Frame 掃描完成
    _tb0on = 0;
    DotPort = 0x00;
}

// ============================================================
// Timer B0 中斷服務程式 (掃描 8x8 點陣)
// ============================================================
DEFINE_ISR(ISR_TB0, 0x24)
{
    DotPort = 0;               // 先清除資料
    ScanPort = ScanCode;       // 輸出掃描碼 (行)
    DotPort = *ptr++;          // 輸出資料 (列)
    
    GCC_RL(ScanCode);          // 更新掃描碼 (左旋)
    
    if(ScanCode == 0b00000001)
    {
        ptr = Buffer;          // 掃完 8 行，重置指標
    }
}

// ============================================================
// 延遲函數 (fSYS = 8MHz, 約 del * 1ms)
// ============================================================
void Delayms(u16 del)
{
    u16 i;
    for(i = 0; i < del; i++)
    {
        GCC_DELAY(2000);
    }
}
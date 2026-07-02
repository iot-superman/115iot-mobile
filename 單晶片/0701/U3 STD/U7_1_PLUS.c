// PROGRAM  : U7-1_PLUS.c                       2026.0604
// FUNCTION : UART Data Transmit with Multi 7-SEGMENT Display
// REFERENCE: U7-1.c & U3_3_1.c                 By 06  鄭立強

#include <HT66F2390.h>
#include "MyType.H"

#define fH      8000000                         // MCU HIRC Frequency
#define BR      19200                           // Baud Rate(Must Match with HC-05)

#if BR>38400 && fH!=11059200
#error Baud Rate Error !!!
#warning "BR Exceed 38400, must use 11.059MHz HXT as fH to Reduce Baud Rate Deviation."
#endif

// ---- 七段顯示器硬體定義 (參考 U3_3_1.c) ----
#define  SEGPort    _pg
#define  SEGPortC   _pgc
#define  ScanPort   _pe
#define  ScanPortC  _pec

// ---- 藍牙/LED 狀態燈定義 ----
#define  pBLED      _pd4
#define  pBLEDC     _pdc4

// ---- 全域變數宣告 ----
const u8 SEG_TAB[] = {                          // 七段顯示碼建表區(共陰)
                0x3F, 0x06, 0x5B, 0x4F, 0x66,
                0x6D, 0x7D, 0x07, 0x7F, 0x67};

volatile u8 *ptr, ScanCode, Buffer[4];

// ---- 函式原型宣告 ----
void Delayms(u16);

void main()
{
    u8 i; 
    u16 adr;
    
     _wdtc = 0b10101111;                         // 關閉看門狗計時器

#if BR>38400                                    // 當BR>38400,需啟用外部11.059MHz的HXT
    _pbs1 = 0xF0; _hxtm = 1;                    // 指定PB[7:6]為OSC功能
    _hxten = 1; while(!_hxtf);                  // 致能HXT並等待穩定
#endif                                          // 注意ESK303 OSC1/2 Jumper之設定

    // ---- UART0 初始化配置 ----
    _pas1 = 0b11000000;                         // 設置TX0 -> PA7
    _u0cr1 = 0b10000000;                        // UARTEN0 / 8-Bit / No_parity / 1 Stop Bit
    _u0cr2 = 0b10100000;                        // TXEN0 / BRGH0
    _brg0 = fH / ((u32)16 * BR) - 1;            // BRGH0 = 1

    // ---- A/D 轉換器初始化配置 ----
    _sadc0 = 0b00111000;                        // ADEN=ADRFS=1, SAC[3:0]=1000 (選擇AN8)
    _sadc1 = 0x07;                              // SAINS[3:0]=0000 (選擇ANn), SACKS[2:0]=7 (fSYS/128)
    _sadc2 = 0b00000000;                        // 禁能PGA, 並參考電壓為AVDD (5V)
    _pds0 = 0x03;                               // 設置PD0功能為AN8

    // ---- 七段顯示器與周邊 I/O 初始化 ----
    SEGPort = 0; SEGPortC = 0;                  // 規劃 SEGPort (_pg) 為輸出屬性
    ScanPort &= 0xF0; ScanPortC &= 0xF0;        // 規劃 ScanPort[3:0] (_pe) 為輸出屬性
    pBLED = 1; pBLEDC = 0;                      // 規劃 pBLED (_pd4) 為輸出屬性並初值輸出1

    // ---- 七段顯示器顯示緩衝區與指標初值 ----
    ptr = Buffer; 
    ScanCode = 0b00000001;                      // 掃描碼初值從第1位數開始
    for(i = 0; i < 4; i++) Buffer[i] = 0;       // 清空顯示內容

    // ---- Time Base 0 中斷定時初始化 ----
    _psc0r = 0x01;                              // fPSC0 = fSYS / 4
    _tb0c = 0x85;                               // 週期 = 8192 * (1 / fPSC0), 約 4.096ms 掃描一次
    _tb0e = 1;                                  // 致能 TB0 中斷
    _emi = 1;                                   // 致能全域中斷 (EMI)

    while(1)
    {
        // 1. 啟動並讀取 A/D 轉換值
        _start = 1; _start = 0;                 // 啟動 A/D 轉換
        while(_adbz);                           // 等待 A/D 轉換完成
        
        // 2. 透過 UART 發送原始 12-bit A/D 資料
        _txr_rxr0 = _sadol;                     // 傳送結果低八位元
        while(!_txif0); 
        _txr_rxr0 = _sadoh;                     // 傳送結果高四位元
        while(!_txif0);                         // 確保高八位也發送完畢再繼續

        // 3. 將 A/D 值計算轉換為電壓值 (mV) 並填入七段顯示器顯示緩衝區
        adr = ((u16)_sadoh << 8) | _sadol;       // 組合為 12-bit 原始數值
        adr = ((u32)adr * 5000) >> 12;          // 換算電壓值: 5000mV / 4096
        
        Buffer[3] = adr / 1000; adr %= 1000;     // 取得千位數
        Buffer[2] = adr / 100;  adr %= 100;      // 取得百位數 
        Buffer[1] = adr / 10;   adr %= 10;       // 取得十位數
        Buffer[0] = adr;                        // 取得個位數

        // 4. 延遲與狀態燈反轉
        Delayms(300);                           // 延遲 300ms
        pBLED = !pBLED;                         // pBLED 狀態翻轉
    }
}

// ---- Time Base 0 中斷服務程式：負責七段顯示器動態掃描 ----
DEFINE_ISR(ISR_TB0, 0x24)
{
    SEGPort = 0;                                // 暗點亮防鬼影：先關閉七段顯示器
    ScanPort = (ScanPort & 0xF0) | (ScanCode & 0x0F); // 送出低4位掃描碼，保留高4位原本狀態
    SEGPort = SEG_TAB[*ptr++];                  // 送出字型編碼，指標指向下一個位數
    
    GCC_RL(ScanCode);                           // 將掃描碼左移循環更新
    
    if(ScanCode == 0b00010000)                  // 若四個位數都已掃描完畢
    {
        ScanCode = 0b00000001;                  // 重新回到第一位數
        ptr = Buffer;                           // 指標重置回 Buffer 起頭
        SEGPort |= (1 << 7);                    // 依據 U3_3_1.c 邏輯，在千位數點亮小數點 (即顯示 X.XXX V)
    }
}

// ---- 延遲函式 ----
void Delayms(u16 del)
{
    u16 i;                                      // @fSYS = 8MHz, 延遲 del * 1ms
    for(i = 0; i < del; i++) GCC_DELAY(2000);   // Delay 1ms @ 8MHz
}
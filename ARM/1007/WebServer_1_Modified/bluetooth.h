#ifndef __BLUETOOTH_H
#define __BLUETOOTH_H

#include "main.h"
#include "melody_control.h"

/*
 * ============================================================================
 * HC-06 藍牙命令定義（依 2026/10/02 課堂老師版概念）
 * ============================================================================
 * 手機藍牙終端只要送「單一 ASCII 字元」即可控制歌曲：
 *   '0' = 停止
 *   '1' = Song_1：1 kHz 測試音
 *   '2' = Song_2：保留曲目
 *   '3' = Song_3：Nokia Tune
 *   '4' = Song_4：Basic tone 1~21
 *
 * 若要播放《泡沫》Song_5，額外支援：
 *   '5' = Song_5：Bubble
 *
 * HC-06 使用 USART1：
 *   STM32 PA9  / USART1_TX -> HC-06 RX
 *   STM32 PA10 / USART1_RX <- HC-06 TX
 *
 * BUZZER：PA4 / TIM14_CH1
 *
 * App Lamp 命令（依你截圖/老師 bluetooth.h）：
 *   Lamp1: 'x'=ON, 'y'=OFF
 *   Lamp2: 'c'=ON, 'd'=OFF
 *   Lamp3: 'h'=ON, 'i'=OFF
 *   Lamp4: 'j'=ON, 'k'=OFF
 *   Lamp5: 'm'=ON, 'n'=OFF
 */
#define Song_off_cmd   '0'
#define Song_1_cmd     '1'
#define Song_2_cmd     '2'
#define Song_3_cmd     '3'
#define Song_4_cmd     '4'
#define Song_5_cmd     '5'

/* 5 組燈的藍牙單字元命令 */
#define Lamp1_on_cmd   'x'
#define Lamp1_off_cmd  'y'
#define Lamp2_on_cmd   'c'
#define Lamp2_off_cmd  'd'
#define Lamp3_on_cmd   'h'
#define Lamp3_off_cmd  'i'
#define Lamp4_on_cmd   'j'
#define Lamp4_off_cmd  'k'
#define Lamp5_on_cmd   'm'
#define Lamp5_off_cmd  'n'

/* 初始化 USART1 RXNE 中斷。請在 MX_USART1_UART_Init() 後呼叫。 */
void bluetooth_init(void);

/* USART1_IRQHandler 收到 1 Byte 後呼叫；函式只記錄命令，不在 IRQ 裡播歌。 */
void bluetooth_on_rx_byte(uint8_t rxData);

/* 主迴圈查詢 / 取出待處理命令。 */
uint8_t bluetooth_has_pending_request(void);
uint8_t bluetooth_fetch_request(uint8_t *song, uint8_t *command);

/* App Lamp 1~5 待處理命令：lamp=1~5，turnOn=1 開 / 0 關。 */
uint8_t bluetooth_has_pending_lamp_request(void);
uint8_t bluetooth_fetch_lamp_request(uint8_t *lamp, uint8_t *turnOn, uint8_t *command);

/*
 * 2026/10/02 DEBUG：取得最近一筆 USART1 原始接收資料。
 * recognized = 1 表示為有效控制命令；0 表示 CR/LF 或未知字元。
 * 此函式只供 main loop printf 除錯，不在 IRQ 裡做 printf。
 */
uint8_t bluetooth_fetch_rx_debug(uint8_t *rxData, uint8_t *recognized);

/* 印出 USART1 / HC-06 啟動時的重要暫存器狀態。 */
void bluetooth_print_debug_status(void);

#endif /* __BLUETOOTH_H */

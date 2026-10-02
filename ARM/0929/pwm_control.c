#include "main.h"
#include "pwm_control.h"

/* =========================================================
 * TIM3 PWM Channel 1
 * CC1E = CCER bit 0
 * ========================================================= */
void enablePWM_ch1(void)
{
    TIM3->CNT = 0;              // 計數器歸零
    TIM3->CR1 |= 0x1UL;         // CEN = 1，啟動 TIM3
    TIM3->CCER |= 0x1UL;        // CC1E = 1，啟用 CH1 輸出
}

void disablePWM_ch1(void)
{
    TIM3->CCER &= ~0x1UL;       // CC1E = 0，關閉 CH1 輸出
    TIM3->CR1  &= ~0x1UL;       // CEN = 0，停止 TIM3
}

void pwmLevel_ch1(uint32_t level)
{
    TIM3->CCR1 = level;         // 設定 CH1 Duty，比較值
}


/* =========================================================
 * TIM3 PWM Channel 2
 * CC2E = CCER bit 4
 * ========================================================= */
void enablePWM_ch2(void)
{
    TIM3->CNT = 0;              // 計數器歸零
    TIM3->CR1 |= 0x1UL;         // CEN = 1，啟動 TIM3
    TIM3->CCER |= (0x1UL << 4); // CC2E = 1，啟用 CH2 輸出
}

void disablePWM_ch2(void)
{
    TIM3->CCER &= ~(0x1UL << 4);// CC2E = 0，關閉 CH2 輸出
    TIM3->CR1  &= ~0x1UL;       // CEN = 0，停止 TIM3
}

void pwmLevel_ch2(uint32_t level)
{
    TIM3->CCR2 = level;         // 設定 CH2 Duty，比較值
}


/* =========================================================
 * TIM3 PWM Channel 3
 * CC3E = CCER bit 8
 * ========================================================= */
void enablePWM_ch3(void)
{
    TIM3->CNT = 0;              // 計數器歸零
    TIM3->CR1 |= 0x1UL;         // CEN = 1，啟動 TIM3
    TIM3->CCER |= (0x1UL << 8); // CC3E = 1，啟用 CH3 輸出
}

void disablePWM_ch3(void)
{
    TIM3->CCER &= ~(0x1UL << 8);// CC3E = 0，關閉 CH3 輸出
    TIM3->CR1  &= ~0x1UL;       // CEN = 0，停止 TIM3
}

void pwmLevel_ch3(uint32_t level)
{
    TIM3->CCR3 = level;         // 設定 CH3 Duty，比較值
}


/* =========================================================
 * TIM3 PWM Channel 4
 * CC4E = CCER bit 12
 * ========================================================= */
void enablePWM_ch4(void)
{
    TIM3->CNT = 0;               // 計數器歸零
    TIM3->CR1 |= 0x1UL;          // CEN = 1，啟動 TIM3
    TIM3->CCER |= (0x1UL << 12); // CC4E = 1，啟用 CH4 輸出
}

void disablePWM_ch4(void)
{
    TIM3->CCER &= ~(0x1UL << 12);// CC4E = 0，關閉 CH4 輸出
    TIM3->CR1  &= ~0x1UL;        // CEN = 0，停止 TIM3
}

void pwmLevel_ch4(uint32_t level)
{
    TIM3->CCR4 = level;          // 設定 CH4 Duty，比較值
}

#ifndef __PWM_CONTROL_H
#define __PWM_CONTROL_H

#ifdef __cplusplus
extern "C" {
#endif

#include "main.h"

/* TIM3 PWM Channel 1 */
void enablePWM_ch1(void);
void disablePWM_ch1(void);
void pwmLevel_ch1(uint32_t level);

/* TIM3 PWM Channel 2 */
void enablePWM_ch2(void);
void disablePWM_ch2(void);
void pwmLevel_ch2(uint32_t level);

/* TIM3 PWM Channel 3 */
void enablePWM_ch3(void);
void disablePWM_ch3(void);
void pwmLevel_ch3(uint32_t level);

/* TIM3 PWM Channel 4 */
void enablePWM_ch4(void);
void disablePWM_ch4(void);
void pwmLevel_ch4(uint32_t level);

#ifdef __cplusplus
}
#endif

#endif /* __PWM_CONTROL_H */

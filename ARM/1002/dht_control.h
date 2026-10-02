#ifndef __DHT_CONTROL_H__
#define __DHT_CONTROL_H__

#include "main.h"
#include <stdint.h>
#include <stdbool.h>

#ifdef __cplusplus
extern "C" {
#endif

/* DHT22 / AM2303 DATA pin = PA12 */

/* Latest valid values, unit = x10
 * Example:
 *   263 = 26.3 C
 *   658 = 65.8 %RH
 */
extern volatile int16_t  dht22_temperature_x10;
extern volatile uint16_t dht22_humidity_x10;

/* PA12 GPIO helpers */
void set_high(void);
void set_low(void);
void set_output(void);
void set_input(void);

/* Read one DHT22 frame.
 * return true  : success
 * return false : timeout/checksum error
 */
bool getData_DHT22(void);

/* Get latest valid values */
int16_t  DHT22_GetTemperatureX10(void);
uint16_t DHT22_GetHumidityX10(void);

#ifdef __cplusplus
}
#endif

#endif /* __DHT_CONTROL_H__ */

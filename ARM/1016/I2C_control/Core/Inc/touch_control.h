#ifndef TOUCH_CONTROL_H
#define TOUCH_CONTROL_H

#include "main.h"
#include <stdint.h>

/* Global BM command and address */
extern volatile uint8_t command;
extern volatile uint8_t touch_addr;

/* Last received data and error status */
extern volatile uint8_t touch_data;
extern volatile uint8_t touch_error;

/* Configure I2C master write */
void i2c_master_write(void);

/* Configure I2C master read */
void i2c_master_read(void);

/* Read BM touch module data */
void getTouchData(void);

#endif /* TOUCH_CONTROL_H */

#include "touch_control.h"
 

/* Touch read command */
volatile uint8_t command = 0x0DU;

/* BM module 7-bit I2C address */
volatile uint8_t touch_addr = 0x50U;

/* Touch result, 0xFF means no touch */
volatile uint8_t touch_data = 0xFFU;

/* Error status: 0 = OK, 1 = failure */
volatile uint8_t touch_error = 0U;

/* I2C status flags */
#define TOUCH_TXIS     (1UL << 1)
#define TOUCH_RXNE     (1UL << 2)
#define TOUCH_NACKF    (1UL << 4)
#define TOUCH_STOPF    (1UL << 5)
#define TOUCH_TC       (1UL << 6)
#define TOUCH_BERR     (1UL << 8)
#define TOUCH_ARLO     (1UL << 9)
#define TOUCH_BUSY     (1UL << 15)

/* I2C control flags */
#define TOUCH_RD_WRN   (1UL << 10)
#define TOUCH_START    (1UL << 13)
#define TOUCH_STOP     (1UL << 14)
#define TOUCH_AUTOEND  (1UL << 25)

/* Software polling limit; depends on CPU clock */
#define TOUCH_TIMEOUT  1000000UL

/* Report an error flag or timeout */
static uint8_t touch_wait_for(uint32_t flag)
{
    uint32_t timeout = TOUCH_TIMEOUT;

    while ((I2C1->ISR & flag) == 0U)
    {
        /* Check NACK, bus error and arbitration loss */
        if ((I2C1->ISR & (TOUCH_NACKF | TOUCH_BERR | TOUCH_ARLO)) != 0U)
        {
            return 1U;
        }

        if (timeout-- == 0U)
        {
            return 1U;
        }
    }

    return 0U;
}

/* Configure a one-byte master write */
void i2c_master_write(void)
{
    /* Write address and NBYTES in one operation */
    I2C1->CR2 = (1UL << 16) | ((uint32_t)touch_addr << 1);

    /* Keep read mode and AUTOEND disabled */
    I2C1->CR2 &= ~(TOUCH_RD_WRN | TOUCH_AUTOEND);
}

/* Configure a one-byte master read */
void i2c_master_read(void)
{
    /* Set 7-bit address and receive length */
    I2C1->CR2 = (1UL << 16) | ((uint32_t)touch_addr << 1);

    /* Read direction and automatic STOP */
    I2C1->CR2 |= TOUCH_RD_WRN | TOUCH_AUTOEND;
}

/* Send command 0x0D then read one touch byte */
void getTouchData(void)
{
    uint32_t timeout;

    touch_error = 0U;
    touch_data = 0xFFU;

    /* Reject a busy bus before starting */
    timeout = TOUCH_TIMEOUT;
    while ((I2C1->ISR & TOUCH_BUSY) != 0U)
    {
        if (timeout-- == 0U)
        {
            touch_error = 1U;
            return;
        }
    }

    /* Clear stale completion and error flags */
    I2C1->ICR = TOUCH_STOPF | TOUCH_NACKF | TOUCH_BERR | TOUCH_ARLO;

    /* Start the command write */
    i2c_master_write();
    I2C1->CR2 |= TOUCH_START;

    /* Wait until TXDR is ready */
    if (touch_wait_for(TOUCH_TXIS) != 0U)
    {

        goto err;
    }

    /* Write the BM touch command */
    I2C1->TXDR = command;

    /* Wait for transfer complete (no STOP yet) */
    if (touch_wait_for(TOUCH_TC) != 0U)
    {

        goto err;
    }

    /* Switch to read and issue a repeated START */
    i2c_master_read();
    I2C1->CR2 |= TOUCH_START;

    /* Wait for the received byte */
    if (touch_wait_for(TOUCH_RXNE) != 0U)
    {

        goto err;
    }

    /* Read RXDR before the automatic STOP */
    touch_data = (uint8_t)I2C1->RXDR;

    /* Wait for the automatic STOP flag */
    if (touch_wait_for(TOUCH_STOPF) != 0U)
    {

        goto err;
    }

    /* Clear STOP after successful transfer */
    I2C1->ICR = TOUCH_STOPF;

    return;

err:
    /* Record error and invalidate the data */
    touch_error = 1U;
    touch_data = 0xFFU;

    /* Request STOP if a transfer is still active */
    if ((I2C1->ISR & TOUCH_BUSY) != 0U)
    {
        I2C1->CR2 |= TOUCH_STOP;

        /* Allow hardware time to finish STOP */
        timeout = TOUCH_TIMEOUT;
        while (((I2C1->ISR & TOUCH_STOPF) == 0U) && (timeout-- != 0U))
        {
        }
    }

    /* Clear only documented clearable flags */
    I2C1->ICR = TOUCH_NACKF | TOUCH_STOPF | TOUCH_BERR | TOUCH_ARLO;
}

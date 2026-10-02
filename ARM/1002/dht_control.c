#include "dht_control.h"
#include <stdio.h>
#include <stdint.h>
#include <stdbool.h>

/*
 * DHT22 / AM2303
 * DATA = PA12
 *
 * 40-bit frame:
 *   data[0] = humidity high byte
 *   data[1] = humidity low byte
 *   data[2] = temperature high byte
 *   data[3] = temperature low byte
 *   data[4] = checksum
 *
 * checksum = low 8 bits of:
 *   data[0] + data[1] + data[2] + data[3]
 *
 * This implementation does not use TIM6/TIM7/TIM14.
 * It only uses PA12 and short busy-wait loops.
 */

volatile int16_t  dht22_temperature_x10 = 0;
volatile uint16_t dht22_humidity_x10 = 0;

#define DHT22_TIMEOUT_COUNT  30000UL

static inline uint8_t DHT22_ReadPin(void)
{
    return ((GPIOA->IDR & (1UL << 12)) != 0U) ? 1U : 0U;
}

static void DHT22_ShortDelay(void)
{
    volatile uint32_t i;

    for (i = 0U; i < 80U; i++)
    {
        __NOP();
    }
}

void set_high(void)
{
    GPIOA->ODR |= (1UL << 12);
}

void set_low(void)
{
    GPIOA->ODR &= ~(1UL << 12);
}

void set_output(void)
{
    /* PA12 mode = 01, general purpose output */
    GPIOA->MODER &= ~(0x3UL << (12U * 2U));
    GPIOA->MODER |=  (0x1UL << (12U * 2U));
}

void set_input(void)
{
    /* PA12 mode = 00, input */
    GPIOA->MODER &= ~(0x3UL << (12U * 2U));
}

static bool DHT22_WaitLevel(uint8_t level)
{
    uint32_t timeout = 0U;

    while (DHT22_ReadPin() != level)
    {
        timeout++;

        if (timeout > DHT22_TIMEOUT_COUNT)
        {
            return false;
        }
    }

    return true;
}

static bool DHT22_MeasureLevel(uint8_t level, uint32_t *count)
{
    uint32_t c = 0U;

    if (count == NULL)
    {
        return false;
    }

    while (DHT22_ReadPin() == level)
    {
        c++;

        if (c > DHT22_TIMEOUT_COUNT)
        {
            return false;
        }
    }

    *count = c;
    return true;
}

bool getData_DHT22(void)
{
    uint8_t data[5] = {0U, 0U, 0U, 0U, 0U};
    uint32_t bitIndex;

    /* --------------------------------------------------------
     * 1. MCU start signal
     * --------------------------------------------------------
     * DHT22 requires host LOW for at least about 1 ms.
     * Keep teacher's 5 ms start pulse.
     */
    set_output();
    set_low();
    HAL_Delay(5);

    /* Release the bus */
    set_high();
    DHT22_ShortDelay();
    set_input();

    /* --------------------------------------------------------
     * 2. Sensor response
     * --------------------------------------------------------
     * Sensor response sequence:
     *   LOW  about 80 us
     *   HIGH about 80 us
     */

    if (!DHT22_WaitLevel(0U))
    {
        printf("[DHT22] ERROR: no response LOW\r\n");
        return false;
    }

    if (!DHT22_WaitLevel(1U))
    {
        printf("[DHT22] ERROR: response LOW timeout\r\n");
        return false;
    }

    if (!DHT22_WaitLevel(0U))
    {
        printf("[DHT22] ERROR: response HIGH timeout\r\n");
        return false;
    }

    /* --------------------------------------------------------
     * 3. Read 40 bits
     * --------------------------------------------------------
     */
    for (bitIndex = 0U; bitIndex < 40U; bitIndex++)
    {
        uint32_t lowCount = 0U;
        uint32_t highCount = 0U;
        uint8_t byteIndex;
        uint8_t bitValue;

        /* Measure bit LOW period */
        if (!DHT22_MeasureLevel(0U, &lowCount))
        {
            printf("[DHT22] ERROR: bit %lu LOW timeout\r\n",
                   (unsigned long)bitIndex);
            return false;
        }

        /* Measure bit HIGH period */
        if (!DHT22_MeasureLevel(1U, &highCount))
        {
            printf("[DHT22] ERROR: bit %lu HIGH timeout\r\n",
                   (unsigned long)bitIndex);
            return false;
        }

        /* DHT22:
         * bit 0 = short HIGH
         * bit 1 = long HIGH
         *
         * Compare HIGH length with preceding LOW length.
         */
        bitValue = (highCount > lowCount) ? 1U : 0U;

        byteIndex = (uint8_t)(bitIndex / 8U);

        data[byteIndex] <<= 1U;
        data[byteIndex] |= bitValue;
    }

    /* --------------------------------------------------------
     * 4. Checksum
     * --------------------------------------------------------
     */
    {
        uint8_t checksum;

        checksum = (uint8_t)(
            data[0] +
            data[1] +
            data[2] +
            data[3]
        );

        if (checksum != data[4])
        {
            printf("[DHT22] ERROR: checksum calc=0x%02X recv=0x%02X\r\n",
                   checksum,
                   data[4]);

            printf("[DHT22] RAW: %02X %02X %02X %02X %02X\r\n",
                   data[0],
                   data[1],
                   data[2],
                   data[3],
                   data[4]);

            return false;
        }
    }

    /* --------------------------------------------------------
     * 5. Decode humidity
     * --------------------------------------------------------
     */
    dht22_humidity_x10 =
        (uint16_t)(((uint16_t)data[0] << 8U) |
                   ((uint16_t)data[1]));

    /* --------------------------------------------------------
     * 6. Decode temperature
     * --------------------------------------------------------
     */
    {
        uint16_t rawTemperature;

        rawTemperature =
            (uint16_t)(((uint16_t)(data[2] & 0x7FU) << 8U) |
                       ((uint16_t)data[3]));

        if ((data[2] & 0x80U) != 0U)
        {
            dht22_temperature_x10 = -(int16_t)rawTemperature;
        }
        else
        {
            dht22_temperature_x10 = (int16_t)rawTemperature;
        }
    }

    /* --------------------------------------------------------
     * 7. Debug print
     * --------------------------------------------------------
     */
    printf("[DHT22] RAW: %02X %02X %02X %02X %02X\r\n",
           data[0],
           data[1],
           data[2],
           data[3],
           data[4]);

    if (dht22_temperature_x10 < 0)
    {
        int16_t absTemp;

        absTemp = (int16_t)(-dht22_temperature_x10);

        printf("[DHT22] Temperature = -%d.%d C\r\n",
               absTemp / 10,
               absTemp % 10);
    }
    else
    {
        printf("[DHT22] Temperature = %d.%d C\r\n",
               dht22_temperature_x10 / 10,
               dht22_temperature_x10 % 10);
    }

    printf("[DHT22] Humidity = %u.%u %%RH\r\n",
           (unsigned int)(dht22_humidity_x10 / 10U),
           (unsigned int)(dht22_humidity_x10 % 10U));

    return true;
}

int16_t DHT22_GetTemperatureX10(void)
{
    return dht22_temperature_x10;
}

uint16_t DHT22_GetHumidityX10(void)
{
    return dht22_humidity_x10;
}

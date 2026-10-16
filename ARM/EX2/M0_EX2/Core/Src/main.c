/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.c
  * @brief          : Main program body
  ******************************************************************************
  * @attention
  *
  * Copyright (c) 2026 STMicroelectronics.
  * All rights reserved.
  *
  * This software is licensed under terms that can be found in the LICENSE file
  * in the root directory of this software component.
  * If no LICENSE file comes with this software, it is provided AS-IS.
  *
  ******************************************************************************
  */
/* USER CODE END Header */
/* Includes ------------------------------------------------------------------*/
#include "main.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */
#include <stdio.h>  /* V4: printf -> USART2 (ST-LINK Virtual COM Port) */

/* USER CODE END Includes */

/* Private typedef -----------------------------------------------------------*/
/* USER CODE BEGIN PTD */

/* USER CODE END PTD */

/* Private define ------------------------------------------------------------*/
/* USER CODE BEGIN PD */

/* USER CODE END PD */

/* Private macro -------------------------------------------------------------*/
/* USER CODE BEGIN PM */

/* USER CODE END PM */

/* Private variables ---------------------------------------------------------*/
UART_HandleTypeDef huart2;

/* USER CODE BEGIN PV */

/* USER CODE END PV */

/* Private function prototypes -----------------------------------------------*/
void SystemClock_Config(void);
static void MX_GPIO_Init(void);
static void MX_USART2_UART_Init(void);
/* USER CODE BEGIN PFP */

/* USER CODE END PFP */

/* Private user code ---------------------------------------------------------*/
/* USER CODE BEGIN 0 */
/*
 * Ex2 V6: RESET forward/reverse 0.5s LED self-test with UART printf.
 * STM32F072RB, USART2 TX=PA2, RX=PA3, baud=38400, 8-N-1.
 * PC0..PC7 correspond to physical LED1..LED8 ONLY when wired that way.
 * Touch module is active LOW: PB1/PB2/PA4/PC9, EXTI falling edge.
 */
#define EX2_LED_MASK     ((uint16_t)0x00FFU)
#define EX2_ALL_ON       ((uint16_t)0x00FFU)
#define EX2_ALL_OFF      ((uint16_t)0x0000U)
#define EX2_ODD_ON       ((uint16_t)0x0055U)
#define EX2_EVEN_ON      ((uint16_t)0x00AAU)
#define EX2_DEBOUNCE_MS  20U
#define EX2_TEST_ON_MS   500U   /* V6: each LED lights for exactly 500 ms (approximately, excluding UART logging) */

static volatile uint8_t ex2_pending_key = 0U;
static volatile uint32_t ex2_pending_tick = 0U;

/* V4: standard printf output redirected to existing USART2.
 * NOTE: HAL_UART_Transmit blocks, so NEVER call printf in an interrupt ISR.
 * This fputc retarget is intended for the project's Keil Arm Compiler 6.
 */
int fputc(int ch, FILE *stream)
{
  uint8_t byte = (uint8_t)ch;
  (void)stream;
  (void)HAL_UART_Transmit(&huart2, &byte, 1U, 100U);
  return ch;
}

/* V4: exact GPIOC ODR feedback helps distinguish firmware mask from wiring.
 * Reading ODR only confirms software GPIO latch state, NOT real LED current.
 */
static void EX2_SetLeds(uint16_t on_mask)
{
  uint16_t on_bits = (uint16_t)(on_mask & EX2_LED_MASK);
  uint16_t off_bits = (uint16_t)(EX2_LED_MASK & (uint16_t)~on_bits);
  GPIOC->BSRR = (uint32_t)on_bits | ((uint32_t)off_bits << 16U);
}

static void EX2_LogLeds(const char *label, uint16_t requested)
{
  unsigned int odr = (unsigned int)(GPIOC->ODR & EX2_LED_MASK);
  printf("[LED] %s req=0x%02X GPIOC_ODR=0x%02X\r\n",
         label, (unsigned int)(requested & EX2_LED_MASK), odr);
}

/* Interrupt context: only record event, no print/no blocking. */
void HAL_GPIO_EXTI_Callback(uint16_t GPIO_Pin)
{
  uint8_t key = 0U;
  if      (GPIO_Pin == key_1_Pin) key = 1U;
  else if (GPIO_Pin == key_2_Pin) key = 2U;
  else if (GPIO_Pin == key_3_Pin) key = 3U;
  else if (GPIO_Pin == key_4_Pin) key = 4U;
  else return;
  ex2_pending_tick = HAL_GetTick();
  ex2_pending_key = key;
}

static uint8_t EX2_KeyIsLow(uint8_t key)
{
  switch (key)
  {
    case 1U: return HAL_GPIO_ReadPin(key_1_GPIO_Port, key_1_Pin) == GPIO_PIN_RESET;
    case 2U: return HAL_GPIO_ReadPin(key_2_GPIO_Port, key_2_Pin) == GPIO_PIN_RESET;
    case 3U: return HAL_GPIO_ReadPin(key_3_GPIO_Port, key_3_Pin) == GPIO_PIN_RESET;
    case 4U: return HAL_GPIO_ReadPin(key_4_GPIO_Port, key_4_Pin) == GPIO_PIN_RESET;
    default: return 0U;
  }
}

static void EX2_DumpPins(const char *stage, uint16_t requested);

/* V5: print press and mask after debounce, in main loop. */
static void EX2_ProcessKeyEvent(void)
{
  uint8_t key;
  uint32_t tick;
  uint16_t mask;
  const char *mode;

  __disable_irq();
  key = ex2_pending_key;
  tick = ex2_pending_tick;
  __enable_irq();
  if (key == 0U || (uint32_t)(HAL_GetTick() - tick) < EX2_DEBOUNCE_MS)
    return;

  __disable_irq();
  if (ex2_pending_key == key && ex2_pending_tick == tick)
    ex2_pending_key = 0U;
  else
    key = 0U;
  __enable_irq();
  if (key == 0U)
    return;

  if (!EX2_KeyIsLow(key))
  {
    printf("[KEY] KEY%u ignored: no longer LOW after %u ms\r\n",
           (unsigned int)key, (unsigned int)EX2_DEBOUNCE_MS);
    return;
  }

  switch (key)
  {
    case 1U: mask = EX2_ALL_ON;  mode = "ALL ON";   break;
    case 2U: mask = EX2_ALL_OFF; mode = "ALL OFF";  break;
    case 3U: mask = EX2_ODD_ON;  mode = "ODD 1,3,5,7"; break;
    case 4U: mask = EX2_EVEN_ON; mode = "EVEN 2,4,6,8"; break;
    default: return;
  }

  printf("[KEY] KEY%u valid LOW, mode=%s\r\n", (unsigned int)key, mode);
  EX2_SetLeds(mask);
  EX2_LogLeds(mode, mask);
  EX2_DumpPins(mode, mask);
  HAL_GPIO_TogglePin(LD2_GPIO_Port, LD2_Pin);
}

/* V5: Report both software output latch ODR and real pin input sample IDR.
 * IDR can indicate pin voltage, but is not a current measurement and cannot
 * prove an LED is physically lit. For this board PC0..PC7 are GPIO outputs.
 */
static void EX2_DumpPins(const char *stage, uint16_t requested)
{
  uint16_t odr = (uint16_t)(GPIOC->ODR & EX2_LED_MASK);
  uint16_t idr = (uint16_t)(GPIOC->IDR & EX2_LED_MASK);
  printf("[PIN] %-16s req=0x%02X ODR=0x%02X IDR=0x%02X PC6=%u PC7=%u\r\n",
         stage, (unsigned int)requested, (unsigned int)odr,
         (unsigned int)idr, (unsigned int)((idr >> 6U) & 1U),
         (unsigned int)((idr >> 7U) & 1U));
}

/* V6 NEW: set exactly one LED and print both GPIOC ODR/IDR for debugging.
 * The lamp remains on for 500ms; all other LED outputs are driven LOW.
 * No 250ms inter-lamp gaps in this version.
 */
static void EX2_TestOneLed(uint8_t led_number)
{
  uint8_t pin_number = (uint8_t)(led_number - 1U);
  uint16_t mask = (uint16_t)(1U << pin_number);
  char label[22];

  EX2_SetLeds(mask);
  (void)snprintf(label, sizeof(label), "LED%u / PC%u",
                 (unsigned int)led_number, (unsigned int)pin_number);
  EX2_DumpPins(label, mask);
  HAL_Delay(EX2_TEST_ON_MS);
}

/* V6 NEW RESET test order requested:
 * 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 (500ms each)
 * 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 (500ms each)
 * ALL ON for 500ms, then ALL OFF.
 * USART2: 38400 baud, 8N1; press KEY1..KEY4 after self-test.
 */
static void EX2_StartupSelfTest(void)
{
  uint8_t i;
  printf("\r\n========== M0 Ex2 V6 RESET FORWARD/REVERSE DEBUG ==========\r\n");
  printf("USART2=38400 8N1 | LEDs PC0..PC7 | active-HIGH outputs\r\n");
  printf("Touch PB1 PB2 PA4 PC9 | ACTIVE LOW, falling EXTI\r\n");

  printf("[BOOT] FORWARD LED1 -> LED8, 500 ms/LED\r\n");
  for (i = 1U; i <= 8U; ++i)
  {
    EX2_TestOneLed(i);
  }

  printf("[BOOT] REVERSE LED8 -> LED1, 500 ms/LED\r\n");
  for (i = 8U; i >= 1U; --i)
  {
    EX2_TestOneLed(i);
  }

  printf("[BOOT] ALL ON, 500 ms\r\n");
  EX2_SetLeds(EX2_ALL_ON);
  EX2_DumpPins("ALL ON", EX2_ALL_ON);
  HAL_Delay(500U);

  EX2_SetLeds(EX2_ALL_OFF);
  EX2_DumpPins("ALL OFF", EX2_ALL_OFF);
  printf("[BOOT] Finished: ALL OFF. Touch KEY1..KEY4.\r\n");
}

/* USER CODE END 0 */

/**
  * @brief  The application entry point.
  * @retval int
  */
int main(void)
{

  /* USER CODE BEGIN 1 */

  /* USER CODE END 1 */

  /* MCU Configuration--------------------------------------------------------*/

  /* Reset of all peripherals, Initializes the Flash interface and the Systick. */
  HAL_Init();

  /* USER CODE BEGIN Init */

  /* USER CODE END Init */

  /* Configure the system clock */
  SystemClock_Config();

  /* USER CODE BEGIN SysInit */

  /* USER CODE END SysInit */

  /* Initialize all configured peripherals */
  MX_GPIO_Init();
  MX_USART2_UART_Init();
  /* USER CODE BEGIN 2 */
  /* V4: UART is initialized before any printf; no logging inside EXTI ISR. */
  EX2_StartupSelfTest();
  /* USER CODE END 2 */

  /* Infinite loop */
  /* USER CODE BEGIN WHILE */
  while (1)
  {
    /* USER CODE END WHILE */

    /* USER CODE BEGIN 3 */
    EX2_ProcessKeyEvent();
  }
  /* USER CODE END 3 */
}

/**
  * @brief System Clock Configuration
  * @retval None
  */
void SystemClock_Config(void)
{
  RCC_OscInitTypeDef RCC_OscInitStruct = {0};
  RCC_ClkInitTypeDef RCC_ClkInitStruct = {0};
  RCC_PeriphCLKInitTypeDef PeriphClkInit = {0};

  /** Initializes the RCC Oscillators according to the specified parameters
  * in the RCC_OscInitTypeDef structure.
  */
  RCC_OscInitStruct.OscillatorType = RCC_OSCILLATORTYPE_HSI48;
  RCC_OscInitStruct.HSI48State = RCC_HSI48_ON;
  RCC_OscInitStruct.PLL.PLLState = RCC_PLL_NONE;
  if (HAL_RCC_OscConfig(&RCC_OscInitStruct) != HAL_OK)
  {
    Error_Handler();
  }

  /** Initializes the CPU, AHB and APB buses clocks
  */
  RCC_ClkInitStruct.ClockType = RCC_CLOCKTYPE_HCLK|RCC_CLOCKTYPE_SYSCLK
                              |RCC_CLOCKTYPE_PCLK1;
  RCC_ClkInitStruct.SYSCLKSource = RCC_SYSCLKSOURCE_HSI48;
  RCC_ClkInitStruct.AHBCLKDivider = RCC_SYSCLK_DIV1;
  RCC_ClkInitStruct.APB1CLKDivider = RCC_HCLK_DIV1;

  if (HAL_RCC_ClockConfig(&RCC_ClkInitStruct, FLASH_LATENCY_1) != HAL_OK)
  {
    Error_Handler();
  }
  PeriphClkInit.PeriphClockSelection = RCC_PERIPHCLK_USART2;
  PeriphClkInit.Usart2ClockSelection = RCC_USART2CLKSOURCE_PCLK1;
  if (HAL_RCCEx_PeriphCLKConfig(&PeriphClkInit) != HAL_OK)
  {
    Error_Handler();
  }
}

/**
  * @brief USART2 Initialization Function
  * @param None
  * @retval None
  */
static void MX_USART2_UART_Init(void)
{

  /* USER CODE BEGIN USART2_Init 0 */

  /* USER CODE END USART2_Init 0 */

  /* USER CODE BEGIN USART2_Init 1 */

  /* USER CODE END USART2_Init 1 */
  huart2.Instance = USART2;
  huart2.Init.BaudRate = 38400;
  huart2.Init.WordLength = UART_WORDLENGTH_8B;
  huart2.Init.StopBits = UART_STOPBITS_1;
  huart2.Init.Parity = UART_PARITY_NONE;
  huart2.Init.Mode = UART_MODE_TX_RX;
  huart2.Init.HwFlowCtl = UART_HWCONTROL_NONE;
  huart2.Init.OverSampling = UART_OVERSAMPLING_16;
  huart2.Init.OneBitSampling = UART_ONE_BIT_SAMPLE_DISABLE;
  huart2.AdvancedInit.AdvFeatureInit = UART_ADVFEATURE_NO_INIT;
  if (HAL_UART_Init(&huart2) != HAL_OK)
  {
    Error_Handler();
  }
  /* USER CODE BEGIN USART2_Init 2 */

  /* USER CODE END USART2_Init 2 */

}

/**
  * @brief GPIO Initialization Function
  * @param None
  * @retval None
  */
static void MX_GPIO_Init(void)
{
  GPIO_InitTypeDef GPIO_InitStruct = {0};
/* USER CODE BEGIN MX_GPIO_Init_1 */
/* USER CODE END MX_GPIO_Init_1 */

  /* GPIO Ports Clock Enable */
  __HAL_RCC_GPIOC_CLK_ENABLE();
  __HAL_RCC_GPIOF_CLK_ENABLE();
  __HAL_RCC_GPIOA_CLK_ENABLE();
  __HAL_RCC_GPIOB_CLK_ENABLE();

  /*Configure GPIO pin Output Level */
  HAL_GPIO_WritePin(GPIOC, led_01_Pin|led_02_Pin|led_03_Pin|led_04_Pin
                          |led_05_Pin|led_06_Pin|led_07_Pin|led_08_Pin, GPIO_PIN_RESET);

  /*Configure GPIO pin Output Level */
  HAL_GPIO_WritePin(LD2_GPIO_Port, LD2_Pin, GPIO_PIN_RESET);

  /*Configure GPIO pin : B1_Pin */
  GPIO_InitStruct.Pin = B1_Pin;
  GPIO_InitStruct.Mode = GPIO_MODE_IT_FALLING;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  HAL_GPIO_Init(B1_GPIO_Port, &GPIO_InitStruct);

  /*Configure GPIO pins : led_01_Pin led_02_Pin led_03_Pin led_04_Pin
                           led_05_Pin led_06_Pin led_07_Pin led_08_Pin */
  GPIO_InitStruct.Pin = led_01_Pin|led_02_Pin|led_03_Pin|led_04_Pin
                          |led_05_Pin|led_06_Pin|led_07_Pin|led_08_Pin;
  GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
  HAL_GPIO_Init(GPIOC, &GPIO_InitStruct);

  /*Configure GPIO pin : key_3_Pin */
  GPIO_InitStruct.Pin = key_3_Pin;
  GPIO_InitStruct.Mode = GPIO_MODE_IT_FALLING;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  HAL_GPIO_Init(key_3_GPIO_Port, &GPIO_InitStruct);

  /*Configure GPIO pin : LD2_Pin */
  GPIO_InitStruct.Pin = LD2_Pin;
  GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
  HAL_GPIO_Init(LD2_GPIO_Port, &GPIO_InitStruct);

  /*Configure GPIO pins : key_1_Pin key_2_Pin */
  GPIO_InitStruct.Pin = key_1_Pin|key_2_Pin;
  GPIO_InitStruct.Mode = GPIO_MODE_IT_FALLING;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  HAL_GPIO_Init(GPIOB, &GPIO_InitStruct);

  /*Configure GPIO pin : key_4_Pin */
  GPIO_InitStruct.Pin = key_4_Pin;
  GPIO_InitStruct.Mode = GPIO_MODE_IT_FALLING;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  HAL_GPIO_Init(key_4_GPIO_Port, &GPIO_InitStruct);

  /* EXTI grouped IRQs: PB1 -> 0_1, PB2 -> 2_3, PA4/PC9 -> 4_15. */
  HAL_NVIC_SetPriority(EXTI0_1_IRQn, 0, 0);
  HAL_NVIC_EnableIRQ(EXTI0_1_IRQn);
  HAL_NVIC_SetPriority(EXTI2_3_IRQn, 0, 0);
  HAL_NVIC_EnableIRQ(EXTI2_3_IRQn);
  HAL_NVIC_SetPriority(EXTI4_15_IRQn, 0, 0);
  HAL_NVIC_EnableIRQ(EXTI4_15_IRQn);

/* USER CODE BEGIN MX_GPIO_Init_2 */
/* USER CODE END MX_GPIO_Init_2 */
}

/* USER CODE BEGIN 4 */

/* USER CODE END 4 */

/**
  * @brief  This function is executed in case of error occurrence.
  * @retval None
  */
void Error_Handler(void)
{
  /* USER CODE BEGIN Error_Handler_Debug */
  /* User can add his own implementation to report the HAL error return state */
  __disable_irq();
  while (1)
  {
  }
  /* USER CODE END Error_Handler_Debug */
}

#ifdef  USE_FULL_ASSERT
/**
  * @brief  Reports the name of the source file and the source line number
  *         where the assert_param error has occurred.
  * @param  file: pointer to the source file name
  * @param  line: assert_param error line source number
  * @retval None
  */
void assert_failed(uint8_t *file, uint32_t line)
{
  /* USER CODE BEGIN 6 */
  /* User can add his own implementation to report the file name and line number,
     ex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */
  /* USER CODE END 6 */
}
#endif /* USE_FULL_ASSERT */

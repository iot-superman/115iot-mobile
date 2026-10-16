Keil 5 STM32F072 BM Touch I2C Serial - teacher-style register access.

Changed Core/Src/main.c: on PA0 press, immediately read touch key and print
"Key Pressed = N". On PA0 release, print "Key Released". Once per second
regardless of press, call getTouchData() and print "timer 7, 1 sec | value = N".
I2C reading happens in main loop, never inside an interrupt.

USART2 UART configuration in original project: 38400 baud, 8N1.
Use the real ST-LINK VCP COM port (COM16/COM23 depend on the computer).
I2C1 PB8/PB9 and interrupt PA0 must match your actual board wiring.
Touch module convention: 0xFF = no touch; 0 to 7 = key index.

NOTE: This is a source patch; Keil ARMCLANG toolchain and board not available
here. Build in Keil and verify I2C reads with actual hardware.

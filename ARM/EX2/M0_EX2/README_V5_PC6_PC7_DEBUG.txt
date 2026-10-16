M0 Ex2 V5 - PC6/PC7 LED7 LED8 DEBUG

Board: STM32 NUCLEO-F072RB
UART: USART2 (ST-LINK Virtual COM Port), 38400 baud, 8N1, no flow control.

Reset self-test:
1. LED1 to LED8 (PC0..PC7) light individually for 1 second.
2. PC6 ONLY 2 seconds; PC7 ONLY 2 seconds; PC6+PC7 2 seconds; repeat PC6 / PC7 each 2 seconds.
3. All 8 LEDs 2 seconds; then all OFF.

Debug logs:
[PIN] ... req=0x40 ODR=0x40 IDR=0x40 PC6=1 PC7=0
[PIN] ... req=0x80 ODR=0x80 IDR=0x80 PC6=0 PC7=1
Values are expected only if both pins operate correctly and no other inputs are high.
ODR is the output latch; IDR samples pin input voltage. Neither proves whether an LED physically lights.

KEY1=ALL ON; KEY2=ALL OFF; KEY3=ODD 1/3/5/7; KEY4=EVEN 2/4/6/8.

Safety: Unplug USB power before moving jumper wires. Keep current-limiting resistor per LED.
If PC6-only lights both LED7 and LED8, investigate breadboard row connection, LED cathodes/anodes and wiring shorts.
If V5 prints correct req/ODR/IDR but lamps differ, check physical LED mapping and wiring.

Build:
Open MDK-ARM/M0_EX2.uvprojx using Keil; Build (F7), then Load.
Source modified: Core/Src/main.c, USER CODE BEGIN 0 region.
This project has not been compiled in Keil or hardware-verified in this environment.

Reference: https://www.st.com/en/evaluation-tools/nucleo-f072rb.html

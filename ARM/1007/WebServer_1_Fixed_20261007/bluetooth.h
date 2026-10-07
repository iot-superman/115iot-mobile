#ifndef ESP8266_BLUETOOTH_COMMANDS_H
#define ESP8266_BLUETOOTH_COMMANDS_H

/*
 * ESP8266 Arduino 相容版（2026-10-07）
 * 根據老師 STM32 bluetooth.h 的命令定義整理。
 * STM32 原版依賴 main.h、melody_control.h 和 HAL，不可直接在 ESP8266 include。
 * 此檔僅保留共同通訊協定的 ASCII 單字元，Arduino 可直接 include。
 */
#define Song_off_cmd   '0'
#define Song_1_cmd     '1'
#define Song_2_cmd     '2'
#define Song_3_cmd     '3'
#define Song_4_cmd     '4'
#define Song_5_cmd     '5'

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

/* 老師截圖使用 Lamp1_on 等較短名稱，提供別名以維持相容。 */
#define Lamp1_on  Lamp1_on_cmd
#define Lamp1_off Lamp1_off_cmd
#define Lamp2_on  Lamp2_on_cmd
#define Lamp2_off Lamp2_off_cmd
#define Lamp3_on  Lamp3_on_cmd
#define Lamp3_off Lamp3_off_cmd
#define Lamp4_on  Lamp4_on_cmd
#define Lamp4_off Lamp4_off_cmd
#define Lamp5_on  Lamp5_on_cmd
#define Lamp5_off Lamp5_off_cmd

#endif /* ESP8266_BLUETOOTH_COMMANDS_H */

/*
 * uart.h
 *
 *  Created on: May 6, 2023
 *      Author: DAU XUAN THUY
 */

#ifndef INC_UART_H_
#define INC_UART_H_
#include "main.h"

void processData(UART_HandleTypeDef huart,uint8_t* buffer);


#endif /* INC_UART_H_ */

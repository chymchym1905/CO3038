/*
 * uart.c
 *
 *  Created on: May 6, 2023
 *      Author: DAU XUAN THUY
 */
#include "uart.h"
#include "main.h"
#include "string.h"

void processData(UART_HandleTypeDef huart, uint8_t* buffer){
	HAL_UART_Transmit(&huart, buffer,sizeof(buffer), 100);
	if(buffer[2]==49){
//		HAL_UART_Transmit(&huart, buffer,sizeof(buffer), 100);
		if(buffer[1]==78) HAL_GPIO_WritePin(LED1_GPIO_Port, LED1_Pin, SET);
		if(buffer[1]==70) HAL_GPIO_WritePin(LED1_GPIO_Port, LED1_Pin, RESET);
	}
	if(buffer[2]==50){
//		HAL_UART_Transmit(&huart, buffer,sizeof(buffer), 100);
		if(buffer[1]==78) HAL_GPIO_WritePin(LED2_GPIO_Port, LED2_Pin, SET);
		if(buffer[1]==70) HAL_GPIO_WritePin(LED2_GPIO_Port, LED2_Pin, RESET);
	}
}




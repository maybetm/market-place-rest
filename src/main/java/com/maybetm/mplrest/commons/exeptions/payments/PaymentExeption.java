package com.maybetm.mplrest.commons.exeptions.payments;

/**
 * Исключение информирует пользователя о сбое
 * в механизме создания платежа
 *
 * @author zebzeev-sv
 * @version 15.09.2019 22:52
 */
public class PaymentExeption extends RuntimeException {

	public PaymentExeption(String message) {
		super(message);
	}
}

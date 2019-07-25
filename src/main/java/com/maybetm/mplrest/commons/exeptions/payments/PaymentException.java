package com.maybetm.mplrest.commons.exeptions.payments;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение информирует пользователя о сбое
 * в механизме создания платежа.
 *
 * @author zebzeev-sv
 * @version 15.09.2019 22:52
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PaymentException extends RuntimeException {

	public PaymentException(String message) {
		super(message);
	}
}

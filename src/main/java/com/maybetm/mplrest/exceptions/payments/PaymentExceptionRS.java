package com.maybetm.mplrest.exceptions.payments;

import com.maybetm.mplrest.commons.exeptions.AExceptionResponse;

/**
 * @author zebzeev-sv
 * @version 12.08.2019 22:05
 */
public class PaymentExceptionRS extends AExceptionResponse {

	public PaymentExceptionRS(String message, int code, String handlerExceptionType, String exceptionType) {
		super(message, code, handlerExceptionType, exceptionType);
	}
}

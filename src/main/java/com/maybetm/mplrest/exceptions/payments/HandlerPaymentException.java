package com.maybetm.mplrest.exceptions.payments;

import com.maybetm.mplrest.commons.exeptions.AHandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HandlerPaymentException extends AHandlerException <PaymentExceptionRS, PaymentException> {

	@Override
	@ResponseBody
	@ExceptionHandler(PaymentException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public PaymentExceptionRS handle(PaymentException paymentException) {
		return new PaymentExceptionRS(paymentException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
}

package com.maybetm.mplrest.exceptions.security.access_exception;

import com.maybetm.mplrest.commons.exeptions.AHandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zebzeev-sv
 * @version 12.08.2019 22:10
 */
@ControllerAdvice
public class HandlerAccessException extends AHandlerException<AccessExceptionRS, AccessException> {

	@Override
	@ResponseBody
	@ExceptionHandler(AccessException.class)
	@ResponseStatus (HttpStatus.FORBIDDEN)
	public AccessExceptionRS handle(AccessException exception) {
		return new AccessExceptionRS(exception.getMessage(), HttpStatus.FORBIDDEN.value(),
                                 handlerExceptionType, "AccessException");
	}
}

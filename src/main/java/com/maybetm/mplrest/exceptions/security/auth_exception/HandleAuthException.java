package com.maybetm.mplrest.exceptions.security.auth_exception;

import com.maybetm.mplrest.commons.exeptions.AHandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zebzeev-sv
 * @version 12.08.2019 22:15
 */
@ControllerAdvice
public class HandleAuthException extends AHandlerException<AuthExceptionRS, AuthException> {

	@Override
	@ResponseBody
	@ExceptionHandler(AuthException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public AuthExceptionRS handle(AuthException exception) {
		return new AuthExceptionRS(exception.getMessage(), HttpStatus.FORBIDDEN.value(),
                               handlerExceptionType, "AuthException");
	}
}

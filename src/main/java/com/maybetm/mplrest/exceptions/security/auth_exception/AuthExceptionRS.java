package com.maybetm.mplrest.exceptions.security.auth_exception;

import com.maybetm.mplrest.commons.exeptions.AExceptionResponse;

/**
 * @author zebzeev-sv
 * @version 12.08.2019 22:15
 */
public class AuthExceptionRS extends AExceptionResponse {

	public AuthExceptionRS(String message, int code, String handlerExceptionType, String exceptionType) {
		super(message, code, handlerExceptionType, exceptionType);
	}
}

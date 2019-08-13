package com.maybetm.mplrest.exceptions.security.access_exception;

import com.maybetm.mplrest.commons.exeptions.AExceptionResponse;

/**
 * @author zebzeev-sv
 * @version 12.08.2019 22:10
 */
public class AccessExceptionRS extends AExceptionResponse {

	public AccessExceptionRS(String message, int code) {
		super(message, code);
	}
}

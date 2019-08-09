package com.maybetm.mplrest.exceptions.test;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zebzeev-sv
 * @version 08.08.2019 18:21
 */
@ControllerAdvice
public class TestExController
{
  @ExceptionHandler (TextEx.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ApiError handle(TextEx e) {
    return new ApiError(HttpStatus.FORBIDDEN, e.getMessage());
  }
}

package com.maybetm.mplrest.commons.exeptions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maybetm.mplrest.commons.DateTime.ZonedDateTimeSerialization;

import java.time.ZonedDateTime;

/**
 * Базовая модель для ответа,
 * при вызове пользовательских исключений.
 *
 * Используется для кастомизации ответа, в случае возникновения исключений.
 *
 * @author zebzeev-sv
 * @version 09.08.2019 18:11
 */
public abstract class AExceptionResponse
{
  @JsonSerialize(using = ZonedDateTimeSerialization.class)
  private ZonedDateTime time = ZonedDateTime.now();

  private int code;

  private String message;

  private String handlerExceptionType;

  private String exceptionType;

  public AExceptionResponse(String message, int code, String handlerExceptionType, String exceptionType) {
    this.message = message;
    this.code = code;
    this.handlerExceptionType = handlerExceptionType;
    this.exceptionType = exceptionType;
  }

  public String getExceptionType()
  {
    return exceptionType;
  }

  public void setExceptionType(String exceptionType)
  {
    this.exceptionType = exceptionType;
  }

  public String getHandlerExceptionType()
  {
    return handlerExceptionType;
  }

  public void setHandlerExceptionType(String handlerExceptionType)
  {
    this.handlerExceptionType = handlerExceptionType;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public ZonedDateTime getTime()
  {
    return time;
  }

  public void setTime(ZonedDateTime time)
  {
    this.time = time;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

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

  public AExceptionResponse(String message, int code) {
    this.message = message;
    this.code = code;
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

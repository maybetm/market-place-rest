package com.maybetm.mplrest.commons.exeptions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maybetm.mplrest.commons.DateTime.ZonedDateTimeSerialization;

import java.time.ZonedDateTime;

/**
 * Базовая модель для ответа,
 * при вызове пользовательских исключений
 *
 * @author zebzeev-sv
 * @version 09.08.2019 18:11
 */
public abstract class AExceptionResponse
{
  @JsonSerialize(using = ZonedDateTimeSerialization.class)
  private ZonedDateTime time = ZonedDateTime.now();

  public ZonedDateTime getTime()
  {
    return time;
  }

  public void setTime(ZonedDateTime time)
  {
    this.time = time;
  }
}

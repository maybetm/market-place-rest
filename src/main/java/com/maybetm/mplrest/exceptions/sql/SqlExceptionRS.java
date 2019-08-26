package com.maybetm.mplrest.exceptions.sql;

import com.maybetm.mplrest.commons.exeptions.AExceptionResponse;

/**
 * @author zebzeev-sv
 * @version 26.08.2019 10:48
 */
public class SqlExceptionRS extends AExceptionResponse
{
  public SqlExceptionRS(String message, int code, String handlerExceptionType, String exceptionType)
  {
    super(message, code, handlerExceptionType, exceptionType);
  }

}

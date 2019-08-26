package com.maybetm.mplrest.exceptions.sql;

import com.maybetm.mplrest.commons.exeptions.AHandlerException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zebzeev-sv
 * @version 26.08.2019 10:42
 */
@ControllerAdvice
public class HandlerSqlException extends AHandlerException<SqlExceptionRS, DataIntegrityViolationException>
{

  @Override
  @ResponseBody
  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public SqlExceptionRS handle(DataIntegrityViolationException exception)
  {
    final String localizedMessage = "Повторяющееся значение ключа нарушает ограничение уникальности";

    logger.error("localizedMessage: {}; NativeMessage: {};", localizedMessage, exception.getMessage());
    return new SqlExceptionRS(localizedMessage, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                              handlerExceptionType, "DataIntegrityViolationException");

  }

}

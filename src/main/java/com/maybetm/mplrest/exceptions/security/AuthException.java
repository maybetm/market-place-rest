package com.maybetm.mplrest.exceptions.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение используется в случае возникновении ошибки
 * в механизме аутентификации и идентификации
 *
 * @author zebzeev-sv
 * @version 02.08.2019 18:52
 */
@ResponseStatus (HttpStatus.FORBIDDEN)
public class AuthException extends RuntimeException
{
  public AuthException(String message)
  {
    super(message);
  }
}

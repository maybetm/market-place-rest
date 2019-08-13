package com.maybetm.mplrest.exceptions.security.access_exception;

import com.maybetm.mplrest.security.SecurityHandlerInterceptor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение информирует пользователя об отказе доступа к запрашиваемому ресурсу.
 * Использутся в механизме контроля доступа {@link SecurityHandlerInterceptor}.
 *
 * @author zebzeev-sv
 * @version 25.07.2019 16:30
 */
@ResponseStatus (HttpStatus.FORBIDDEN)
public class AccessException extends RuntimeException
{
  public AccessException(String message)
  {
    super(message);
  }
}

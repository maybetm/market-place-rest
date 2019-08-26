package com.maybetm.mplrest.commons.exeptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.MappedSuperclass;

/**
 * Базовый класс для хандлеров исключений.
 * Используется для кастомизации ответов приложения, в случае возникновения исключений.
 *
 * @author zebzeev-sv
 * @version 011.08.2019 17:13
 */
@MappedSuperclass
public abstract class AHandlerException<R extends AExceptionResponse, E> {

  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  protected String handlerExceptionType = this.getClass().getSimpleName();

	public abstract R handle(E exception);
}



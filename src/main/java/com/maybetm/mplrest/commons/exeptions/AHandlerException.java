package com.maybetm.mplrest.commons.exeptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import javax.persistence.MappedSuperclass;

/**
 * Базовый класс для хандлеров исключений.
 * Используется для кастомизации ответов приложения, в случае их возникновения.
 *
 * @author zebzeev-sv
 * @version 011.08.2019 17:13
 */
@MappedSuperclass
public abstract class AHandlerException<R extends AExceptionResponse, E> {

	public abstract R handle(E exception);
}



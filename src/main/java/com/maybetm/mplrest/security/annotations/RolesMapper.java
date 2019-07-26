package com.maybetm.mplrest.security.annotations;

import com.maybetm.mplrest.security.Roles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Маркер ограничения доступа для методов рест контроллера.
 *
 * Отсутствие аннотации над рест методом означает,
 * что метод публичный.
 *
 * @author zebzeev-sv
 * @version 25.07.2019 14:51
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface RolesMapper
{
  Roles[] roles();
}

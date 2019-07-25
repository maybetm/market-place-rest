package com.maybetm.mplrest.security;

import java.lang.annotation.*;

/**
 * @author zebzeev-sv
 * @version 23.07.2019 18:34
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleMapper
{
  String value();
}

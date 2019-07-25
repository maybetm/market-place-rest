package com.maybetm.mplrest.security.annotations;

import com.maybetm.mplrest.security.Roles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author zebzeev-sv
 * @version 25.07.2019 14:50
 */
@Target (ElementType.METHOD)
public @interface RoleMapper
{
    Roles value();
}

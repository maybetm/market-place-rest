package com.maybetm.configuration;

import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zebzeev-sv
 * @version 30.09.2019 1:59
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(initializers = {EmbeddedPostgresInitializer.class})
public @interface EmbeddedPostgresTest {
}

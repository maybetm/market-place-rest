package com.maybetm.integration.configuration;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

import java.io.IOException;

/**
 * @author zebzeev-sv
 * @version 29.09.2019 23:37
 */
public class EmbeddedPostgresInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

	@Override
	public void initialize(GenericApplicationContext applicationContext) {
		final EmbeddedPostgres postgres = new EmbeddedPostgres();

		try {
			String url = postgres.start();
			TestPropertyValues values = TestPropertyValues.of(
					"spring.test.database.replace=none",
					"spring.datasource.url=" + url,
					"spring.datasource.driver-class-name=org.postgresql.Driver",
					"spring.jpa.hibernate.ddl-auto=update",
					"spring.jpa.open-in-view=true",
					"spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true"
					);

			values.applyTo(applicationContext);

			applicationContext.registerBean(EmbeddedPostgres.class, () -> postgres,
					beanDefinition -> beanDefinition.setDestroyMethodName("stop"));
		} catch (IOException e) {
			postgres.stop();
			throw new RuntimeException(e);
		}
	}

}
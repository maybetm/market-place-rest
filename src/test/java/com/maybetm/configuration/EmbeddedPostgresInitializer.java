package com.maybetm.configuration;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author zebzeev-sv
 * @version 29.09.2019 23:37
 */
public class EmbeddedPostgresInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

	@Override
	public void initialize(GenericApplicationContext applicationContext) {
		final EmbeddedPostgres postgres = new EmbeddedPostgres();

		try {
			final String url = postgres.start();
			TestPropertyValues values = TestPropertyValues.of(
					"spring.test.database.replace=none",
					"spring.datasource.url=" + url,
					"spring.datasource.username=" + EmbeddedPostgres.DEFAULT_USER,
					"spring.datasource.password=" + EmbeddedPostgres.DEFAULT_PASSWORD,
					"spring.datasource.driver-class-name=org.postgresql.Driver",
					"spring.jpa.hibernate.ddl-auto=update",
					"spring.jpa.open-in-view=true"
					);

			values.applyTo(applicationContext);

			applicationContext.registerBean(EmbeddedPostgres.class, () -> postgres,
					beanDefinition -> beanDefinition.setDestroyMethodName("stop"));
		} catch (IOException e) {
			postgres.stop();
			throw new RuntimeException(e);
		}
	}

	private void initialize (String url) throws SQLException {

	}

}
package com.maybetm.configuration;

import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

import java.io.IOException;

/**
 * fixme тут надо что-то придумать с конфигурацией
 *
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
          "spring.jpa.open-in-view=true",
          "spring.datasource.initialization-mode=always",
          "spring.datasource.data=" +
          "classpath*:sql/import_roles.sql," +
          "classpath*:sql/import_accounts.sql," +
          "classpath*:sql/import_category.sql"
      );
      values.applyTo(applicationContext);

     final BeanDefinitionCustomizer postgresStop = (beanDefinition) -> beanDefinition.setDestroyMethodName("stop");
      applicationContext.registerBean(EmbeddedPostgres.class, () -> postgres, postgresStop);
    } catch (IOException e) {
      postgres.stop();
      throw new RuntimeException(e);
    }
  }

/*  private String start (EmbeddedPostgres postgres) throws IOException
  {
    List<String> additionalParams = asList(
        "-E", "UTF-8",
        "--locale=ru_RU",
        "--lc-collate=C",
        "--lc-ctype=C");
    return postgres.start(EmbeddedPostgres.DEFAULT_HOST,
                          findFreePort(), EmbeddedPostgres.DEFAULT_DB_NAME,
                          EmbeddedPostgres.DEFAULT_USER, EmbeddedPostgres.DEFAULT_PASSWORD, additionalParams);
  }*/
}
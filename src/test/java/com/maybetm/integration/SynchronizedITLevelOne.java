package com.maybetm.integration;

import com.maybetm.integration.configuration.AITest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Синхронный интеграционный тест
 * Проверяем базовые возможности рест сервиса
 *
 * @author zebzeev-sv
 * @version 29.09.2019 19:06
 */
public class SynchronizedITLevelOne extends AITest {

	@Value("${test.database.version}")
	private String pgVersion;
	@Value("${test.database.host}")
	private String host;
	@Value("${test.database.port}")
	private int port;
	@Value("${test.database.dbName}")
	private String dbName;
	@Value("${test.database.username}")
	private String username;
	@Value("${test.database.password}")
	private String password;


	@Test
	public void test() throws IOException {
		// starting Postgres
		final EmbeddedPostgres postgres = new EmbeddedPostgres(pgVersion);
		final String url = postgres.start(host, port, dbName, username, password);

		try {
			final Connection conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			postgres.stop();
			e.printStackTrace();
		}
		postgres.stop();
	}
}

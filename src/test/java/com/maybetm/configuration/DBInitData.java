package com.maybetm.configuration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Используется для наполнения бд важными данными (инициализация)
 * перед выполнением тестов
 * fixme по хорошему бы сюда надо инжектить url и делать методы статическими
 *
 * @author zebzeev-sv
 * @version 12.10.2019 15:31
 */
public final class DBInitData {

	private static final String[] scripts = {
			"sql/roles-mapper.sql",
			"sql/category-mapper.sql",
			"sql/accounts-mapper.sql"};

	private final String url;

	public DBInitData(String url) {
		this.url = url;
	}

	public void execute() throws SQLException {
		for (String script : scripts) {
			executeScript(script);
		}
	}

	private void executeScript(String script)  throws SQLException  {
		final Connection connection = getConnection();
		final EncodedResource resource = new EncodedResource(new ClassPathResource(script), StandardCharsets.UTF_8);
		ScriptUtils.executeSqlScript(connection, resource);
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url);
	}
}

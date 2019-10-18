package com.maybetm.integration.sync_test;

import com.maybetm.commons.AITest;
import com.maybetm.integration.sync_test.cases.TestCaseForDefaultAdmin;
import com.maybetm.integration.sync_test.cases.TestCaseForDefaultClient;
import com.maybetm.integration.sync_test.cases.TestCaseForDefaultMarket;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.roles.Role;
import com.maybetm.mplrest.security.constants.Roles;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDateTime;

/**
 * Набор синхронных интеграционных тестов.
 * Все методы должны прогоняться в строгом порядке.
 *
 * @author zebzeev-sv
 * @version 05.10.2019 20:52
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTestS1 extends AITest {

	private static Account admin = new Account("login-admin-test", "mail-admin-test",
			"password-admin-test", LocalDateTime.now(), new Role(Roles.admin.id));

	private static Account market = new Account("login-market-test", "mail-market-test",
      "password-market-test", LocalDateTime.now(), new Role(Roles.market.id));

	private static Account client = new Account("login-client-test", "mail-client-test",
			"password-client-test", LocalDateTime.now(), new Role(Roles.client.id));

	@Test
	public void stepS01() throws Exception {
		// Всякие манипуляции с администратором и безопасностью
		new TestCaseForDefaultAdmin(admin, mockMvc).process();
	}

	@Test
	public void stepS02() throws Exception {
    // Всякие манипуляции с магазинов и продуктами.
    new TestCaseForDefaultMarket(market, mockMvc).process();
	}

	@Test
	public void stepS03() throws Exception {
		// Всякие манипуляции с товарами и покупателем.
		new TestCaseForDefaultClient(client, mockMvc).process();
  }
}

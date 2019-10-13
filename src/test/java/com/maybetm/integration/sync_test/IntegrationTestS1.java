package com.maybetm.integration.sync_test;

import com.maybetm.commons.AITest;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.roles.Role;
import com.maybetm.mplrest.security.constants.Roles;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Синхронный интеграционный тест
 *
 * @author zebzeev-sv
 * @version 05.10.2019 20:52
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTestS1 extends AITest {

	private static Account admin = new Account("login-admin-test", "mail-admin-test",
			"password-admin-test", LocalDateTime.now(), new Role(Roles.admin.id));

	@Test
	public void testCaseForDefaultAdminS01() throws Exception {
		// выполяем первый кейс. Всякие манипуляции с администратором и безопасностью
		new TestCaseForDefaultAdmin(admin, mockMvc).process();
	}

	@Test
	public void testCaseForMarket() throws Exception {
		LoggerFactory.getLogger("").info("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
	}
}

package com.maybetm.integration.sync_test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.security.Token;
import com.maybetm.mplrest.security.constants.SecurityConstants;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author zebzeev-sv
 * @version 05.10.2019 21:21
 */
class TestCaseForDefaultAdmin {

	private final Account admin;
	private final MockMvc mockMvc;
	private MvcResult mvcResult;

	private static final ObjectMapper om = new ObjectMapper();

	TestCaseForDefaultAdmin(Account admin, MockMvc mockMvc) {
		this.admin = admin;
		this.mockMvc = mockMvc;
	}

	/**
	 * 1. Чекаем список аккаунтов. Получаем ошибку "доступ запрещён"
	 * 2.	Пытамся создать учётную запись стандартного админа. Ловим ошибку. Так как запись уже создана.
	 * 3. Идентификация Администратора.Получаем токен.
	 * 5. Удаление ключа из бд.
	 * 7. Ещё одна попытка получить список аккаунтов. Должны болучить болт.
	 */
	void process() throws Exception {
		// Чекаем список аккаунтов
		Assert.assertEquals("Ожидается HTTP статус 403 FORBIDDEN",
				this.getAccounts(), HttpStatus.FORBIDDEN.value());
		// Пытамся создать учётную запись стандартного админа
		Assert.assertEquals("Учётная запись не должна была быть создана.",
				this.createUserAdmin(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		// Получаем jwt ключ для дефолтного администратора
		String admJwt = authLoginAdm();
		// Теперь мы можем получить список учетных записей. Должна быть одна.
		Assert.assertEquals("Должна быть ровно одна учётная запись",
				this.getAccountsWithJwt(admJwt), 1);
		// удаляем пользовательский токен
		destroyToken(admJwt);
		// ещё раз пытаемся получить список аккаунтов
		Assert.assertEquals("Ожидается HTTP статус 403 FORBIDDEN",
				this.getAccounts(), HttpStatus.FORBIDDEN.value());
	}

	private int createUserAdmin() throws Exception {
		mvcResult = mockMvc.perform(post("/account/createAccount")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(om.writeValueAsString(admin)))
				.andReturn();
		return mvcResult.getResponse().getStatus();
	}

	private int getAccounts() throws Exception {
		mvcResult = mockMvc.perform(get("/account/getAccounts")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		return mvcResult.getResponse().getStatus();
	}

	private int getAccountsWithJwt(String jwt) throws Exception {
		mvcResult = mockMvc.perform(get("/account/getAccounts")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.header(SecurityConstants.headerAuth, jwt))
				.andReturn();
		final Set<Account> accounts = om.readValue(mvcResult.getResponse().getContentAsString(),
				new TypeReference<Set<Account>>() {
				});
		return accounts.size();
	}

	private String authLoginAdm() throws Exception {
		mvcResult = mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(om.writeValueAsString(admin)))
				.andReturn();
		return om.readValue(mvcResult.getResponse().getContentAsString(), Token.class).getToken();
	}

	private void destroyToken(String token) throws Exception {
		mvcResult = mockMvc.perform(delete("/auth/logout")
				.param("token", token)
				.header(SecurityConstants.headerAuth, token))
				.andReturn();
	}
}

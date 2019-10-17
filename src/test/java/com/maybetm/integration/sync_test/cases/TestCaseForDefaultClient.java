package com.maybetm.integration.sync_test.cases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.security.Token;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author zebzeev-sv
 * @version 17.10.2019 21:25
 */
public class TestCaseForDefaultClient {

	private static final ObjectMapper om = new ObjectMapper();

	private final MockMvc mockMvc;
	private final Account clientRQ;
	private MvcResult mvcResult;

	public TestCaseForDefaultClient(Account client, MockMvc mockMvc) {
		this.mockMvc = mockMvc;
		this.clientRQ = client;
	}

	/**
	 * 	1. Регистрируем пользователя с ролью "Клиент".
	 * 	2. Получаем токен.
	 * 	3. Смотрим количество товаров.
	 * 	4. Добавляем товары в корзину пользователя. Скупаем все имеющиеся товары.
	 * 	5. Получаем данные корзины. Проверяем количество продуктов лежащих в корзине.
	 * 	6. Удаляем пользовательский токен.
	 * 	7. Пытаемся получить ещё раз данные корзины. Должны получить ошибку доступа.
	 * 	8. Ещё раз получаем пользовательский токен.
	 * 	9. Получаем данные корзины. Проверяем возможна ли покупка.
	 * 	10. Выполняем создание платежа.
	 * 	11. Корзина должна быть пуста.
	 * 	12. Проверяем изменилось ли количество товаров на торговой площадке. Количество всех купленных товаров должно быть
	 * 	равно нулю.
	 * 	13. Удаляем токен.
	 * 	14. Пытаемся ещё раз авторизоваться. Должны получить ошибку доступа.
	 * 	15. Получаем токен.
	 * 	16. Пытаемся получить данные корзины. Корзина должна быть пустой.
	 * 	17. Добавляем ещё какую-то позицию в корзину.
	 * 	18. Пытаемся оплатить. Должны получить ошибку. Так-как товаров нет на складе.
	 * 	19. Удаляем токен пользователя.
	 * 	20. Пытаемся получить данные корзины, ловим ошибку.
	 * 	21. Кейс должен вернуть объект пользователя после успешного прохождения.
	 */
	public void process() throws Exception {
		// регистрация пользователя с ролью "клиент"
		final Account clientRS = regAccountClient();
		Assert.assertEquals("Повторная регистрация. Неожиданный http статус",
				this.regAccountClientStatus(), HttpStatus.INTERNAL_SERVER_ERROR.value());

		// Получаем токен
		final Token token = authLogin();
		Assert.assertEquals("Повторная регистрация. Неожиданный http статус",
				this.getProductCount(), 4L);
	}

	private Account regAccountClient() throws Exception {
		mvcResult = mockMvc.perform(post("/account/createAccount")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(om.writeValueAsBytes(clientRQ)))
				.andReturn();
		return om.readValue(mvcResult.getResponse().getContentAsString(), Account.class);
	}

	private int regAccountClientStatus() throws Exception {
		mvcResult = mockMvc.perform(post("/account/createAccount")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(om.writeValueAsBytes(clientRQ)))
				.andReturn();
		return mvcResult.getResponse().getStatus();
	}

	private Token authLogin() throws Exception {
		mvcResult = mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(om.writeValueAsBytes(clientRQ)))
				.andReturn();
		return om.readValue(mvcResult.getResponse().getContentAsString(), Token.class);
	}

	private long getProductCount() throws Exception {
		mvcResult = mockMvc.perform(get("/product/getProducts")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		return om.readValue(mvcResult.getResponse().getContentAsString(), List.class).size();
	}

}

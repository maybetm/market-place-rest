package com.maybetm.integration.sync_test.cases;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.entities.payments.Payment;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.security.Token;
import com.maybetm.mplrest.security.constants.SecurityConstants;
import org.junit.Assert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
	 * 1. Регистрируем пользователя с ролью "Клиент".
	 * 2. Получаем токен.
	 * 3. Смотрим количество товаров.
	 * 4. Получаем данные корзины. Проверяем количество продуктов лежащих в корзине.
	 * 5. Добавляем товары в корзину пользователя. Скупаем все имеющиеся товары.
	 * 7. Пытаемся получить ещё раз данные корзины без токена.
	 * 8. Выполняем создание платежа 1 с рандомными значениями.
	 * 9. Проверяем корзину. Коризна должна быть пустой.
	 * 10. Проверяем список товаров, товары не должны равняться нулю.
	 * 11. Наполняем корзину. Проводим платёж2. Скупаем все товары.
	 * 12. Проверяем, количество всех товаров равняется нулю.
	 * 13. Проверяем количество платежей у пользователя. Смотрим статистику платежей. Там должно быть ровно столько записей,
	 * сколько платежей мы создали по каждой позиции в корзине.
	 * 14. Удаляем токен. Пытаемся получить доступ к приватному методу пользователя. Завершаем кейс.
	 */
	public void process() throws Exception {
		final Account clientRS = regAccountClient();
		Assert.assertEquals("Повторная регистрация. Неожиданный http статус",
				this.regAccountClientStatus(), INTERNAL_SERVER_ERROR.value());

		final Token token = authLogin();
		Assert.assertEquals("Повторная регистрация. Неожиданный http статус", this.getProductCount(), 4L);
		Assert.assertEquals("Корзина клиента. Неожиданное количество объектов в корзине.",
				this.getClientBasketCount(clientRS.getId(), token.getToken()), 0L);

		final List<Product> productsPayment1 = getProducts();
		// первый этап создания корзины. Покупаем неполное количество товаров.
		Assert.assertEquals("Наполнение корзины. Рандомное количество товаров. Неожиданный HTTP статус",
				this.createBasketLinesRandomCount(productsPayment1, clientRS, token.getToken()), OK.value());

		final List<Basket> basketLinesRandomCount = getBasketByClientId(clientRS.getId(), token.getToken());
		Assert.assertEquals("Выгрузка корзины пользователя. Неизвестное количество объектов в корзине.",
				basketLinesRandomCount.size(), 4L);
		Assert.assertEquals("Выгрузка корзины пользователя без токена. Неверный HTPP статус.",
				this.getBasketByClientIdWithOutToken(clientRS.getId(), ""), FORBIDDEN.value());

		Assert.assertEquals("Создание платежа 1. Неверный HTTP статус",
				this.createPayment(basketLinesRandomCount, clientRS.getId(), token.getToken()), OK.value());

		final Set<Payment> payments = getPaymentsStatistic(clientRS.getId(), token.getToken());
		Assert.assertEquals("Количество купленных товаров. Не верное количество", payments.size(), 4L);
		// чекаем корзину
		Assert.assertEquals("Сверка позиции в корзине пользователя. Не верное количество позиций",
				this.getClientBasketCount(clientRS.getId(), token.getToken()), 0L);

		// Скупаем все оставшиеся товары
		final List<Product> productsPayment2 = getProducts();
		Assert.assertEquals("Наполнение корзины. Скупаем все товары. Неожиданный HTTP статус",
				this.createBasketLinesAllProducts(productsPayment2, clientRS, token.getToken()), OK.value());

		final List<Basket> basketLinesAllProducts = getBasketByClientId(clientRS.getId(), token.getToken());
		Assert.assertEquals("Создание платежа 2. Неверный HTTP статус",
				this.createPayment(basketLinesAllProducts, clientRS.getId(), token.getToken()), OK.value());
		// проверяем что скупили всё
		Assert.assertTrue("Товары должны иметь количество равное нулю. Неверное количество",
				this.productsCountIsZero(this.getProducts()));
		Assert.assertEquals("Должно быть 8 записей в таблице статистики платежей. ",
				this.getPaymentsStatistic(clientRS.getId(), token.getToken()).size(), 8L);

		Assert.assertEquals("Удаляем токен пользователя из системы. Неверный HTTP стаус",
				this.destroyToken(token.getToken()), OK.value());
		Assert.assertEquals("Попытка получить доступ к корзине со старым токеном. Неожиданный HTTP статус",
				this.getClientBasketStatus(clientRS.getId(), token.getToken()), FORBIDDEN.value());
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

	private long getClientBasketCount(long clientId, String token) throws Exception {
		mvcResult = mockMvc.perform(get("/basket/getBasketByClientId")
				.header(SecurityConstants.headerAuth, token)
				.param("id", String.valueOf(clientId))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		return om.readValue(mvcResult.getResponse().getContentAsString(), List.class).size();
	}

	private long getClientBasketStatus(long clientId, String token) throws Exception {
		mvcResult = mockMvc.perform(get("/basket/getBasketByClientId")
				.header(SecurityConstants.headerAuth, token)
				.param("id", String.valueOf(clientId))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		return mvcResult.getResponse().getStatus();
	}

	private List<Product> getProducts() throws Exception {
		mvcResult = mockMvc.perform(get("/product/getProducts")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		return om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Product>>() {});
	}

	private int createBasketLinesRandomCount(List<Product> products, Account client, String token) throws Exception {
		int status = 200;
		for (Product product : products) {
			final int max = 3, min = 1;
			final long count = new Random().nextInt((max - min) + 1) + min;
			status = createBasketLine(new Basket(product, client, count), token);
		}
		return status;
	}

	private int createBasketLinesAllProducts(List<Product> products, Account client, String token) throws Exception {
		int status = 200;
		for (Product product : products) {
			final long count = product.getCount();
			status = createBasketLine(new Basket(product, client, count), token);
		}
		return status;
	}

	private int createBasketLine(Basket basket, String token) throws Exception {
		mvcResult = mockMvc.perform(post("/basket/createBasketLine")
				.header(SecurityConstants.headerAuth, token)
				.content(om.writeValueAsBytes(basket))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		return mvcResult.getResponse().getStatus();
	}

	private List<Basket> getBasketByClientId(long clientId, String token) throws Exception {
		mvcResult = mockMvc.perform(get("/basket/getBasketByClientId")
				.param("id", String.valueOf(clientId))
				.header(SecurityConstants.headerAuth, token)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		return om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Basket>>() {});
	}

	private long getBasketByClientIdWithOutToken(long clientId, String token) throws Exception {
		mvcResult = mockMvc.perform(get("/basket/getBasketByClientId")
				.param("id", String.valueOf(clientId))
				.header(SecurityConstants.headerAuth, token)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		return mvcResult.getResponse().getStatus();
	}

	private int createPayment(List<Basket> baskets, long clientId, String token) throws Exception {
		mvcResult = mockMvc.perform(post("/payments/createPayment")
				.param("clientId", String.valueOf(clientId))
				.content(om.writeValueAsBytes(baskets))
				.header(SecurityConstants.headerAuth, token)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		return mvcResult.getResponse().getStatus();
	}

	private Set<Payment> getPaymentsStatistic(long clientId, String token) throws Exception {
		mvcResult = mockMvc.perform(get("/payments/getPaymentsStatistic")
				.param("clientId", String.valueOf(clientId))
				.header(SecurityConstants.headerAuth, token)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		return om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Set<Payment>>() {
		});
	}

	private boolean productsCountIsZero(List<Product> products) {
		boolean countIsZero = false;
		for (Product product : products) {
			countIsZero = product.getCount().equals(0L);
		}
		return countIsZero;
	}

	private int destroyToken(String token) throws Exception {
		mvcResult = mockMvc.perform(delete("/auth/logout")
				.param("token", token)
				.header(SecurityConstants.headerAuth, token))
				.andReturn();
		return mvcResult.getResponse().getStatus();
	}
}

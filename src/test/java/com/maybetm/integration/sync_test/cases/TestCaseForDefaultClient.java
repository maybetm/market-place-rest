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
import java.util.Set;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
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
   * 	4. Получаем данные корзины. Проверяем количество продуктов лежащих в корзине.
   * 	5. Добавляем товары в корзину пользователя. Скупаем все имеющиеся товары.
	 * 	7. Пытаемся получить ещё раз данные корзины без токена.
	 * 	10. Выполняем создание платежа__.
	 * 	11. Корзина должна быть пуста.
	 * 	12. Проверяем изменилось ли количество товаров на торговой площадке. Количество всех товаров должно быть
	 * 	равно нулю.
	 * 	13. Удаляем токен.
   * 	14. Пытаемся получить доступ к корзине.
   * 	15. Создание платежа.
   * 	16. Проверяем количество платежей у пользователя.
	 */
	public void process() throws Exception {
		final Account clientRS = regAccountClient();
		Assert.assertEquals("Повторная регистрация. Неожиданный http статус",
                        this.regAccountClientStatus(), INTERNAL_SERVER_ERROR.value());

		final Token token = authLogin();
		Assert.assertEquals("Повторная регистрация. Неожиданный http статус", this.getProductCount(), 4L);
    Assert.assertEquals("Корзина клиента. Неожиданное количество объектов в корзине.",
                        this.getClientBasketCount(clientRS.getId(), token.getToken()), 0L);

    final Set<Product> products = getProducts();
    Assert.assertEquals("Наполнение корзины. Неожиданный HTTP статус",
                        this.createBasketLines(products, clientRS, token.getToken()), OK.value());

    final Set<Basket> basketLines = getBasketByClientId(clientRS.getId(), token.getToken());
    Assert.assertEquals("Выгрузка корзины пользователя. Неизвестное количество объектов в корзине.",
                        basketLines.size(), 4L);
    Assert.assertEquals("Выгрузка корзины пользователя без токена. Неверный HTPP статус.",
                        this.getBasketByClientIdWithOutToken(clientRS.getId(), ""), FORBIDDEN.value());
    Assert.assertEquals("Создание платежа. Неверный HTTP статус",
                        this.createPayment(basketLines, clientRS.getId(), token.getToken()), OK.value());

    final Set<Payment> payments = getPaymentsStatistic(clientRS.getId(), token.getToken());
    Assert.assertEquals("Количество купленных товаров. Не верное количество", payments.size(), 4L);
    // чекаем корзину
    Assert.assertEquals("Сверка позиции в корзине пользователя. Не верное количество позиций",
                        this.getClientBasketCount(clientRS.getId(), token.getToken()), 0L);
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

  private Set<Product> getProducts() throws Exception {
    mvcResult = mockMvc.perform(get("/product/getProducts")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andReturn();
    return om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Set<Product>>(){});
  }

  private int createBasketLines (Set<Product> products, Account client, String token) throws Exception {
	  int status = 200;
	  for(Product product : products) {
	     status = createBasketLine(new Basket(product, client, product.getCount()), token);
    }
	  return status;
  }

  private int createBasketLine (Basket basket, String token) throws Exception {
    mvcResult = mockMvc.perform(post("/basket/createBasketLine")
                                    .header(SecurityConstants.headerAuth, token)
                                    .content(om.writeValueAsBytes(basket))
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andReturn();
    return mvcResult.getResponse().getStatus();
  }

  private Set<Basket> getBasketByClientId (long clientId, String token) throws Exception {
    mvcResult = mockMvc.perform(get("/basket/getBasketByClientId")
                                    .param("id", String.valueOf(clientId))
                                    .header(SecurityConstants.headerAuth, token)
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andReturn();
	  return om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Set<Basket>>(){});
  }

  private long getBasketByClientIdWithOutToken (long clientId, String token) throws Exception {
    mvcResult = mockMvc.perform(get("/basket/getBasketByClientId")
                                    .param("id", String.valueOf(clientId))
                                    .header(SecurityConstants.headerAuth, token)
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andReturn();
    return mvcResult.getResponse().getStatus();
  }

  private int createPayment (Set<Basket> baskets, long clientId, String token) throws Exception
  {
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
    return om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Set<Payment>>(){});
  }
}

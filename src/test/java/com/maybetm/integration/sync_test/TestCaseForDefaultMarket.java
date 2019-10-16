package com.maybetm.integration.sync_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.category.Category;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.security.Token;
import com.maybetm.mplrest.security.constants.SecurityConstants;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author zebzeev-sv
 * @version 15.10.2019 11:43
 */
class TestCaseForDefaultMarket
{

  private static final ObjectMapper om = new ObjectMapper();

  private final MockMvc mockMvc;
  // неполная информация о магазине, используется для регистрации
  private final Account marketRQ;
  private MvcResult mvcResult;

  TestCaseForDefaultMarket(Account marketRQ, MockMvc mockMvc)
  {
    this.mockMvc = mockMvc;
    this.marketRQ = marketRQ;
  }

  /*
    1. Регистрируем пользователя с ролью "Магазин".
    2. Пытаемся ещё раз зарегестрироваться.
    3. Проходим процедуру идентификации
    2. Добавляем ~5 товаров в корзину.
    3. Удаляем один случайный товар
    4. Проверяем количество товаров. Должно измениться, в зависимости от того сколько удалили.
    5. Изменяем количество одного из добавленных товаров.
    6. Сверяем поличество.
    8. Вызываем общий метод, для поиска товаров. Товара не должно быть.
    9. Пытаемся найти удалённый товар по его id. Должно вернуться исключение или пустота.
    10. Ищем товар по его названию. Если находим, получаем его id и сверяем с тем id товара, корторый мы удалили.
    11. Нужно как-то запомнить количество товаров, которое осталось в магазине, для следующего кейса.
    12. Удаляем токен.
    13. Пробуем выполнить какой-нибудь рест метод, который доступен только авторизированному пользователю,
    с ролью "Магазин".
    14. Проверяем ответ, должна вернуться ошибка.
    15. После успешного прохождения возвращаем для следующего кейса объект с информацией о пользователе с ролью "магазин".
  */
  void process() throws Exception
  {
    // регистрируем пользователя с ролью "магазин".
    final Account marketRS = regAccountMarket();
    // Пытаемся выполнить повторную регистрацию. Должны словить статус 500.
    Assert.assertEquals("Повторная регистрация. Неожиданный http статус",
                        this.regMarket(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    // выполняем идентификацию и получаем токен
    final Token marketTokenRS = authLogin();
    // создаём товары нашему магазину. Ожидаем http статус ok.
    Assert.assertEquals("Создание товаров. Неожиданный http статус",
                        this.createProducts(marketRS.getId(), marketTokenRS), HttpStatus.OK.value());
    // Сверяем количество товаров на ТП
    Assert.assertEquals("Должно быть 5 товаров на торговой площадке", this.getProductCount(), 5L);
    // удаляем один товар
    Assert.assertEquals("Удаление товара. Не верный HTTP статус",
                        this.deleteProduct(2L, marketTokenRS.getToken()), HttpStatus.OK.value());
    // должно быть 4 позиции
    Assert.assertEquals("Не верное количество товаров на торговой площадке",
                        this.getProductCount(), 4L);
    final Product product = new Product(1L, 4L);
    Assert.assertEquals("Обновление количества выбранного товара. Не верный HTPP статус",
                        this.updateProductCount(product, marketTokenRS.getToken()), HttpStatus.OK.value());

    // fixme наверно стоит что-то поменять с обновлением товара
    Assert.assertEquals("Получение обновлённой информации по товару. Не верное количество",
                        this.getProductById(1L).getCategory().getName(), "Тестовая категория 1");
  }

  private int regMarket() throws Exception {
    mvcResult = mockMvc.perform(post("/account/createAccount")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                    .content(om.writeValueAsBytes(marketRQ)))
        .andReturn();
    return  mvcResult.getResponse().getStatus();
  }

  private Account regAccountMarket() throws Exception {
    mvcResult = mockMvc.perform(post("/account/createAccount")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                    .content(om.writeValueAsBytes(marketRQ)))
        .andReturn();
    return om.readValue(mvcResult.getResponse().getContentAsString(), Account.class);
  }

  private Token authLogin () throws Exception {
    mvcResult = mockMvc.perform(post("/auth/login")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                    .content(om.writeValueAsBytes(marketRQ)))
        .andReturn();
    return om.readValue(mvcResult.getResponse().getContentAsString(), Token.class);
  }

  private int createProducts(long marketId, Token token) throws Exception {
    final Category category = new Category(1L);

    final List<Product> products = new ArrayList<Product>(){{
      add(new Product("product-test-1", "product-test-info-1", 10000L, 10L, marketId, category));
      add(new Product("product-test-2", "product-test-info-2", 20000L, 20L, marketId, category));
      add(new Product("product-test-3", "product-test-info-3", 30000L, 30L, marketId, category));
      add(new Product("product-test-4", "product-test-info-4", 40000L, 40L, marketId, category));
      add(new Product("product-test-5", "product-test-info-5", 50000L, 50L, marketId, category));
    }};
    int status = 200;
    for (Product product : products) {
      status = createProductRQ(product, token);
    }
    return status;
  }

  private int createProductRQ(Product product, Token token) throws Exception {
    mvcResult = mockMvc.perform(post("/product/createProduct")
                      .header(SecurityConstants.headerAuth, token.getToken())
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                    .content(om.writeValueAsBytes(product)))
        .andReturn();
    return mvcResult.getResponse().getStatus();
  }

  private long getProductCount () throws Exception {
    mvcResult = mockMvc.perform(get("/product/getProducts")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andReturn();
    return om.readValue(mvcResult.getResponse().getContentAsString(), List.class).size();
  }

  private int deleteProduct(long productId, String token) throws Exception {
    mvcResult = mockMvc.perform(delete("/product/deleteProduct")
                                    .param("id", String.valueOf(productId))
                                    .header(SecurityConstants.headerAuth, token)
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andReturn();
    return mvcResult.getResponse().getStatus();
  }

  private int updateProductCount (Product product, String token) throws Exception {
    mvcResult = mockMvc.perform(patch("/product/editProduct")
                                    .param("id", String.valueOf(product.getId()))
                                    .content(om.writeValueAsBytes(product))
                                    .header(SecurityConstants.headerAuth, token)
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andReturn();
    return mvcResult.getResponse().getStatus();
  }

  private Product getProductById (long productId)  throws Exception{
    mvcResult = mockMvc.perform(get("/product/getProduct")
                                    .param("id", String.valueOf(productId))
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andReturn();
    return om.readValue(mvcResult.getResponse().getContentAsString(), Product.class);
  }

}

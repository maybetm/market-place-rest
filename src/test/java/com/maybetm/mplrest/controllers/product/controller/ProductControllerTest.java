package com.maybetm.mplrest.controllers.product.controller;

import com.maybetm.mplrest.ATest;
import com.maybetm.mplrest.security.SecurityConstants;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Модульный тест контроллера для товаров {@link ProductController}
 *
 * @author zebzeev-sv
 * @version 05.08.2019 18:43
 */
public class ProductControllerTest extends ATest
{

  private final static String product = "/product/";
  private final static String token = "eyJjcmVhdGlvblRpbWUiOiIyMDE5LTA4LTA2VDE1OjQxOjEwLjkzOSswNTowMCIsInJvbGVJZCI6NCwiaWQiOjEsImFsZyI6IkhTMjU2In0.eyJleHAiOjE1NjUxNzQ0NzB9.AhVVDE9jdg25Dj8xSK7j59xlZobHoy5ow2m8apEnRZw";

  @Test
  public void testGetProduct() throws Exception
  {
    mvcResult = mockMvc.perform(get(product + "getProduct?id=11").header(SecurityConstants.headerAuth, token))
    .andExpect(status().is2xxSuccessful())
    .andReturn();
    logger.info("response: {}", mvcResult.getResponse());
  }

  @Test
  public void getProductsIsSuccessful() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get(product + "getProducts"))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    logger.info(mvcResult.getResponse().getContentAsString());
  }

}
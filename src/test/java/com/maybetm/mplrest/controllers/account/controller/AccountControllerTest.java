package com.maybetm.mplrest.controllers.account.controller;

import com.maybetm.commons.AUnitTest;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.roles.Role;
import com.maybetm.mplrest.security.constants.Roles;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Модульный тест для
 * контроллера accounts {@link AccountController}
 *
 * @author zebzeev-sv
 * @version 02.08.2019 11:54
 */
public class AccountControllerTest extends AUnitTest
{
  private static Account accountClient = new Account("login24", "email24",
      "password24", LocalDateTime.now(), new Role(Roles.client.id));

  @Test
  public void testCreateAccount() throws Exception
  {
    mvcResult = mockMvc.perform(post("/account/createAccount")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                    .content(objectMapper.writeValueAsString(accountClient)))
        .andReturn();
    logger.info("Response: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void testDeleteAccount() throws Exception
  {

  }

  @Test
  public void testUpdateAccount() throws Exception
  {

  }

  @Test
  public void testGetAccounts() throws Exception
  {
    logger.info("Response: ----------------------------------------");
    mvcResult = mockMvc.perform(get("/account/getAccounts")
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andReturn();
    logger.info("Response: {}", mvcResult.getResponse().getContentAsString());
  }
}
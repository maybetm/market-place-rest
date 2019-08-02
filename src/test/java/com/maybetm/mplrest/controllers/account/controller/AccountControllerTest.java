package com.maybetm.mplrest.controllers.account.controller;

import com.maybetm.mplrest.ATest;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.roles.Role;
import com.maybetm.mplrest.security.Roles;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Модульный тест для
 * контроллера accounts {@link AccountController}
 *
 * @author zebzeev-sv
 * @version 02.08.2019 11:54
 */
public class AccountControllerTest extends ATest
{
  Account accountClient = new Account("login", "email", "password", any(ZonedDateTime.class), new Role(Roles.client.id));

  @Test
  public void testCreateAccount() throws Exception
  {
    mvcResult = mockMvc.perform(post("/account/createAccount")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                    .content(objectMapper.writeValueAsString(accountClient)))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    logger.info("Response: " + mvcResult.getResponse().getContentAsString());
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

  }
}
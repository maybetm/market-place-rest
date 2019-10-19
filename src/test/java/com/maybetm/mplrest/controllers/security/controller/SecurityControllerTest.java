package com.maybetm.mplrest.controllers.security.controller;

import com.maybetm.commons.AUnitTest;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.roles.Role;
import com.maybetm.mplrest.security.constants.Roles;
import com.maybetm.mplrest.security.constants.SecurityConstants;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Модульный тест для ендпойнта аутентификации
 * и идентификации пользователя {@link SecurityController}
 *
 * @author zebzeev-sv
 * @version 02.08.2019 15:32
 */
public class SecurityControllerTest extends AUnitTest
{

  private static final String endpoint = "/auth/";
  private Account accountClient = new Account("login478", "email444", "password4",
      LocalDateTime.now(), new Role(Roles.client.id));

  @Test
  public void testLogin() throws Exception
  {
    mvcResult = mockMvc.perform(post(endpoint + "login")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                    .content(objectMapper.writeValueAsString(accountClient)))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andReturn();
    logger.info("status: {}", mvcResult.getResponse().getStatus());
    logger.info("response: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void testLogout() throws Exception
  {
    final String token = "eyJjcmVhdGlvblRpbWUiOiIyMDE5LTEwLTA2VDIyOjU2OjM1LjQyOCIsInJvbGVJZCI6MiwiaWQiOjQsImFsZyI6IkhTMjU2In0.eyJleHAiOjE1NzA0NzA5OTV9.juKn4FSxtkDapZIBEHoELkZTmly9GznYq3QRK5zB_Ww";
    mvcResult = mockMvc.perform(delete("/auth/logout")
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .param("token", token)
        .header(SecurityConstants.headerAuth, token))
        .andReturn();
  }

}
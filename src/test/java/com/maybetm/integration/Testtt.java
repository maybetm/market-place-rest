package com.maybetm.integration;

import com.maybetm.ATest;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.roles.Role;
import com.maybetm.mplrest.security.constants.Roles;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author zebzeev-sv
 * @version 15.09.2019 21:18
 */

public class Testtt extends ATest {
	private static Account accountClient = new Account("login4", "email4",
			"password4", ZonedDateTime.now(), new Role(Roles.client.id));

	@Test
	public void testCreateAccount() throws Exception
	{
		logger.info("Response: ----------------------------------------");
		mvcResult = mockMvc.perform(post("/account/createAccount")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(objectMapper.writeValueAsString(accountClient)))
				.andReturn();
		logger.info("Response: {}", mvcResult.getResponse().getContentAsString());
	}

}

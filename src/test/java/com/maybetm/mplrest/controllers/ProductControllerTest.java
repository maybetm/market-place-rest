package com.maybetm.mplrest.controllers;

import com.maybetm.mplrest.ATest;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zebzeev-sv
 * @version 16.07.2019 15:38
 */
public class ProductControllerTest extends ATest
{
  @Test
  public void badCredentials() throws Exception {
    MvcResult response = mockMvc.perform(get("/product/getProducts"))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    System.out.println(response.getResponse().getContentAsString());

  }

}

package com.maybetm.mplrest.controllers.test;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author zebzeev-sv
 * @version 01.07.2019 15:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerTest extends TestCase
{
  @MockBean
  UserService userService;

  @Autowired
  MockMvc mockMvc;

  @Test
  public void getAlltest() throws Exception
  {
    String uri = "/getAll";
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                                          .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);
    String content = mvcResult.getResponse().getContentAsString();
    assertEquals(content, "Product is updated successsfully");
  }
}
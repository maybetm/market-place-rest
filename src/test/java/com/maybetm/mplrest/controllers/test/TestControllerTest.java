package com.maybetm.mplrest.controllers.test;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author zebzeev-sv
 * @version 01.07.2019 15:39
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TestController.class)
public class TestControllerTest extends TestCase
{
  @MockBean
  UserService userService;

  @Autowired
  MockMvc mockMvc;

  @Test
  public void getAlltest() throws Exception
  {
    mockMvc.perform(MockMvcRequestBuilders.get("/getAll"));
  }
}
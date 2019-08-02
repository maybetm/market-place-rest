package com.maybetm.mplrest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.MappedSuperclass;

/**
 * @author zebzeev-sv
 * @version 16.07.2019 15:45
 */
@RunWith (SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource ("/application.properties")
@MappedSuperclass
public abstract class ATest
{
  @Autowired
  protected MockMvc mockMvc;

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected final static ObjectMapper objectMapper = new ObjectMapper();

  public MvcResult mvcResult;


}

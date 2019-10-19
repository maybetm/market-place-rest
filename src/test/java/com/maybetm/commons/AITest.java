package com.maybetm.commons;

import com.maybetm.configuration.EmbeddedPostgresTest;
import com.maybetm.mplrest.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.MappedSuperclass;

/**
 * По умолчанию интеграционные тесты включены
 * в жизненный цикл сборки проекта.
 *
 * @author zebzeev-sv
 * @version 16.07.2019 15:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@MappedSuperclass
@EmbeddedPostgresTest
public abstract class AITest {
	@Autowired
	protected MockMvc mockMvc;
}

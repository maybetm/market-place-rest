package com.maybetm.mplrest.controllers.security.controller;

import com.maybetm.mplrest.controllers.security.service.ServiceSecurity;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.security.Token;
import com.maybetm.mplrest.security.annotations.RolesMapper;
import com.maybetm.mplrest.security.constants.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для управления аутентификацией
 * и идентификацейпользователей
 *
 * @author zebzeev-sv
 * @version 01.08.2019 11:32
 */
@RestController
@RequestMapping(value = "auth")
public class SecurityController {
	private ServiceSecurity serviceSecurity;

	@Autowired
	public SecurityController(ServiceSecurity serviceSecurity) {
		this.serviceSecurity = serviceSecurity;
	}

  /**
   * метод для аутентификации и идентификации
   * должен выполнить проверку подлинности пользователя в бд,
   * если пользователь прошёл проверку. То ему выдаётся jwt токен доступа, иначе вызываем пользовательское исключение.
	 */
  @PostMapping(value = "login")
	public ResponseEntity<Token> login(@RequestBody Account account) {
		return ResponseEntity.ok(serviceSecurity.getAccessToken(account));
	}

  /**
   * стоит допустить механизм выхода из системы
   * Он подразумевает возможность отозвать пользовательский токен, который пользователь запросил ранее.
   * должен быть доступен только авторизированному пользователю (необходим ключ jwt).
	 */
  @DeleteMapping(value = "logout")
	@RolesMapper(roles = {Roles.client, Roles.market, Roles.admin})
	public void logout(@RequestParam String token) {
		serviceSecurity.destroyToken(token);
	}
}
package com.maybetm.mplrest.controllers.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maybetm.mplrest.controllers.security.service.ServiceSecurity;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.security.Token;
import com.maybetm.mplrest.security.constants.Roles;
import com.maybetm.mplrest.security.annotations.RolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для аутентификации
 * и идентификации пользователя
 *
 * @author zebzeev-sv
 * @version 01.08.2019 11:32
 */
@RestController
@RequestMapping (value = "auth")
public class SecurityController implements ISecurityController
{
  private ServiceSecurity serviceSecurity;

  @Autowired
  public SecurityController(ServiceSecurity serviceSecurity)
  {
    this.serviceSecurity = serviceSecurity;
  }

  @PostMapping(value = "login")
  public ResponseEntity<Token> login(@RequestBody(required = true) Account account) throws JsonProcessingException
  {
    return ResponseEntity.ok(serviceSecurity.getAccessToken(account));
  }

  @PostMapping(value = "logout")
  @RolesMapper (roles = {Roles.client, Roles.market})
  public void logout(String jwt)
  {

  }

  @PostMapping(value = "destroy")
  @RolesMapper(roles = Roles.admin)
  public void destroy()
  {

  }
}

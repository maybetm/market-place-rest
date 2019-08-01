package com.maybetm.mplrest.controllers.security.controller;

import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.security.Roles;
import com.maybetm.mplrest.security.annotations.RolesMapper;
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
public class SecurityController
{

  // метод для аутентификации и идентификации
  // должен выполнить проверку подлинности пользователя в бд,
  // если пользователь прошёл проверку. То ему выдаётся jwt токен доступа, иначе вызываем пользовательское исключение.
  public String login(Account account)
  {
    return "JWT";
  }

  // стоит допустить механизм выхода из системы
  // Он подразумевает возможность отозвать пользовательский токен, который запросил ранее.
  // должен быть доступен только авторизированному пользователю (необходим ключ jwt).
  public void logout(String jwt)
  {

  }

  // так-же стоит предусмотреть
  // удаление токена администратором
  @RolesMapper(roles = Roles.admin)
  public void destroy()
  {

  }

}

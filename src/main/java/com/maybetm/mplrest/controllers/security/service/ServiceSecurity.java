package com.maybetm.mplrest.controllers.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maybetm.mplrest.exceptions.security.auth_exception.AuthException;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.account.IDBAccount;
import com.maybetm.mplrest.entities.security.IDBToken;
import com.maybetm.mplrest.entities.security.Token;
import com.maybetm.mplrest.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author zebzeev-sv
 * @version 01.08.2019 19:20
 */
@Service
public class ServiceSecurity
{

  private IDBAccount idbAccount;
  private IDBToken idbToken;

  @Autowired
  public ServiceSecurity(IDBAccount idbAccount, IDBToken idbToken)
  {
    this.idbAccount = idbAccount;
    this.idbToken = idbToken;
  }

  public Token getAccessToken(Account identificationData)
  {
    Optional<Account> account = searchAccount.apply(identificationData);

    if (account.isPresent()) {
      return createAccessToken(account.get());
    } else {
      throw new AuthException("Ошибка идентификации. " +
                              "Не удалось найти пользователя или не верные параметры идентификации.");
    }
  }

  private final Function<Account, Optional<Account>> searchAccount = (a) -> idbAccount
      .findByEmailOrLoginAndPasswordIgnoreCase(a.getEmail(), a.getLogin(), a.getPassword());

  private Token createAccessToken(Account account)
  {
    // функция для создания сущности токена с мета информацией
    // используется для сохранения и ответа идентифицированному пользователю
    final BiFunction<Account, String, Token> getTokenEntity = (a, token) ->
        new Token(new Account(a.getId()), token, ZonedDateTime.now());

    // генирируем ключ доступа
    String jwt = JwtService.createJwt(getJwtParams.apply(account));
    // сохраняем ключ и привязываем его к конкретному пользователю
    idbToken.saveAndFlush(getTokenEntity.apply(account, jwt));

    return getTokenEntity.apply(account, jwt);
  }

  private final Function<Account, Map<String, Object>> getJwtParams = (account) -> {

    Map<String, Object> result = new HashMap<>(3);
    result.put("id", account.getId());
    result.put("roleId", account.getRole().getId());
    result.put("creationTime", DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.now()));

    return result;
  };
}

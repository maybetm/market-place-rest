package com.maybetm.mplrest.controllers.security.service;

import com.maybetm.mplrest.commons.exeptions.security.AuthException;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.account.IDBAccount;
import com.maybetm.mplrest.entities.security.Token;
import com.maybetm.mplrest.security.jwt.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author zebzeev-sv
 * @version 01.08.2019 19:20
 */
@Service
public class ServiceSecurity
{

  private IDBAccount idbAccount;

  @Autowired
  public ServiceSecurity(IDBAccount idbAccount)
  {
    this.idbAccount = idbAccount;
  }

  public Token getAccessToken(Account account)
  {
    if (searchAccount.apply(account).isPresent()) {
      return createAccessToken(account);
    } else {
      throw new AuthException("Ошибка идентификации. " +
                              "Не удалось найти пользователя или не верные параметры идентификации пользователя.");
    }
  }

  private Token createAccessToken(Account account)
  {
    String token = Jwts.builder()
        .setHeaderParams(getJwtParams.apply(account))
        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.tokenLiveTime))
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.secretToken).compact();
    return new Token(account, token, ZonedDateTime.now());
  }

  private final Function<Account, Optional<Account>> searchAccount = (a) -> idbAccount
      .findByEmailOrLoginAndPassword(a.getEmail(), a.getLogin(), a.getPassword());

  private final Function<Account, Map<String, Object>> getJwtParams = (account) -> {

    Map<String, Object> result = new HashMap<>(3);
    result.put("id", account.getId());
    result.put("role", account.getRole());
    result.put("creationTime", ZonedDateTime.now());

    return result;
  };
}

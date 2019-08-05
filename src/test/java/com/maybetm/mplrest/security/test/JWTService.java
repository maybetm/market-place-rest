package com.maybetm.mplrest.security.test;

import com.maybetm.mplrest.ATest;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.security.jwt.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Тестирование механизма создания токена.
 *
 * @author zebzeev-sv
 * @version 26.07.2019 19:27
 */
public class JWTService extends ATest
{

  @Test
  public void testGenerateTocken (){
    String token = Jwts.builder()
        .setHeaderParams(getJwtParams.apply(null))
        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.tokenLiveTime))
        .signWith(SignatureAlgorithm.HS256, SecurityConstants.secretToken)
        .compact();
    System.out.println("token: " + token);
  }

  private final Function<Account, Map<String, Object>> getJwtParams = (account) -> {

    Map<String, Object> result = new HashMap<>(3);
    result.put("id", 1l);
    result.put("role", 2l);
    result.put("creationTime", 3L);

    return result;
  };



}

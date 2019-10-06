package com.maybetm.mplrest.security.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybetm.commons.AUnitTest;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.security.Token;
import com.maybetm.mplrest.security.jwt.JwtService;
import com.maybetm.mplrest.security.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.maybetm.mplrest.security.constants.SecurityConstants.TokenParams.creationTime;
import static com.maybetm.mplrest.security.constants.SecurityConstants.TokenParams.id;
import static com.maybetm.mplrest.security.constants.SecurityConstants.TokenParams.roleId;

/**
 * Тестирование механизма создания и разбора токена.
 *
 * @author zebzeev-sv
 * @version 26.07.2019 19:27
 */
public class JWTService extends AUnitTest
{

  private String token = "eyJjcmVhdGlvblRpbWUiOiIyMDE5LTA4LTA1VDE4OjIwOjIwLjIyMyswNTowMFtBc2lhL1lla2F0ZXJpbmJ1cmddIiwicm9sZUlkIjoyLCJpZCI6MSwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE1NjUwOTc2MjB9.isKxe1P-2ItyUy28PWusPKJtkklJqd3NnxLNa9nwosg";

  @Test
  public void testGenerateTocken (){
    token = Jwts.builder()
        .setHeaderParams(getJwtParams.apply(null))
        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.tokenLiveTime))
        .signWith(SignatureAlgorithm.HS256, SecurityConstants.secretToken)
        .compact();
    logger.info("token: {}", token);
  }

  private final Function<Account, Map<String, Object>> getJwtParams = (account) -> {

    Map<String, Object> result = new HashMap<>(3);
    result.put(id, 4L);
    result.put(roleId, 2l);
    result.put(creationTime, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

    return result;
  };

  @Test
  public void testJwtParse() throws JsonProcessingException
  {
    testGenerateTocken();
    Token authInfo = JwtService.parse(token).get();
    System.out.println("parse result: " + new ObjectMapper().writeValueAsString(authInfo));
  }

}

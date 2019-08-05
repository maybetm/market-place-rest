package com.maybetm.mplrest.security.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import static com.maybetm.mplrest.security.jwt.SecurityConstants.secretToken;
import static com.maybetm.mplrest.security.jwt.SecurityConstants.tokenLiveTime;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

/**
 * Класс содержащий механизм генерации jwt токена
 *
 * @author zebzeev-sv
 * @version 05.08.2019 13:12
 */
@Service
public class JwtService
{

  public static String createJwt(Map<String, Object> params)
  {
    return Jwts.builder()
        .setHeaderParams(params)
        .setExpiration(new Date(System.currentTimeMillis() + tokenLiveTime))
        .signWith(HS256, secretToken)
        .compact();
  }
}

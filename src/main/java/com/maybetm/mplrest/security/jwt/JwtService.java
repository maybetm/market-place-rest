package com.maybetm.mplrest.security.jwt;

import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.roles.Role;
import com.maybetm.mplrest.entities.security.IDBToken;
import com.maybetm.mplrest.entities.security.Token;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.maybetm.mplrest.security.SecurityConstants.TokenParams.*;
import static com.maybetm.mplrest.security.SecurityConstants.secretToken;
import static com.maybetm.mplrest.security.SecurityConstants.tokenLiveTime;
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
  @Autowired
  private IDBToken idbToken;

  public static String createJwt(Map<String, Object> params)
  {
    return Jwts.builder()
        .setHeaderParams(params)
        .setExpiration(new Date(System.currentTimeMillis() + tokenLiveTime))
        .signWith(HS256, secretToken)
        .compact();
  }

  public boolean isValid (String jwt) {

    final Token tokenFromJwt = parse(jwt);
    final Long roleId = tokenFromJwt.getAccount().getRole().getId();

    System.out.println("idbToken is null? " + (idbToken == null));
    // fixme как тут вызвать репу и выполнить проверку токена в бд.
    final Optional<Token> tokenFromDb = idbToken.findByAccountId(tokenFromJwt.getAccount().getId());

    return true;
  }

  public static Token parse(String jwt) {
    JwsHeader jwsHeader = Jwts.parser().setSigningKey(secretToken).parseClaimsJws(jwt).getHeader();
    return getTokenInfo.apply(jwsHeader, jwt);
  }

  private static final BiFunction<JwsHeader, String, Token> getTokenInfo = (jws, token) -> {

    Role role = new Role(Long.valueOf(jws.get(roleId).toString()));
    Account account = new Account(Long.valueOf(jws.get(id).toString()), null, null, null, null, role);

    return new Token(account, token, ZonedDateTime.parse(jws.get(creationTime).toString()));
  };
}

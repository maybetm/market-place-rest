package com.maybetm.mplrest.security.jwt;

import com.maybetm.mplrest.exceptions.security.AccessException;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.roles.Role;
import com.maybetm.mplrest.entities.security.IDBToken;
import com.maybetm.mplrest.entities.security.Token;
import com.maybetm.mplrest.security.constants.Roles;
import com.maybetm.mplrest.security.annotations.RolesMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.maybetm.mplrest.security.constants.SecurityConstants.TokenParams.*;
import static com.maybetm.mplrest.security.constants.SecurityConstants.secretToken;
import static com.maybetm.mplrest.security.constants.SecurityConstants.tokenLiveTime;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

/**
 * Класс содержащий механизм генерации jwt токена
 *
 * @author zebzeev-sv
 * @version 05.08.2019 13:12
 */
@Configuration
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

  public boolean isValid (RolesMapper rolesMapper, String jwt) {

    final Optional<Token> tokenFromJwt = parse(jwt);

    if (tokenFromJwt.isPresent()) {
      final Long roleId = tokenFromJwt.get().getAccount().getRole().getId();
      final Long accountId = tokenFromJwt.get().getAccount().getId();

      // сперва ищем id такой роли в памяти приложения. Продолжаем, если такая роль есть.
      final boolean appValidationIsAllowed = Roles.checkById(roleId);
      // теперь можно сверить id роли в токене, с ролями в аннотации над методом
      final boolean methodIsAllowed = Roles.checkByRolesMapper(rolesMapper, roleId);
      // сравниваем accountId, roleId, jwt с найдеными полями в таблице tokens
      final boolean dbIsAllowed = dbTokenAllowed.apply(tokenFromJwt.get(), accountId);

      // если токен на всех этапах прошёл проверку валидности, вернём истину.
      return appValidationIsAllowed && methodIsAllowed && dbIsAllowed;
    }

    return false;
  }

  private final BiFunction<Token, Long, Boolean> dbTokenAllowed = (tokenFromJwt, accountId) -> {
    final Optional<Token> tokenFromDb = idbToken.findByTokenAndAccountId(tokenFromJwt.getToken(), accountId);
    return tokenFromDb.get().equalsTokenFromJwt(tokenFromDb);
  };

  public static Optional<Token> parse(String jwt) {
    try {
      JwsHeader jwsHeader = Jwts.parser().setSigningKey(secretToken).parseClaimsJws(jwt).getHeader();
      return Optional.of(getTokenInfo.apply(jwsHeader, jwt));
    } catch (ExpiredJwtException ex) {
      throw new AccessException("Ошибка обработки токена. Истёк срок действия ключа доступа.");
    } catch (Exception ex) {
      return Optional.empty();
    }
  }

  private static final BiFunction<JwsHeader, String, Token> getTokenInfo = (jws, token) -> {

    Role role = new Role(Long.valueOf(jws.get(roleId).toString()));
    Account account = new Account(Long.valueOf(jws.get(id).toString()), null, null, null, null, role);

    return new Token(account, token, ZonedDateTime.parse(jws.get(creationTime).toString()));
  };
}

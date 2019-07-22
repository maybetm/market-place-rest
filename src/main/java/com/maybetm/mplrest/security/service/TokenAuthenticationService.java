package com.maybetm.mplrest.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

/**
 * @author zebzeev-sv
 * @version 22.07.2019 11:44
 */
public class TokenAuthenticationService
{
  private static final String SECRET = "d9397e04-ffab-4122-a098-007a001a111f";
  private static final String HEADER_STRING = "Authorization";
  private static final long EXPIRATIONTIME = 864_000_000;

  public static void addAuthentication(HttpServletResponse res, String username) {
    String JWT = Jwts.builder().setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    res.addHeader(HEADER_STRING, JWT);
  }


  public static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
      return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
    }
    return null;
  }
}

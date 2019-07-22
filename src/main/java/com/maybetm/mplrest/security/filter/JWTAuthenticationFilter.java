package com.maybetm.mplrest.security.filter;

import com.maybetm.mplrest.security.service.TokenAuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zebzeev-sv
 * @version 22.07.2019 11:40
 */
public class JWTAuthenticationFilter extends GenericFilterBean
{

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
  {
    Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest)servletRequest);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(servletRequest, servletResponse);
  }
}

package com.maybetm.mplrest.security;

import com.maybetm.mplrest.security.annotations.RolesMapper;
import com.maybetm.mplrest.security.jwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Перехватчик используется для управления доступом
 * к конечным точкам рест контроллеров.
 *
 * @author zebzeev-sv
 * @version 24.07.2019 16:07
 */
public class SecurityHandlerInterceptor extends HandlerInterceptorAdapter
{
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
  {
    if (handler instanceof HandlerMethod) {

      Optional<RolesMapper> roleMapper = getRolesMapper.apply(handler);
      String jwt = request.getHeader(SecurityConstants.headerAuth);

      logger.info("jwt: {}", jwt);

      if (roleMapper.isPresent()) {
        return accessIsAllowed.apply(roleMapper.get(), jwt);
      }
    }
    return true;
  }

  private Function<Object, Optional<RolesMapper>> getRolesMapper = (handler) ->
      Optional.ofNullable(AnnotationUtils.findAnnotation(((HandlerMethod)handler).getMethod(), RolesMapper.class));

  private final BiFunction<RolesMapper, String, Boolean> accessIsAllowed = (rolesMapper, jwt)-> {

    final boolean isValidInApplication = new JwtService().isValid(jwt);

    logger.info("isValidInApplication: {}", isValidInApplication);

    return true;
  };

  private static final Function<Long, Boolean> validates  = (roleId) -> {

  return true;
  };

}

package com.maybetm.mplrest.security;

import com.maybetm.mplrest.exceptions.security.access_exception.AccessException;
import com.maybetm.mplrest.security.annotations.RolesMapper;
import com.maybetm.mplrest.security.constants.SecurityConstants;
import com.maybetm.mplrest.security.jwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
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
  @Autowired
  private JwtService jwtService;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

    if (handler instanceof HandlerMethod) {

      // определяем наличие маркера RolesMapper над методом рест контроллера
      final Optional<RolesMapper> methodRolesMapper = getRolesMapper.apply(handler);
      // определяем наличие jwt ключа в заголовке
      final Optional<String> jwt = Optional.ofNullable(request.getHeader(SecurityConstants.headerAuth));

      // выполяем валидацию входящего запроса
      validateSecurity.accept(methodRolesMapper, jwt);

      // если метод промаркирован и токен прошёл проверку валидности
      if (methodRolesMapper.isPresent() && jwt.isPresent() && jwtService.isValid(methodRolesMapper.get(), jwt.get())) {
        return true;
      }
      // если метод не промаркирован, вернётся истина. Значит метод публичный.
      return !methodRolesMapper.isPresent();
    }
    // по умолчанию все входящие запросы запрещены.
    return false;
  }

  // ищем RolesMapper над методм и оборачиваем результат в Optional
  private Function<Object, Optional<RolesMapper>> getRolesMapper = (handler) ->
      Optional.ofNullable(AnnotationUtils.findAnnotation(((HandlerMethod)handler).getMethod(), RolesMapper.class));

  // консумер нужен для более удобного логгирования входных обезличенных параметров
  private final Consumer<String> loggerJwtParamsInterceptor = (jwt) -> {
    logger.info("request token: {}", jwt);
    JwtService.parse(jwt).ifPresent(token -> {
      logger.info("Processing of token content: Account id: {}; Role id: {}; Access token: {};",
          token.getAccount().getId(), token.getAccount().getRole().getId(), token.getToken());
    });
  };

  // если произошла ошибка, рест метод вернёт исключение
  private final BiConsumer<Optional<RolesMapper>, Optional<String>> validateSecurity = ((rolesMapper, jwt) -> {
    // если метод рест контроллера содержит маркер RolesMapper и пришёл jwt токен
    if (rolesMapper.isPresent() && jwt.isPresent()) {
      // логируем данные токена
      jwt.ifPresent(loggerJwtParamsInterceptor);
      // выполяем проверку токена на валидность
      if (!jwtService.isValid(rolesMapper.get(), jwt.get())){
        throw new AccessException("Отказано в доступе!");
      }
    }
    // если метод промаркирован, но токена нет, отправляем ошибку.
    if (rolesMapper.isPresent() && !jwt.isPresent()) {
      throw new AccessException("Отсутствует ключ доступа.");
    }
  });
}

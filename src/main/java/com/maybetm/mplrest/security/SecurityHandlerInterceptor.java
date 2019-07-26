package com.maybetm.mplrest.security;

import com.maybetm.mplrest.commons.exeptions.security.AccessException;
import com.maybetm.mplrest.security.annotations.RolesMapper;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * Перехватчик используется для управления доступом
 * к конечным точкам рест контроллеров.
 *
 * @author zebzeev-sv
 * @version 24.07.2019 16:07
 */
public class SecurityHandlerInterceptor extends HandlerInterceptorAdapter
{

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
  {
    if (handler instanceof HandlerMethod) {
      RolesMapper roleMapper = AnnotationUtils.findAnnotation(((HandlerMethod)handler).getMethod(), RolesMapper.class);
      try {
        // Проверяем доступ к запрашиваемым ресурсам у пользователя
        boolean isAllowed = accessIsAllowed.apply(roleMapper, null);

        // есть id роли совпадает с id роли, который мы получили из токена, то возвращаем истину.
        // Иначе возвращаем исключение
        throw new AccessException("Нет доступа.");

      } catch (NullPointerException ex) {
        // если метод не помечен аннотацией, то сработает исключение.
        // Это означает, что метод публичный.
        return true;
      }
    }
    return true;
  }

  private final BiFunction<RolesMapper, String, Boolean> accessIsAllowed = (roles, jwt)-> {
    Arrays.asList(roles.roles()).stream().forEach(r -> {
      System.out.printf("role id: %s; name: %s; nameObj: %s \n", r.getId(), r.getName(), r.name());
    });
    return true;
  };

}

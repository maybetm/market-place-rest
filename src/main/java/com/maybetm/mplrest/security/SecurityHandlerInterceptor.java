package com.maybetm.mplrest.security;

import com.maybetm.mplrest.commons.exeptions.NotFoundException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    if (handler instanceof HandlerMethod) {
      RoleMapper roleMapper = AnnotationUtils.findAnnotation(((HandlerMethod)handler).getMethod(), RoleMapper.class);
      if (roleMapper != null) {
        System.out.println("annotation value: " + roleMapper.value());
        // fixme вокрук этого хука можно построить доступ к едпойнтам.
        if (roleMapper.value().equals("stop")) {
          // fixme сюда пихнём нужное исключение
          throw new NotFoundException();
        }
      }
    }
    return true;
  }

}

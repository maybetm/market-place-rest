package com.maybetm.mplrest.configurations;

import com.maybetm.mplrest.security.SecurityHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zebzeev-sv
 * @version 24.07.2019 15:50
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer
{

  // Такой подход нужен, чтобы можно было хорошо автовайрить объекты в интерсепторе.
  @Bean public SecurityHandlerInterceptor securityHandlerInterceptor(){
    return new SecurityHandlerInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(securityHandlerInterceptor());
  }

}

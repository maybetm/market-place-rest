package com.maybetm.mplrest.security.config;

import com.maybetm.mplrest.security.filter.JWTAuthenticationFilter;
import com.maybetm.mplrest.security.filter.JWTLoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author zebzeev-sv
 * @version 22.07.2019 11:38
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    String password = "123";
    String encrytedPassword = this.passwordEncoder().encode(password);

    InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder>
        mngConfig = auth.inMemoryAuthentication();

    // Defines 2 users, stored in memory.
    // ** Spring BOOT >= 2.x (Spring Security 5.x)
    // Spring auto add ROLE_
    UserDetails u1 = User.withUsername("tom").password(encrytedPassword).roles("USER").build();
    UserDetails u2 = User.withUsername("jerry").password(encrytedPassword).roles("USER").build();

    mngConfig.withUser(u1);
    mngConfig.withUser(u2);

    // If Spring BOOT < 2.x (Spring Security 4.x)):
    // Spring auto add ROLE_
    // mngConfig.withUser("tom").password("123").roles("USER");
    // mngConfig.withUser("jerry").password("123").roles("USER");

  }

}

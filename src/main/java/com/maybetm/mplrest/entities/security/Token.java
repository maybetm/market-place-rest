package com.maybetm.mplrest.entities.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.account.Account;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

/**
 * Сущность используется для хранения токенов
 *
 * @author zebzeev-sv
 * @version 01.08.2019 12:29
 */
@Entity(name = "tokens")
@JsonIgnoreProperties ({"password"})
public class Token extends AEntity
{

  private Account account;

  private String token;

  private ZonedDateTime timeRegistration;

  public Token()
  {
  }

  public Token(Account account, String token, ZonedDateTime timeRegistration)
  {
    this.account = account;
    this.token = token;
    this.timeRegistration = timeRegistration;
  }

  @ManyToOne
  @JoinColumn (name = "accountId", nullable = false, updatable = false)
  public Account getAccount()
  {
    return account;
  }

  public void setAccount(Account account)
  {
    this.account = account;
  }

  public String getToken()
  {
    return token;
  }

  public void setToken(String token)
  {
    this.token = token;
  }

  public ZonedDateTime getTimeRegistration()
  {
    return timeRegistration;
  }

  public void setTimeRegistration(ZonedDateTime timeRegistration)
  {
    this.timeRegistration = timeRegistration;
  }
}

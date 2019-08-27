package com.maybetm.mplrest.entities.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.commons.datetime.ZonedDateTimeSerialization;
import com.maybetm.mplrest.entities.account.Account;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Сущность используется для хранения токенов
 *
 * @author zebzeev-sv
 * @version 01.08.2019 12:29
 */
@Entity(name = "tokens")
@Table (uniqueConstraints = {@UniqueConstraint (columnNames = {"token"})})
public class Token extends AEntity
{

  private Account account;

  @JsonSerialize(using = ZonedDateTimeSerialization.class)
  private ZonedDateTime timeRegistration;

  private String token;

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

  // используется для сверки параметров входящего токена с токеном из бд
  public boolean equalsTokenFromJwt(Optional<Token> token)
  {
    return token.isPresent() && this.account != null && this.account.getRole() != null &&
           this.account.getId().equals(token.get().getAccount().getId()) &&
           this.account.getRole().getId().equals(token.get().account.getRole().getId());
  }
}

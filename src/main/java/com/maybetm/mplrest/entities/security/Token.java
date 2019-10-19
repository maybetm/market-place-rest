package com.maybetm.mplrest.entities.security;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.commons.datetime.LocalDateTimeDeserializer;
import com.maybetm.mplrest.commons.datetime.LocalDateTimeSerializer;
import com.maybetm.mplrest.entities.account.Account;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

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

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime timeReg;

  private String token;

  public Token()
  {
  }

  public Token(Account account, String token, LocalDateTime timeRegistration)
  {
    this.account = account;
    this.token = token;
    this.timeReg = timeRegistration;
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

  public LocalDateTime getTimeReg() {
    return timeReg;
  }

  public void setTimeReg(LocalDateTime timeReg) {
    this.timeReg = timeReg;
  }

  // используется для сверки параметров входящего токена с токеном из бд
  public boolean equalsTokenFromJwt(Token token)
  {
    return this.account != null && this.account.getRole() != null &&
           this.account.getId().equals(token.getAccount().getId()) &&
           this.account.getRole().getId().equals(token.account.getRole().getId());
  }
}

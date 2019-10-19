package com.maybetm.mplrest.entities.payments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.account.Account;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * jpa сущность для хранения платежей
 *
 * @author zebzeev-sv
 * @version 12.07.2019 10:56
 */
@Entity(name = "payments")
public class Payment extends AEntity
{

  private Long accountId;

  private String login;

  private String email;

  @JsonSerialize(using = com.maybetm.mplrest.commons.datetime.LocalDateTimeSerializer.class)
  @JsonDeserialize(using = com.maybetm.mplrest.commons.datetime.LocalDateTimeDeserializer.class)
  private LocalDateTime dateTimeReg;

  private Long roleId;

  private String roleName;

  private String token;

  public Payment() {
  }

  public Payment(Account account, String token) {
    this.accountId = account.getId();
    this.login = account.getLogin();
    this.email = account.getEmail();
    this.dateTimeReg = account.getDateTimeReg();
    this.roleId = account.getRole().getId();
    this.roleName = account.getRole().getName();
    this.token = token;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDateTime getDateTimeReg() {
    return dateTimeReg;
  }

  public void setDateTimeReg(LocalDateTime dateTimeReg) {
    this.dateTimeReg = dateTimeReg;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}

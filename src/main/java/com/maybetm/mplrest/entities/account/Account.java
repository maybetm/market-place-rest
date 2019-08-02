package com.maybetm.mplrest.entities.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.entities.payments.Payment;
import com.maybetm.mplrest.entities.roles.Role;
import com.maybetm.mplrest.entities.security.Token;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * jpa сущность для любого типа пользователя
 *
 * @author zebzeev-sv
 * @version 02.07.2019 14:13
 */
@Entity(name = "accounts")
@JsonIgnoreProperties ({"basket", "payments"})
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"login", "email"})})
public class Account extends AEntity
{
  private String login;

  private String email;

  private String password;

  private ZonedDateTime dateRegistration;

  private Role role;

  private Set<Basket> basket;

  private Set<Payment> payments;

  private Set<Token> tokens;

  public Account()
  {
  }

  public Account(String login, String email, String password, ZonedDateTime dateRegistration, Role role)
  {
    this.login = login;
    this.password = password;
    this.email = email;
    this.dateRegistration = dateRegistration;
    this.role = role;
  }

  @ManyToOne
  @JoinColumn(name = "roleId", nullable = false, updatable = false)
  public Role getRole() {
    return role;
  }

  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
  public Set<Basket> getBasket() {
    return basket;
  }

  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
  public Set<Payment> getPayments()
  {
    return payments;
  }

  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
  public Set<Token> getTokens()
  {
    return tokens;
  }

  public void setTokens(Set<Token> tokens)
  {
    this.tokens = tokens;
  }

  public void setPayments(Set<Payment> payments)
  {
    this.payments = payments;
  }

  public void setBasket(Set<Basket> baskets) {
    this.basket = baskets;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public ZonedDateTime getDateRegistration() {
    return dateRegistration;
  }

  public void setDateRegistration(ZonedDateTime dateRegistration) {
    this.dateRegistration = dateRegistration;
  }
}

package com.maybetm.mplrest.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.entities.roles.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * jpa сущность пользователей
 *
 * @author zebzeev-sv
 * @version 02.07.2019 14:13
 */
@Entity(name = "users")
@JsonIgnoreProperties ({"basket"})
public class User extends AEntity
{
  private String login;

  private String password;

  private String email;

  private LocalDateTime dateRegistration;

  private Role role;

  private Set<Basket> basket;

  @ManyToOne
  @JoinColumn(name = "roleId", nullable = false, updatable = false)
  public Role getRole() {
    return role;
  }

  @OneToMany (fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
  public Set<Basket> getBasket() {
    return basket;
  }

  public void setBasket(Set<Basket> basket) {
    this.basket = basket;
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

  public LocalDateTime getDateRegistration() {
    return dateRegistration;
  }

  public void setDateRegistration(LocalDateTime dateRegistration) {
    this.dateRegistration = dateRegistration;
  }
}

package com.maybetm.mplrest.entities.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.commons.datetime.LocalDateTimeDeserializer;
import com.maybetm.mplrest.commons.datetime.LocalDateTimeSerializer;
import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.entities.roles.Role;
import com.maybetm.mplrest.entities.security.Token;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * jpa сущность для любого типа пользователя
 *
 * @author zebzeev-sv
 * @version 02.07.2019 14:13
 */
@Entity(name = "accounts")
@JsonIgnoreProperties({"basket", "payments", "tokens"})
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"login"}), @UniqueConstraint(columnNames = {"email"})})
public class Account extends AEntity {

	private String login;

	private String email;

	private String password;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime dateTimeReg;

	private Role role;

	private Set<Basket> basket;

	private Set<Token> tokens;

	public Account() {
	}

	public Account(Long id) {
		this.id = id;
	}

	public Account(String login, String email, String password, LocalDateTime dateRegistration, Role role) {
		this.login = login;
		this.email = email;
		this.password = password;
		this.dateTimeReg = dateRegistration;
		this.role = role;
	}

	public Account(Long id, String login, String email, String password, LocalDateTime dateRegistration, Role role) {
		this.setId(id);
		this.login = login;
		this.email = email;
		this.password = password;
		this.dateTimeReg = dateRegistration;
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
	public Set<Token> getTokens() {
		return tokens;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setTokens(Set<Token> tokens) {
		this.tokens = tokens;
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

	public void setPassword(String password) {
		this.password = password;
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
}

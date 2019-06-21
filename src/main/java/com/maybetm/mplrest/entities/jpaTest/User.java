package com.maybetm.mplrest.entities.jpaTest;

import com.maybetm.mplrest.commons.AEntity;

import javax.persistence.*;

/**
 * @author: ZebzeevSV
 * @version: 19.06.2019 23:26
 */
@Entity(name = "users")
public class User extends AEntity {

	private String login;

	private Address address;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@ManyToOne()
	@JoinColumn(name = "address_id", nullable = false)
	public Address getAddress() {
		return address;
	}

}
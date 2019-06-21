package com.maybetm.mplrest.entities.jpaTest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maybetm.mplrest.commons.AEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author: ZebzeevSV
 * @version: 19.06.2019 23:26
 */
@Entity(name = "address")
@JsonIgnoreProperties({"user"})
public class Address extends AEntity {

	private String name_address;

	private Set<User> user;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "address")
  public Set<User> getUser() {
    return user;
  }

	public String getName_address() {
		return name_address;
	}

	public void setName_address(String name_address) {
		this.name_address = name_address;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}
}
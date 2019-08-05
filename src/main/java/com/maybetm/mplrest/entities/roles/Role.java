package com.maybetm.mplrest.entities.roles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.account.Account;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 02.07.2019 23:29
 */
@Entity(name = "roles")
@JsonIgnoreProperties ({"accounts"})
public class Role extends AEntity {

	private String name;

	private Set<Account> accounts;

  public Role()
  {
  }

  public Role(Long id)
  {
    this.setId(id);
  }

  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

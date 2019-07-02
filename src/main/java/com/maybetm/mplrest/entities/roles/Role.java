package com.maybetm.mplrest.entities.roles;

import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author: ZebzeevSV
 * @version: 02.07.2019 23:29
 */
@Entity(name = "roles")
public class Role extends AEntity {

	private String name;

	private Set<User> users;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

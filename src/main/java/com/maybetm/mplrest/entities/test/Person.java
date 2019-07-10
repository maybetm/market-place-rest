package com.maybetm.mplrest.entities.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maybetm.mplrest.commons.AEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 10.07.2019 11:52
 */
@Entity(name = "person")
@JsonIgnoreProperties ({"usersAddress"})
public class Person extends AEntity
{
  private String firstName;

  private String lastName;

  private Set<UsersAddress> usersAddress;

  @OneToMany (fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL)
  public Set<UsersAddress> getUsersAddress()
  {
    return usersAddress;
  }

  public void setUsersAddress(Set<UsersAddress> usersAddress)
  {
    this.usersAddress = usersAddress;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }
}

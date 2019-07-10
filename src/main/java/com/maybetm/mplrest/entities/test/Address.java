package com.maybetm.mplrest.entities.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maybetm.mplrest.commons.AEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 10.07.2019 11:52
 */
@Entity(name = "address")
@JsonIgnoreProperties ({"usersAddress"})
public class Address extends AEntity
{
  private String name;

  private CategoryAddress categoryAddress;

  private Set<UsersAddress> usersAddress;

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @OneToMany (fetch = FetchType.LAZY, mappedBy = "address", cascade = CascadeType.ALL)
  public Set<UsersAddress> getUsersAddress()
  {
    return usersAddress;
  }

  public void setUsersAddress(Set<UsersAddress> usersAddress)
  {
    this.usersAddress = usersAddress;
  }

  @ManyToOne
  @JoinColumn(name = "categoryAddressId", nullable = false, updatable = false)
  public CategoryAddress getCategoryAddress()
  {
    return categoryAddress;
  }

  public void setCategoryAddress(CategoryAddress categoryAddress)
  {
    this.categoryAddress = categoryAddress;
  }
}

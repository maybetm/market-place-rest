package com.maybetm.mplrest.entities.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maybetm.mplrest.commons.AEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 10.07.2019 13:22
 */
@Entity
@JsonIgnoreProperties ({"addresses"})
public class CategoryAddress extends AEntity
{

  private String nameCategory;

  private Set<Address> addresses;

  public String getNameCategory()
  {
    return nameCategory;
  }

  public void setNameCategory(String nameCategory)
  {
    this.nameCategory = nameCategory;
  }

  @OneToMany (mappedBy = "categoryAddress", fetch = FetchType.LAZY)
  public Set<Address> getAddresses()
  {
    return addresses;
  }

  public void setAddresses(Set<Address> addresses)
  {
    this.addresses = addresses;
  }
}

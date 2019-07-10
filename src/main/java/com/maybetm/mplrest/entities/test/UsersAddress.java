package com.maybetm.mplrest.entities.test;

import com.maybetm.mplrest.commons.AEntity;

import javax.persistence.*;

/**
 * @author zebzeev-sv
 * @version 10.07.2019 11:51
 */
@Entity(name = "UsersAddress")
@Table (uniqueConstraints = @UniqueConstraint (columnNames = {"addressId", "personId"}))
public class UsersAddress extends AEntity
{
  private Address address;

  private Person person;

  private String info;

  @ManyToOne
  @JoinColumn (name = "addressId", nullable = false, updatable = false)
  public Address getAddress()
  {
    return address;
  }

  public void setAddress(Address address)
  {
    this.address = address;
  }

  @ManyToOne
  @JoinColumn(name = "personId", nullable = false, updatable = false)
  public Person getPerson()
  {
    return person;
  }

  public void setPerson(Person person)
  {
    this.person = person;
  }

  public String getInfo()
  {
    return info;
  }

  public void setInfo(String info)
  {
    this.info = info;
  }
}

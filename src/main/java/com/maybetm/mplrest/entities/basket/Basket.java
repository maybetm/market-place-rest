package com.maybetm.mplrest.entities.basket;

import com.maybetm.mplrest.commons.AEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * jpa сущность корзины
 *
 * @author zebzeev-sv
 * @version 02.07.2019 13:24
 */
@Table
@Entity (name = "basket")
public class Basket extends AEntity
{

/*  private Product productInBasket;

  private User user;*/

  private Long count;
/*
  public Product getProduct()
  {
    return productInBasket;
  }

  public void setProduct(Product product)
  {
    this.productInBasket = product;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }*/

  public Long getCount()
  {
    return count;
  }

  public void setCount(Long count)
  {
    this.count = count;
  }
}

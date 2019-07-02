package com.maybetm.mplrest.entities.basket;

import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.product.Product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

  /** fixme до лучших времён private User user; */

  private Long count;

  private Product productInBasket;

  @ManyToOne
  @JoinColumn (name = "productId", nullable = false, updatable = false)
  public Product getProduct()
  {
    return productInBasket;
  }

  public void setProduct(Product product)
  {
    this.productInBasket = product;
  }

  public Long getCount()
  {
    return count;
  }

  public void setCount(Long count)
  {
    this.count = count;
  }
}

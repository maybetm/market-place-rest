package com.maybetm.mplrest.entities.payments;

import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.user.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * jpa сущность для хранения платежей
 *
 * @author zebzeev-sv
 * @version 12.07.2019 10:56
 */
@Entity(name = "payments")
public class Payment extends AEntity
{

  private User user;

  private Product product;

  private Long costTimeOfPayment;

  private LocalDateTime paymentTime;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false, updatable = false)
  public User getUser()
  {
    return user;
  }

  @ManyToOne
  @JoinColumn(name = "productId", nullable = false, updatable = false)
  public Product getProduct()
  {
    return product;
  }

  public void setProduct(Product product)
  {
    this.product = product;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public Long getCostTimeOfPayment()
  {
    return costTimeOfPayment;
  }

  public void setCostTimeOfPayment(Long costTimeOfPayment)
  {
    this.costTimeOfPayment = costTimeOfPayment;
  }

  public LocalDateTime getPaymentTime()
  {
    return paymentTime;
  }

  public void setPaymentTime(LocalDateTime paymentTime)
  {
    this.paymentTime = paymentTime;
  }
}

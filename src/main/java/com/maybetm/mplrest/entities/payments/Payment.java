package com.maybetm.mplrest.entities.payments;

import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.user.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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

  private ZonedDateTime paymentTime;

  public Payment() {
  }

  public Payment(User user, Product product, Long costTimeOfPayment, ZonedDateTime paymentTime) {
    this.user = user;
    this.product = product;
    this.costTimeOfPayment = costTimeOfPayment;
    this.paymentTime = paymentTime;
  }

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

  public ZonedDateTime getPaymentTime()
  {
    return paymentTime;
  }

  public void setPaymentTime(ZonedDateTime paymentTime)
  {
    this.paymentTime = paymentTime;
  }
}

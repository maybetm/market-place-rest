package com.maybetm.mplrest.entities.payments;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.commons.DateTime.ZonedDateTimeSerialization;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.product.Product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

  private Account account;

  private Product product;

  private Long costTimeOfPayment;

  @JsonSerialize (using = ZonedDateTimeSerialization.class)
  private ZonedDateTime paymentTime;

  public Payment() {
  }

  public Payment(Account account, Product product, Long costTimeOfPayment, ZonedDateTime paymentTime) {
    this.account = account;
    this.product = product;
    this.costTimeOfPayment = costTimeOfPayment;
    this.paymentTime = paymentTime;
  }

  @ManyToOne
  @JoinColumn(name = "accountId", nullable = false, updatable = false)
  public Account getAccount()
  {
    return account;
  }

  @ManyToOne
  @JoinColumn(name = "productId", nullable = false, updatable = false)
  public Product getProduct()
  {
    return product;
  }

  public ZonedDateTime getPaymentTime()
  {
    return paymentTime;
  }

  public void setProduct(Product product)
  {
    this.product = product;
  }

  public void setAccount(Account account)
  {
    this.account = account;
  }

  public Long getCostTimeOfPayment()
  {
    return costTimeOfPayment;
  }

  public void setCostTimeOfPayment(Long costTimeOfPayment)
  {
    this.costTimeOfPayment = costTimeOfPayment;
  }

  public void setPaymentTime(ZonedDateTime paymentTime)
  {
    this.paymentTime = paymentTime;
  }
}

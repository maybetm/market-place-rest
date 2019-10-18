package com.maybetm.mplrest.entities.payments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.commons.datetime.LocalDateTimeDeserializer;
import com.maybetm.mplrest.commons.datetime.LocalDateTimeSerializer;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.product.Product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * jpa сущность для хранения платежей
 * fixme эту сущность ннеобходимо денормализовать
 *
 * @author zebzeev-sv
 * @version 12.07.2019 10:56
 */
@Entity(name = "payments")
public class Payment extends AEntity
{

  private Account account;

  private Product product;
  private Long cost;

  @JsonSerialize (using = LocalDateTimeSerializer.class)
  @JsonDeserialize (using = LocalDateTimeDeserializer.class)
  private LocalDateTime paymentTime;

  public Payment() {
  }

  public Payment(Long clientId, Product product, Long cost, LocalDateTime paymentTime) {
    this.account = new Account(clientId);
    this.product = product;
    this.cost = cost;
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

  public LocalDateTime getPaymentTime()
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

  public Long getCost()
  {
    return cost;
  }

  public void setCost(Long cost)
  {
    this.cost = cost;
  }

  public void setPaymentTime(LocalDateTime paymentTime)
  {
    this.paymentTime = paymentTime;
  }
}

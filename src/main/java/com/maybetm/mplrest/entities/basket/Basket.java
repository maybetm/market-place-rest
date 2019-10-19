package com.maybetm.mplrest.entities.basket;

import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.product.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * jpa сущность корзины
 *
 * @author zebzeev-sv
 * @version 02.07.2019 13:24
 */
@Entity(name = "basket")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"accountId", "productId"}))
public class Basket extends AEntity {

	private Product product;

	private Account account;

	private Long count;

  public Basket()
  {
  }

  public Basket(Product product, Account account, Long count)
  {
    this.product = product;
    this.account = account;
    this.count = count;
  }

  @ManyToOne
	@JoinColumn(name = "productId", nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	@ManyToOne
	@JoinColumn(name = "accountId", nullable = false, updatable = false)
	public Account getAccount() {
		return account;
	}

	@Column(nullable = false)
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}

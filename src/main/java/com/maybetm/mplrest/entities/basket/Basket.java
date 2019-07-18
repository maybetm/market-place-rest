package com.maybetm.mplrest.entities.basket;

import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.account.Account;

import javax.persistence.*;

/**
 * jpa сущность корзины
 *
 * fixme сущность корзины можно модифицировать, изменив поле account на userId типа Long.
 * fixme так-как предполагается, что у нас не будет необходимости в в полноценной сущности account
 * fixme в контексте корзины
 *
 * @author zebzeev-sv
 * @version 02.07.2019 13:24
 */
@Entity(name = "basket")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"accountId", "productId"}))
public class Basket extends AEntity {

	private Product product;

  // fixme возможно стоит переделать в <<Long userId>>
	private Account account;

	private Long count;

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

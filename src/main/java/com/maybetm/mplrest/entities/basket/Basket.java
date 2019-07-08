package com.maybetm.mplrest.entities.basket;

import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.user.User;

import javax.persistence.*;

/**
 * jpa сущность корзины
 *
 * @author zebzeev-sv
 * @version 02.07.2019 13:24
 */
@Entity(name = "baskets")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "productId"}))
public class Basket extends AEntity {

	private User user;

	private Product product;

  private Long count;

	@ManyToOne
	@JoinColumn(name = "productId", nullable = false, updatable = false)
	private Product getProduct() {
		return product;
	}

	@ManyToOne
	@JoinColumn(name = "userId", updatable = false)
	public User getUser() {
		return user;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
}

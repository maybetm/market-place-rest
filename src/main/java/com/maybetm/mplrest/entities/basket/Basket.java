package com.maybetm.mplrest.entities.basket;

import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.user.User;

import javax.persistence.*;

/**
 * jpa сущность корзины
 *
 * fixme сущность корзины можно модифицировать, изменив поле user на userId типа Long.
 * fixme так-как предполагается, что у нас не будет необходимости в в полноценной сущности user
 * fixme в контексте корзины
 *
 * @author zebzeev-sv
 * @version 02.07.2019 13:24
 */
@Entity(name = "basket")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "productId"}))
public class Basket extends AEntity {

	private Product product;

	private User user;

	private Long count;

	@ManyToOne
	@JoinColumn(name = "productId", nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false, updatable = false)
	public User getUser() {
		return user;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}

package com.maybetm.mplrest.entities.basket;

import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.user.User;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

/**
 * jpa сущность корзины
 *
 * @author zebzeev-sv
 * @version 02.07.2019 13:24
 */
@Entity(name = "baskets")
public class Basket extends AEntity {

	private User user;

	private Long count;

	private Product product;

	@ManyToOne
	@JoinColumn(name = "productId", nullable = false, updatable = false)
	private Product getProduct() {
		return product;
	}

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false, updatable = false)
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

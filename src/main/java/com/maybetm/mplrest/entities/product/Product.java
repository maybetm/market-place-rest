package com.maybetm.mplrest.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.entities.category.Category;
import com.maybetm.mplrest.entities.payments.Payment;

import javax.persistence.*;
import java.util.Set;

/**
 * Модель товара в магазине
 *
 * @author zebzeev-sv
 * @version 09.06.2019 23:43
 */
@Entity(name = "products")
@JsonIgnoreProperties ({"basket", "payments"})
public class Product extends AEntity {

	private String name;

	private String info;

	private Long cost;

	private Long count;

	private String logo;

	private Long marketId;

  private Category category;

	private Set<Basket> basket;

  private Set<Payment> payments;

  public Product()
  {
  }

  public Product (Long id, Long count) {
    this.setId(id);
    this.setCount(count);
  }

  @ManyToOne
  @JoinColumn(name = "categoryId", nullable = false, updatable = false)
  public Category getCategory() {
    return category;
  }

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	public Set<Basket> getBasket() {
		return basket;
	}

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
  public Set<Payment> getPayments()
  {
    return payments;
  }

  public void setPayments(Set<Payment> payments)
  {
    this.payments = payments;
  }

	public void setBasket(Set<Basket> basket) {
		this.basket = basket;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}
}
